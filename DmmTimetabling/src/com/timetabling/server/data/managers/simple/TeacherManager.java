package com.timetabling.server.data.managers.simple;

import java.lang.reflect.Method;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.common.NamespaceController;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;
import com.timetabling.server.data.entities.curriculum.extentions.Teacher;
import com.timetabling.server.data.entities.curriculum.extentions.Teacher.TeacherRank;
import com.timetabling.server.data.entities.curriculum.extentions.Wish;

public class TeacherManager extends GenericDAO<Teacher> {
	
	public TeacherManager() {
		super(Teacher.class);
	}
	
	public Key<Teacher> putTeacher(final Teacher teacher, final Key<Cathedra> cathedraKey) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		Query<Teacher> query = ofy().query(Teacher.class)
									.ancestor(cathedraKey)
									.filter("name", teacher.getName());
		if (query.count() != 0) {
			long oldTeacherId = query.getKey().getId();
			teacher.setId(oldTeacherId);
		}
		return DAOT.runInTransaction(logger, new DatastoreOperation<Key<Teacher>>() {
			@Override
			public Key<Teacher> run(DAOT daot) throws Exception {
				return daot.getOfy().put(teacher);
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
	
	public void setTeacherRank(long teacherId, TeacherRank teacherRank) throws Exception {
		Method rankSetter = Teacher.class.getMethod("setRank", TeacherRank.class);
		Utils.<Teacher>setFieldValueInEntity(NamespaceController.generalNamespace, Teacher.class, teacherId, teacherRank, rankSetter);
	}
	
	public Key<Wish> addWish(final long teacherId, final Wish wish) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		return DAOT.runInTransaction(logger, new DatastoreOperation<Key<Wish>>() {
			@Override
			public Key<Wish> run(DAOT daot) throws Exception {
				Key<Teacher> teacherKey = KeyHelper.getKey(Teacher.class, teacherId);
				wish.setParent(teacherKey);
				return daot.getOfy().put(wish);
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
