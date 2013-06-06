package com.timetabling.server.generating;

import java.util.HashMap;
import java.util.Map;

import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;

public class Generator {
	
	private Map<Long, Double> curPopulation;
	private TimetableIndividual timetable;
	private double curMaxMark;
	private Long curBestIndivudual;
	private int sizeOfPopulation = 20;
	private int populationMultiplier = 5;

	public Generator(int year, boolean semester) {
		loadGroupAndTeacherTTs(year, semester);
		curPopulation = new HashMap<Long, Double>();
		curMaxMark = Double.MIN_VALUE;
		curBestIndivudual = null;
	}
	
	public TimetableIndividual getTTWithMark(double mark) {
		// TODO
		init();
		while (curMaxMark < mark) {
			multiplyAndEstimatePopulation();
			cleanPopulation();
		}
		timetable.switchToVersion(curBestIndivudual);
		return timetable;
	}
	
	private void init() {
		// TODO
	}
	
	private void loadGroupAndTeacherTTs(int year, boolean semester) {
		// TODO
	}
	
	private void multiplyAndEstimatePopulation() {
		// TODO  when individual is just created, estimate it. And remember about max mark.
	}
	
	private void cleanPopulation() {
		double curMinMark=Double.MAX_VALUE;
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
		// TODO
	}
	
}
