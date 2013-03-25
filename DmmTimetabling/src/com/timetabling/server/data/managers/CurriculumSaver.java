package com.timetabling.server.data.managers;

import java.util.List;

import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.curriculum.Specialty;

public class CurriculumSaver extends GenericDAO<Specialty> {

	/** Access modifier is DEFAULT here and it is not accident */
	CurriculumSaver() {
		super(Specialty.class);
	}
	
	/** What is 'year' and 'season'? For example: <br> 
	 *   - year=2012, semester=AUTUMN_WINTER means semester which start at 1 September 2012; <br>
	 *   - year=2012, semester=WINTER_SUMMER means semester which start at ~20 January 2012; <br>
	 *   Use constants Utils.WINTER_SUMMER and Utils.AUTUMN_WINTER !*/
	public void saveCurriculumsForSemester(
			final int year, 
			final boolean season, 
			final List<CurriculumCell> curriculumsCells) 
					throws Exception {
		Utils.setNamespaceForSemester(year, season);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				ofy().put(curriculumsCells);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Persisting of curriculums cells for " + Utils.getSeasonName(season) + " " + year + "season.";
			}
		});
	}
	
/*
	public void saveSpecialty(final List<Specialty> specialties) throws Exception{
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>(){
			
			private List<AdmissionYear> admissionYearsList = new ArrayList<AdmissionYear>();
			private List<Semester> semestersList = new ArrayList<Semester>();
			private List<Subject> subjectsList = new ArrayList<Subject>();
			private List<Type> typesList = new ArrayList<Type>();
			
			private Map<Key<AdmissionYear>,AdmissionYear> admissionYearsMap = new HashMap<Key<AdmissionYear>,AdmissionYear>();
			private Map<Key<Semester>,Semester> semestersMap = new HashMap<Key<Semester>,Semester>();
			private Map<Key<Subject>,Subject> subjectsMap = new HashMap<Key<Subject>,Subject>();
			
			private DAOT daot;

			@Override
			public Void run(DAOT daot) throws Exception {
				this.daot = daot;
				Map<Key<Specialty>, Specialty> specialtiesMap = daot.getOfy().put(specialties);
				Set<Key<Specialty>> keys = specialtiesMap.keySet();
				for (Key<Specialty> key : keys)
					bindAdmissionYears(key, specialtiesMap.get(key).getMyAdmissionYears());
				putAdmissionYears();
				return null;
			}
			
			private void bindAdmissionYears(Key<Specialty> parent, List<AdmissionYear> admissionYears){
				for (AdmissionYear admissionYear : admissionYears)
					admissionYear.setParent(parent);
				admissionYearsList.addAll(admissionYears);
			}
			
			private void putAdmissionYears(){
				admissionYearsMap = daot.getOfy().put(admissionYearsList);
				Set<Key<AdmissionYear>> keys = admissionYearsMap.keySet();
				for (Key<AdmissionYear> key : keys)
					bindSemesters(key, admissionYearsMap.get(key).getSemesters());
				putSemesters();
			}
			
			private void bindSemesters(Key<AdmissionYear> parent, List<Semester> semesters){
				for (Semester semester : semesters)
					semester.setParent(parent);
				semestersList.addAll(semesters);
			}
			
			private void putSemesters(){
				semestersMap = daot.getOfy().put(semestersList);
				Set<Key<Semester>> keys = semestersMap.keySet();
				for (Key<Semester> key : keys)
					bindSubjects(key, semestersMap.get(key).getSubjects());
				putSubjects();
			}
			
			private void bindSubjects(Key<Semester> parent, List<Subject> subjects){
				for (Subject subject : subjects)
					subject.setParent(parent);
				subjectsList.addAll(subjects);
			}
			
			private void putSubjects(){
				subjectsMap = daot.getOfy().put(subjectsList);
				Set<Key<Subject>> keys = subjectsMap.keySet();
				for (Key<Subject> key : keys)
					bindTypes(key, subjectsMap.get(key).getTypes());
				putTypes();
			}
			
			private void bindTypes(Key<Subject> parent, List<Type> types){
				for (Type type : types)
					type.setParent(parent);
				typesList.addAll(types);
			}
			
			private void putTypes(){
				daot.getOfy().put(typesList);
			}

			@Override
			public String getOperationName() {
				return ConstantsServer.SAVE_CURRICULUM;
			}
			
		});
	}
*/	
}
