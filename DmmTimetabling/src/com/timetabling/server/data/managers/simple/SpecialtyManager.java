package com.timetabling.server.data.managers.simple;

import java.lang.reflect.Method;
import java.util.List;

import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.common.NamespaceController;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.base.data.dao.ObjectifyDao;
import com.timetabling.server.data.entities.curriculum.Specialty;

public class SpecialtyManager extends GenericDAO<Specialty> {

	public SpecialtyManager() {
		super(Specialty.class);
	}
	
	public void putSpecialty(Specialty specialty) throws Exception {
		// TODO see cathedra manager
		Utils.setNamespaceGeneral();
		put(specialty);
	}
	
	public void deleteSpecialty(final long specialtyId) throws Exception {
		Utils.setNamespaceGeneral();
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				daot.getOfy().delete(Specialty.class, specialtyId);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Deleting of specialty.";
			}
		});
	}

	/** This method try to find specialty with specified name. <br>
	 *  If it found specialty -> return id of founded specialty, <br> 
	 *  otherwise -> create and persist new specialty with specified name, and return id of created entity */
	public long getSpecialtyIdFor(final String specialtyName) throws Exception {
		Utils.setNamespaceGeneral();
		final ObjectifyDao<Specialty> dao = new ObjectifyDao<Specialty>(Specialty.class);
		return DAOT.runInTransaction(logger, new DatastoreOperation<Long>() {
			@Override
			public Long run(DAOT daot) throws Exception {
				Specialty specialty = dao.getByProperty("name", specialtyName);
				if (specialty != null)
					return specialty.getId();
				Specialty newSpecialty = new Specialty();
				newSpecialty.setName(specialtyName);
				newSpecialty.setShortName(getDefaultShortName(specialtyName));
				return daot.getOfy().put(newSpecialty).getId();
			}
			@Override
			public String getOperationName() {
				return "Saving of new specialty";
			}
		});
	}
	
	public static String getDefaultShortName(String specialtyName) {
		return specialtyName.substring(0, 3);
	}
	
	public Specialty getSpecialtyById(long id) throws Exception {
		Utils.setNamespaceGeneral();
		return get(KeyHelper.getKey(Specialty.class, id));
	}
	
	public List<Specialty> getAllSpecialties() {
		Utils.setNamespaceGeneral();
		return ofy().query(Specialty.class).list();
	}
	
	public void setSpecialtyName(long specialtyId, String specialtyName) throws Exception {
		Method nameSetter = Specialty.class.getMethod("setName", String.class);
		Utils.<Specialty>setFieldValueInEntity(
				NamespaceController.generalNamespace, Specialty.class, specialtyId, specialtyName, nameSetter);
	}
	
	public void setSpecialtyShortName(long specialtyId, String specialtyShortName) throws Exception {
		Method nameSetter = Specialty.class.getMethod("setShortName", String.class);
		Utils.<Specialty>setFieldValueInEntity(
				NamespaceController.generalNamespace, Specialty.class, specialtyId, specialtyShortName, nameSetter);
	}

}
