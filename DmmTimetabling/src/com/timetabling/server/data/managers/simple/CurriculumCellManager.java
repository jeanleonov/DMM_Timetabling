package com.timetabling.server.data.managers.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import com.googlecode.objectify.Key;
import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.curriculum.Specialty;
import com.timetabling.server.data.entities.curriculum.Subject;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;

public class CurriculumCellManager extends GenericDAO<CurriculumCell> {
	
	private static final boolean FLUSHING = true;
	private static final boolean FULL = false;

	public CurriculumCellManager() {
		super(CurriculumCell.class);
	}

	public void putCurriculumCell(int year, boolean season, CurriculumCell cell) throws Exception {
		Utils.setNamespaceForSemester(year, season);
		if (cell.isInitiated()) 
			cleanLessons(key(cell));
		if (cell.getNumberOfSubgroups() != null  &&  cell.getHoursInTwoWeeks() != null) {
			putLessonsFor(key(cell), cell.getNumberOfSubgroups(), cell.getHoursInTwoWeeks());
			cell.setInitiated(true);
		}
		put(cell);
	}
	
	private Key<CurriculumCell> key(CurriculumCell cell) {
		return KeyHelper.getKey(CurriculumCell.class, cell.getId());
	}
	
	private Key<CurriculumCell> key(long cellId) {
		return KeyHelper.getKey(CurriculumCell.class, cellId);
	}
	
	private void cleanLessons(Key<CurriculumCell> parent) {
		List<Key<Lesson>> lessonsKeys = ofy().query(Lesson.class).ancestor(parent).listKeys();
		ofy().delete(lessonsKeys);
	}
	
	private void putLessonsFor(Key<CurriculumCell> parent, byte subgroups, byte hours) {
		List<Lesson> lessonsToPersist = new ArrayList<Lesson>(subgroups*(hours/2+hours%2));
		for (byte group=1; group<=subgroups; group++) {
			for (int full=0; full<hours/2; full++)
				lessonsToPersist.add(new Lesson(parent, group, FULL));
			for (int flushing=0; flushing<hours%2; flushing++)
				lessonsToPersist.add(new Lesson(parent, group, FLUSHING));
		}
		ofy().put(lessonsToPersist);
	}
	
	public void setTeacherForLesson(int year, boolean season, long cellId, byte subgroup, long teacherId) {
		Utils.setNamespaceForSemester(year, season);
		Lesson lesson = ofy().query(Lesson.class)
		.ancestor(key(cellId))
		.filter("subGroupNumber", subgroup)
		.get();
		lesson.setTeacherId(teacherId);
		ofy().put(lesson);
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
		try {
			Map<Long, CurriculumCell> map = new HashMap<Long, CurriculumCell>();
			Set<Key<Cathedra>> keys = new HashSet<Key<Cathedra>>();
			for (CurriculumCell cell : cells) {
				if (cell.getCathedraId() != null) {
					keys.add(KeyHelper.getKey(Cathedra.class, cell.getCathedraId()));
					map.put(cell.getCathedraId(), cell);
				}
			}
			Map<Key<Cathedra>, Cathedra> resultMap = ofy().get(keys);
			for (CurriculumCell cell : cells) {
				if (cell.getCathedraId() != null) {
					Key<Cathedra> key = KeyHelper.getKey(Cathedra.class, cell.getCathedraId());
					String cathedraName = resultMap.get(key).getName();
					cell.setCathedraName(cathedraName);
				}
			}
		} catch(Exception e) {
			logger.log(Level.WARNING, "Can not initiate transient cathedraName for cell", e);
			e.printStackTrace();
		}
	}
	
	private void initSpecialties(List<CurriculumCell> cells) {
		try {
			Map<Long, CurriculumCell> map = new HashMap<Long, CurriculumCell>();
			Set<Key<Specialty>> keys = new HashSet<Key<Specialty>>();
			for (CurriculumCell cell : cells) {
				keys.add(KeyHelper.getKey(Specialty.class, cell.getSpecialtyId()));
				map.put(cell.getSpecialtyId(), cell);
			}
			Map<Key<Specialty>, Specialty> resultMap = ofy().get(keys);
			for (CurriculumCell cell : cells) {
				String specialtyName = resultMap.get(KeyHelper.getKey(Specialty.class, cell.getSpecialtyId())).getName();
				cell.setSpecialtyName(specialtyName);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "Can not initiate transient specialtyName for cell", e);
		}
	}
	
	private void initSubjects(List<CurriculumCell> cells) {
		try {
			Map<Long, CurriculumCell> map = new HashMap<Long, CurriculumCell>();
			Set<Key<Subject>> keys = new HashSet<Key<Subject>>();
			for (CurriculumCell cell : cells) {
				keys.add(KeyHelper.getKey(Subject.class, cell.getSubjectId()));
				map.put(cell.getSubjectId(), cell);
			}
			Map<Key<Subject>, Subject> resultMap = ofy().get(keys);
			for (CurriculumCell cell : cells) {
				String subjectName = resultMap.get(KeyHelper.getKey(Subject.class, cell.getSubjectId())).getName();
				cell.setSubjectName(subjectName);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "Can not initiate transient subjectName for cell", e);
		}
	}
}
