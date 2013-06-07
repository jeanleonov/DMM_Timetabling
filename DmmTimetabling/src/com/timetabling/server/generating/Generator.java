package com.timetabling.server.generating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.timetabling.server.data.entities.timetabling.Time;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
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

	public Generator(int year, boolean semester) {
		loadGroupAndTeacherTTs(year, semester);
		curPopulation = new HashMap<Long, Float>();
		curMaxMark = Double.MIN_VALUE;
		curBestIndivudual = null;
		notEstimatedVersions = new LinkedList<Long>();
		initiateRules();
	}
	
	private void initiateRules() {
		rules = new ArrayList<IRule>();
		// TODO
		rules.add(null);
		rules.add(null);
		rules.add(null);
	}
	
	public TimetableIndividual getTTWithMark(double mark) {
		init();
		while (curMaxMark < mark) {
			multiplyAndEstimatePopulation();
			cleanPopulation();
		}
		timetable.switchToVersion(curBestIndivudual);
		return timetable;
	}
	
	private void init() {
		for (int i=0; i<sizeOfPopulation; i++) {
			Long version = getNewVersion();
			for (Lesson lesson : timetable.getAllLessons()) {
				setRandomTimeForLesson(lesson, collisionAvoidingTries);
				lesson.setTimeInVersion(lesson.getTime(), version);
			}
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
		} while (i<=collisionAvoidingTries || lesson.hasPotentialCollisions(time));
		lesson.setTime(time);
	}
	
	private void loadGroupAndTeacherTTs(int year, boolean semester) {
		// TODO
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
			curPopulation.put(version, mark/rules.size());
		}
	}
	
	private void cleanPopulation() {
		double curMinMark = Double.MAX_VALUE;
		Long curWorstVersion = null;
		while (curPopulation.size() > sizeOfPopulation) {
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
	}
	
	private void killVersion(Long version) {
		for (Lesson lesson : timetable.getAllLessons())
			lesson.removeTimeInVersion(version);
	}
	
}
