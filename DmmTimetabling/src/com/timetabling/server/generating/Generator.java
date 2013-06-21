package com.timetabling.server.generating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.timetabling.Time;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
import com.timetabling.server.data.managers.DaoFactory;
import com.timetabling.server.data.managers.timetabling.LessonsManager;
import com.timetabling.server.generating.rules.CollisionAvoiding;
import com.timetabling.server.generating.rules.DaysLoading;
import com.timetabling.server.generating.rules.IRule;
import com.timetabling.server.generating.rules.WithoutWindows;
import com.timetabling.server.ttprinting.PDFMaker;

public class Generator {
	
	private Map<Long, Float> curPopulation;
	private TimetableIndividual timetable;
	private float curMaxMark;
	private float curMinMark;
	private Long curBestIndivudual;
	private int sizeOfPopulation = 50;
	private int maxPopulationMultiplier = 21;
	private int collisionAvoidingTries = 5;
	private double mutationProbability = 0.15;
	private List<Long> notEstimatedVersions;
	private List<IRule> rules;
	private int generation = 0;

	public Generator(int year, boolean season) {
		curPopulation = new HashMap<Long, Float>();
		curMaxMark = -Float.MAX_VALUE;
		curBestIndivudual = null;
		notEstimatedVersions = new LinkedList<Long>();
		initiateRules();
		loadGroupAndTeacherTTs(year, season);
	}
	
	private void initiateRules() {
		rules = new ArrayList<IRule>();
		rules.add(new CollisionAvoiding());
		rules.add(new DaysLoading());
		rules.add(new WithoutWindows());
	}
	
	public TimetableIndividual getTTWithMark(double mark) {
		while (curMaxMark < mark) {
			multiplyAndEstimatePopulation();
			cleanPopulation();
			System.out.print(generation + "   ");
			for (Float markToPrint: curPopulation.values())
				System.out.format("%.3f ", markToPrint);
			System.out.println();
			generation++;
		}
		try {
			new PDFMaker().createPDF(timetable.getGroupTTs(), timetable.getTeacherTTs());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timetable.setVersion(curBestIndivudual);
		return timetable;
	}
	
	private Long init(List<Lesson> lessons) {
		for (Lesson lesson : lessons) {
			lesson.setVersionTimeMap(new HashMap<Long, Time>());
			lesson.setVersionProblemsMap(new HashMap<Long, Integer>());
		}
		Long lastVersion = null;
		for (int i=0; i<sizeOfPopulation; i++) {
			Long version = getNewVersion();
			for (Lesson lesson : lessons) {
				setRandomTimeForLesson(lesson, 0);
				lesson.setTimeInVersion(lesson.getTime(), version);
			}
			curPopulation.put(version, null);
			notEstimatedVersions.add(version);
			lastVersion = version;
		}
		return lastVersion;
	}
	
	private Long getNewVersion() {
		return System.currentTimeMillis();
	}
	
	private void setRandomTimeForLesson(Lesson lesson, int collisionAvoidingTries) {
		int i=0;
		Time time = null;
		do {
			time = Time.getRandomTime(lesson.isFlushing());
			i++;
		} while (i<collisionAvoidingTries && lesson.hasPotentialCollisions(time));
		lesson.setTime(time);
	}
	
	private void loadGroupAndTeacherTTs(int year, boolean season) {
		LessonsManager manager = DaoFactory.getLessonsManager();
		List<Lesson> lessons = manager.getLessonsFor(year, season);
		Long lastVersion = init(lessons);
		List<CurriculumCell> cells = DaoFactory.getCurriculumCellManager().getCurriculumCells(year, season);
		timetable = manager.bindLessonsToTimetabels(lessons, cells, lastVersion);
		manager.initConnectionsBetweenLessons(timetable);
	}
	
	private void multiplyAndEstimatePopulation() {
		for (Long oldVersion : curPopulation.keySet()) {
			timetable.setVersion(oldVersion);
			Float versionMark = curPopulation.get(oldVersion);
			int multiplier;
			if (versionMark != null)
				multiplier = (int) (maxPopulationMultiplier*(versionMark-curMinMark)/(curMaxMark-curMinMark));
			else
				multiplier = maxPopulationMultiplier;
			for (int i=0; i<multiplier; i++)
				mutateIndividual(oldVersion);
		}
		estimateNewVersions();
	}
	
	private void mutateIndividual(Long baseVersion) {
		Long newVersion = getNewVersion();
		for (Lesson lesson : timetable.getAllLessons()) {
			if (Math.random() < mutationProbability)
				setRandomTimeForLesson(lesson, collisionAvoidingTries);
			lesson.setTimeInVersion(lesson.getTime(), newVersion);
			lesson.setTimeFromVersion(baseVersion);
		}
		notEstimatedVersions.add(newVersion);
	}
	
	private void estimateNewVersions() {
		for (Long version : notEstimatedVersions) {
			timetable.setVersionAndMoveLessonsInTTs(version);
			float mark = 0;
			for (IRule rule : rules)
				mark += rule.checkTT(timetable)*rule.getRuleType().getPriority();
			mark /= rules.size();
			curPopulation.put(version, mark);
			if (curMaxMark < mark) {
				curMaxMark = mark;
				curBestIndivudual = version;
			}
		}
		notEstimatedVersions.clear();
	}
	
	private void cleanPopulation() {
		float curMinMark;
		Long curWorstVersion = null;
		while (curPopulation.size() > sizeOfPopulation) {
			curMinMark = Float.MAX_VALUE;
			for (Long version : curPopulation.keySet()) {
				float versionMark = curPopulation.get(version);
				if (versionMark<curMinMark || curWorstVersion==null) {
					curWorstVersion = version;
					curMinMark = versionMark;
				}
			}
			this.curMinMark = curMinMark;
			curPopulation.remove(curWorstVersion);
			killVersion(curWorstVersion);
		}
	}
	
	private void killVersion(Long version) {
		for (Lesson lesson : timetable.getAllLessons())
			lesson.removeVersion(version);
	}
	
}
