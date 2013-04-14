package com.timetabling.server.data.managers.simple;

import java.lang.reflect.Method;
import java.util.List;

import com.googlecode.objectify.Query;
import com.timetabling.server.base.common.NamespaceController;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;

public class CathedraManager extends GenericDAO<Cathedra> {

	public CathedraManager() {
		super(Cathedra.class);
	}
	
	public void putCathedra(final Cathedra cathedra) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		Query<Cathedra> query = ofy().query(Cathedra.class).filter("name", cathedra.getName());
		if (query.count() != 0) {
			long oldCathedraId = query.getKey().getId();
			cathedra.setId(oldCathedraId);
		}
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				daot.getOfy().put(cathedra);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Persisting of cathedra.";
			}
		});
	}
	
	public void deleteCathedra(final long cathedraId) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				daot.getOfy().delete(Cathedra.class, cathedraId);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Deleting of cathedra.";
			}
		});
	}
	
	public List<Cathedra> getAllCathedras() {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		return ofy().query(Cathedra.class).list();
	}
	
	public void setCathedraName(long cathedraId, String cathedraName) throws Exception {
		Method nameSetter = Cathedra.class.getMethod("setName", String.class);
		Utils.<Cathedra>setFieldValueInEntity(NamespaceController.generalNamespace, Cathedra.class, cathedraId, cathedraName, nameSetter);
	}
	
	public void setCathedraEmail(long cathedraId, String cathedraEmail) throws Exception {
		Method nameSetter = Cathedra.class.getMethod("setEmail", String.class);
		Utils.<Cathedra>setFieldValueInEntity(NamespaceController.generalNamespace, Cathedra.class, cathedraId, cathedraEmail, nameSetter);
	}

}
