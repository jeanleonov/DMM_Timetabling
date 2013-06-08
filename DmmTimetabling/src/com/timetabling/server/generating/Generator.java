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
import com.timetabling.server.generating.rules.IRule;

public class Generator {
	
	private Map<Long, Float> curPopulation;
	private TimetableIndividual timetable;
	private double curMaxMark;
	private Long curBestIndivudual;
	private int sizeOfPopulation = 20;
	private int populationMultiplier = 5;
	private int collisionAvoidingTries = 5;
	private double mutationProbability = 0.1;
	private List<Long> notEstimatedVersions;
	private List<IRule> rules;

	public Generator(int year, boolean season) {
		curPopulation = new HashMap<Long, Float>();
		curMaxMark = -Double.MAX_VALUE;
		curBestIndivudual = null;
		notEstimatedVersions = new LinkedList<Long>();
		initiateRules();
		loadGroupAndTeacherTTs(year, season);
	}
	
	private void initiateRules() {
		rules = new ArrayList<IRule>();
		rules.add(new CollisionAvoiding());
	}
	
	public TimetableIndividual getTTWithMark(double mark) {
		while (curMaxMark < mark) {
			multiplyAndEstimatePopulation();
			cleanPopulation();
		}
		timetable.setVersion(curBestIndivudual);
		return timetable;
	}
	
	private void init(List<Lesson> lessons) {
		for (Lesson lesson : lessons)
			lesson.setVersionTimeMap(new HashMap<Long, Time>());
		for (int i=0; i<sizeOfPopulation; i++) {
			Long version = getNewVersion();
			for (Lesson lesson : lessons) {
				setRandomTimeForLesson(lesson, 0);
				lesson.setTimeInVersion(lesson.getTime(), version);
			}
			curPopulation.put(version, null);
			notEstimatedVersions.add(version);
		}
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
		init(lessons);
		List<CurriculumCell> cells = DaoFactory.getCurriculumCellManager().getCurriculumCells(year, season);
		timetable = manager.bindLessonsToTimetabels(lessons, cells);
		manager.initConnectionsBetweenLessons(timetable);
	}
	
	private void multiplyAndEstimatePopulation() {
		for (Long oldVersion : curPopulation.keySet()) {
			timetable.setVersion(oldVersion);
			for (int i=0; i<populationMultiplier; i++)
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
			timetable.setVersion(version);
			float mark = 0;
			for (IRule rule : rules)
				mark += rule.checkTT(timetable)*rule.getRuleType().getPriority();
			mark /= rules.size();
			curPopulation.put(version, mark);
			if (curMaxMark < mark)
				curMaxMark = mark;
		}
		notEstimatedVersions.clear();
	}
	
	private void cleanPopulation() {
		double curMinMark;
		Long curWorstVersion = null;
		while (curPopulation.size() > sizeOfPopulation) {
			curMinMark = Double.MAX_VALUE;
			for (Long version : curPopulation.keySet()) {
				double versionMark = curPopulation.get(version);
				if (versionMark<curMinMark || curWorstVersion==null) {
					curWorstVersion = version;
					curMinMark = versionMark;
				}
			}
			curPopulation.remove(curWorstVersion);
			killVersion(curWorstVersion);
		}
		int a=0;
	}
	
	private void killVersion(Long version) {
		for (Lesson lesson : timetable.getAllLessons())
			lesson.removeTimeInVersion(version);
	}
	
}
