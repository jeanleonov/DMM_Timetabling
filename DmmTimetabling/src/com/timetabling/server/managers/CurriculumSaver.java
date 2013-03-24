package com.rosinka.tt.server.managers;

import com.rosinka.tt.server.base.data.dao.GenericDAO;
import com.rosinka.tt.server.entities.curriculum.Specialty;

public class CurriculumSaver extends GenericDAO<Specialty> {

	protected CurriculumSaver() {
		super(Specialty.class);
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
