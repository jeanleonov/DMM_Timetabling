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

	/** This method try to find specialty with specified name. <br>
	 *  If it found specialty -> return id of founded specialty, <br> 
	 *  otherwise -> create and persist new specialty with specified name, and return id of created entity */
	public long getSpecialtyIdFor(final String specialtyName) throws Exception {
		Utils.setNamespaceGeneral();
		final ObjectifyDao<Specialty> dao = new ObjectifyDao<Specialty>(Specialty.class);
		Specialty specialty = dao.getByProperty("name", specialtyName);
		if (specialty == null) 
			return DAOT.runInTransaction(logger, new DatastoreOperation<Long>() {
				@Override
				public Long run(DAOT daot) throws Exception {
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
		return specialty.getId();
	}
	
	public static String getDefaultShortName(String specialtyName) {
		return specialtyName.substring(0, 3);
	}
	
	public Specialty getSpecialtyById(long id) throws Exception {
		return get(KeyHelper.getKey(Specialty.class, id));
	}
	
	public List<Specialty> getAllSpecialties() {
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
