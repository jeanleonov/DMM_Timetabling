package com.timetabling.server.data.managers;

import java.util.logging.Logger;

import com.timetabling.server.base.common.NamespaceController;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.ObjectifyDao;
import com.timetabling.server.data.entities.curriculum.Specialty;
import com.timetabling.server.data.entities.curriculum.Subject;

public class Utils {
	
	public final static boolean WINTER_SUMMER = true;
	public final static boolean AUTUMN_WINTER = false;
	
	private static Logger logger = Logger.getLogger(Utils.class.getSimpleName());
	
	/**  - CurriculumCell, Lesson, TimeWithVersion, Version and CuriculumCellJoiner (XXX-entities)
	 *  are placed in specified namespace (see gae datastore namespace). <br>
	 *   - Each semester is connected with namecpace. <br>
	 *   - It means that different XXX-entities can be placed in different namespaces.  <br>
	 *   - Use this method when you save or load some of XXX-entities for going to right namespace. <br>
	 *  For example: <br> 
	 *   - year=2012, semester=AUTUMN_WINTER means semester which start at 1 September 2012; <br>
	 *   - year=2012, semester=WINTER_SUMMER means semester which start at ~20 January 2012;
	 *  */
	public static void setNamespaceForSemester(int year, boolean season) {
		String namespace = new StringBuilder().append(year).append('_').append(getSeasonName(season)).toString();
		NamespaceController.getInstance().updateNamespace(namespace);
	}

	// TODO improve it! Use Cache for entities which are often getting..
	/** This method try to find specialty with specified name. <br>
	 *  If it found specialty -> return id of founded specialty, <br> 
	 *  otherwise -> create and persist new specialty with specified name, and return id of created entity */
	public static long getSpecialtyIdFor(final String specialtyName) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		final ObjectifyDao<Specialty> dao = new ObjectifyDao<Specialty>(Specialty.class);
		Specialty specialty = dao.getByProperty("name", specialtyName);
		if (specialty == null) 
			return DAOT.runInTransaction(logger, new DatastoreOperation<Long>() {
				@Override
				public Long run(DAOT daot) throws Exception {
					Specialty newSpecialty = new Specialty();
					newSpecialty.setName(specialtyName);
					return dao.put(newSpecialty).getId();
				}
				@Override
				public String getOperationName() {
					return "Saving of new specialty";
				}
			});
		return specialty.getId();
	}
	
	// TODO improve it! Use Cache for entities which are often getting..
	/** This method try to find subject with specified name. <br>
	 *  If it found subject -> return id of founded subject, <br> 
	 *  otherwise -> create and persist new subject with specified name, and return id of created entity */
	public static long getSubjectIdFor(final String subjectName) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		final ObjectifyDao<Subject> dao = new ObjectifyDao<Subject>(Subject.class);
		Subject subject = dao.getByProperty("name", subjectName);
		if (subject == null)
			return DAOT.runInTransaction(logger, new DatastoreOperation<Long>() {
				@Override
				public Long run(DAOT daot) throws Exception {
					Subject newSubject = new Subject();
					newSubject.setName(subjectName);
					return dao.put(newSubject).getId();
				}
				@Override
				public String getOperationName() {
					return "Saving of new subject";
			}});
		return subject.getId();
	}
	
	public static String getSeasonName(boolean season) {
		return season? "winter-summer" : "autumn-winter";
	}

}
