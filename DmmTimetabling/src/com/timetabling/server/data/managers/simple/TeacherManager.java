package com.timetabling.server.data.managers.simple;

import java.lang.reflect.Method;
import java.util.List;

import com.googlecode.objectify.Key;
import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.common.NamespaceController;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;
import com.timetabling.server.data.entities.curriculum.extentions.Teacher;
import com.timetabling.server.data.entities.curriculum.extentions.Wish;

public class TeacherManager extends GenericDAO<Teacher> {
	
	public TeacherManager() {
		super(Teacher.class);
	}
	
	public void putTeacher(final Teacher teacher, final long cathedraId) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		Key<Cathedra> cathedraKey = KeyHelper.getKey(Cathedra.class, cathedraId);
		teacher.setParent(cathedraKey);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				daot.getOfy().put(teacher);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Persisting of teacher.";
			}
		});
	}
	
	public void putTeacher(final Teacher teacher) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				daot.getOfy().put(teacher);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Persisting of teacher.";
			}
		});
	}
	
	public void deleteTeacher(final long teacherId) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				daot.getOfy().delete(Teacher.class, teacherId);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Deleting of teacher.";
			}
		});
	}
	
	public List<Teacher> getAllTeachers() {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		return ofy().query(Teacher.class).list();
	}
	
	public void setTeacherName(long teacherId, String teacherName) throws Exception {
		Method nameSetter = Teacher.class.getMethod("setName", String.class);
		Utils.<Teacher>setFieldValueInEntity(NamespaceController.generalNamespace, Teacher.class, teacherId, teacherName, nameSetter);
	}
	
	public void setTeacherRank(long teacherId, int teacherRankCode) throws Exception {
		Method rankCodeSetter = Teacher.class.getMethod("setRankCode", Integer.class);
		Utils.<Teacher>setFieldValueInEntity(NamespaceController.generalNamespace, Teacher.class, teacherId, teacherRankCode, rankCodeSetter);
	}
	
	public void addWish(final long teacherId, final Wish wish) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				Key<Teacher> teacherKey = KeyHelper.getKey(Teacher.class, teacherId);
				wish.setParent(teacherKey);
				daot.getOfy().put(wish);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Persisting of wish.";
			}
		});
	}
	
	public List<Wish> getAllWishesFor(long teacherId) {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		return ofy().query(Wish.class).ancestor(KeyHelper.getKey(Teacher.class, teacherId)).list();
	}
	
	public void deleteWish(long teacherId, long wishId) {
		Key<Wish> wishKey = KeyHelper.getKey(Wish.class, KeyHelper.getKey(Teacher.class, teacherId), wishId);
		ofy().delete(wishKey);
	}

}