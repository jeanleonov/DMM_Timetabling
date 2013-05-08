package com.timetabling.server.data.managers.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.curriculum.Specialty;
import com.timetabling.server.data.entities.curriculum.Subject;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;

public class CurriculumCellManager extends GenericDAO<CurriculumCell> {

	public CurriculumCellManager() {
		super(CurriculumCell.class);
	}

	public void putCurriculumCell(int year, boolean season, CurriculumCell cell) throws Exception {
		Utils.setNamespaceForSemester(year, season);
		put(cell);
	}
	
	public List<CurriculumCell> getCurriculumCells(int year, boolean season) {
		Utils.setNamespaceForSemester(year, season);
		List<CurriculumCell> cells = new ArrayList<CurriculumCell>();
		cells = ofy().query(CurriculumCell.class).list();
		initTransient(cells);
		return cells;
	}
	
	public List<CurriculumCell> getCurriculumCellsForSpecialty(int year, boolean season, long specialtyId) {
		Utils.setNamespaceForSemester(year, season);
		List<CurriculumCell> cells = new ArrayList<CurriculumCell>();
		cells = ofy().query(CurriculumCell.class).
				filter("specialtyId", specialtyId).list();
		initTransient(cells);
		return cells;
	}
	
	public List<CurriculumCell> getCurriculumCellsForCource(int year, boolean season, byte course) {
		Utils.setNamespaceForSemester(year, season);
		List<CurriculumCell> cells = new ArrayList<CurriculumCell>();
		cells = ofy().query(CurriculumCell.class).
				filter("course", course).list();
		initTransient(cells);
		return cells;
	}
	
	public List<CurriculumCell> getCurriculumCellsForCathedra(int year, boolean season, long cathedraId) {
		Utils.setNamespaceForSemester(year, season);
		List<CurriculumCell> cells = new ArrayList<CurriculumCell>();
		cells = ofy().query(CurriculumCell.class).
				filter("cathedraId", cathedraId).list();
		initTransient(cells);
		return cells;
	}
	
	public List<CurriculumCell> getCurriculumCellsForSpecialtyCourse(int year, boolean season, long specialtyId, byte course) {
		Utils.setNamespaceForSemester(year, season);
		List<CurriculumCell> cells = new ArrayList<CurriculumCell>();
		cells = ofy().query(CurriculumCell.class).
				filter("specialtyId", specialtyId).
				filter("course", course).list();
		initTransient(cells);
		return cells;
	}
	
	public List<CurriculumCell> getCurriculumCellsForCathedraCourse(int year, boolean season, long cathedraId, byte course) {
		Utils.setNamespaceForSemester(year, season);
		List<CurriculumCell> cells = new ArrayList<CurriculumCell>();
		cells = ofy().query(CurriculumCell.class).
				filter("cathedraId", cathedraId).
				filter("course", course).list();
		initTransient(cells);
		return cells;
	}
	
	public List<CurriculumCell> getCurriculumCellsForCathedraSpecialty(int year, boolean season, long cathedraId, long specialtyId) {
		Utils.setNamespaceForSemester(year, season);
		List<CurriculumCell> cells = new ArrayList<CurriculumCell>();
		cells = ofy().query(CurriculumCell.class).
				filter("cathedraId", cathedraId).
				filter("specialtyId", specialtyId).list();
		initTransient(cells);
		return cells;
	}
	
	public List<CurriculumCell> getCurriculumCellsForCathedraSpecialtyCourse(int year, boolean season, long cathedraId, long specialtyId, byte course) {
		Utils.setNamespaceForSemester(year, season);
		List<CurriculumCell> cells = new ArrayList<CurriculumCell>();
		cells = ofy().query(CurriculumCell.class).
				filter("cathedraId", cathedraId).
				filter("specialtyId", specialtyId).
				filter("course", course).list();
		initTransient(cells);
		return cells;
	}

	public void deleteCell(int year, boolean season, final long cellId) throws Exception {
		Utils.setNamespaceForSemester(year, season);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				daot.getOfy().delete(CurriculumCell.class, cellId);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Deleting of curriculum cell.";
			}
		});
	}
	
	private void initTransient(List<CurriculumCell> cells) {
		Utils.setNamespaceGeneral();
		initCathedras(cells);
		initSpecialties(cells);
		initSubjects(cells);
	}
	
	private void initCathedras(List<CurriculumCell> cells) {
		Map<Long, CurriculumCell> map = new HashMap<Long, CurriculumCell>();
		Set<Key<Cathedra>> keys = new HashSet<Key<Cathedra>>();
		for (CurriculumCell cell : cells) {
			keys.add(KeyHelper.getKey(Cathedra.class, cell.getCathedraId()));
			map.put(cell.getCathedraId(), cell);
		}
		Map<Key<Cathedra>, Cathedra> resultMap = ofy().get(keys);
		for (Key<Cathedra> cathedraKey : resultMap.keySet())
			map.get(cathedraKey.getId()).setCathedraName(resultMap.get(cathedraKey).getName());
	}
	
	private void initSpecialties(List<CurriculumCell> cells) {
		Map<Long, CurriculumCell> map = new HashMap<Long, CurriculumCell>();
		Set<Key<Specialty>> keys = new HashSet<Key<Specialty>>();
		for (CurriculumCell cell : cells) {
			keys.add(KeyHelper.getKey(Specialty.class, cell.getSpecialtyId()));
			map.put(cell.getSpecialtyId(), cell);
		}
		Map<Key<Specialty>, Specialty> resultMap = ofy().get(keys);
		for (Key<Specialty> specialtyKey : resultMap.keySet())
			map.get(specialtyKey.getId()).setSpecialtyName(resultMap.get(specialtyKey).getName());
	}
	
	private void initSubjects(List<CurriculumCell> cells) {
		Map<Long, CurriculumCell> map = new HashMap<Long, CurriculumCell>();
		Set<Key<Subject>> keys = new HashSet<Key<Subject>>();
		for (CurriculumCell cell : cells) {
			keys.add(KeyHelper.getKey(Subject.class, cell.getSubjectId()));
			map.put(cell.getSubjectId(), cell);
		}
		Map<Key<Subject>, Subject> resultMap = ofy().get(keys);
		for (Key<Subject> subjectKey : resultMap.keySet())
			map.get(subjectKey.getId()).setSubjectName(resultMap.get(subjectKey).getName());
	}

}
