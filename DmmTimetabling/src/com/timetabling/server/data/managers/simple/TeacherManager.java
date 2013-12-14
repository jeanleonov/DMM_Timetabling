package com.timetabling.server.data.managers.simple;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
	
	public Teacher getById(Long cathedraId, Long teacherId) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		return ofy().get(KeyHelper.getKey(Teacher.class, KeyHelper.getKey(Cathedra.class, cathedraId), teacherId));
	}
	
	public Teacher getTeacherWithWishesById(Long cathedraId, Long teacherId) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);			
		Key<Cathedra> cathedraKey = KeyHelper.getKey(Cathedra.class, cathedraId);
		Key<Teacher> teacherKey = KeyHelper.getKey(Teacher.class, cathedraKey, teacherId);		
		Teacher teacher = ofy().get(teacherKey);
		List<Wish> wishes = ofy().query(Wish.class).ancestor(teacherKey).list();
		teacher.setWishes(wishes);
		return teacher;
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
	
	public void deleteTeacher(final long teacherId, final long cathedraId) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		List<Wish> wishes = getAllWishesFor(teacherId, cathedraId);
		final List<Long> idWishes = new ArrayList<Long>();
		for (Wish wish : wishes)
			idWishes.add(wish.getId());
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				Key<Cathedra> cathedraKey = KeyHelper.getKey(Cathedra.class, cathedraId);
				daot.getOfy().delete(KeyHelper.getKey(Teacher.class, cathedraKey, teacherId));											
				deleteWishes(teacherId, cathedraId, idWishes);				
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
	
	public List<Teacher> getAllTeachersFrom(Cathedra cathedra) {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		return ofy().query(Teacher.class).ancestor(cathedra).list();
	}
	
	public void setTeacherName(long teacherId, String teacherName) throws Exception {
		Method nameSetter = Teacher.class.getMethod("setName", String.class);
		Utils.<Teacher>setFieldValueInEntity(NamespaceController.generalNamespace, Teacher.class, teacherId, teacherName, nameSetter);
	}
	
	public void setTeacherRank(long teacherId, int teacherRankCode) throws Exception {
		Method rankCodeSetter = Teacher.class.getMethod("setRankCode", Integer.class);
		Utils.<Teacher>setFieldValueInEntity(NamespaceController.generalNamespace, Teacher.class, teacherId, teacherRankCode, rankCodeSetter);
	}

	public void addWish(final long teacherId, final long cathedraId, final Wish wish) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				Key<Cathedra> cathedraKey = KeyHelper.getKey(Cathedra.class, cathedraId);
				Key<Teacher> teacherKey = KeyHelper.getKey(Teacher.class, cathedraKey, teacherId);
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
	
	public void addWishes(final long teacherId, final long cathedraId, final List<Wish> wishes) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				Key<Cathedra> cathedraKey = KeyHelper.getKey(Cathedra.class, cathedraId);
				Key<Teacher> teacherKey = KeyHelper.getKey(Teacher.class, cathedraKey, teacherId);
				for(Wish wish: wishes){
					wish.setParent(teacherKey);
				}
				daot.getOfy().put(wishes);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Persisting of wishes.";
			}
		});
	}
	
	public List<Wish> getAllWishesFor(long teacherId, long cathedraId) {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		Key<Cathedra> cathedraKey = KeyHelper.getKey(Cathedra.class, cathedraId);
		Key<Teacher> teacherKey = KeyHelper.getKey(Teacher.class, cathedraKey, teacherId);
		return ofy().query(Wish.class).ancestor(teacherKey).list();
	}
	
	public void deleteWish(long teacherId, long cathedraId, long wishId) {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		Key<Cathedra> cathedraKey = KeyHelper.getKey(Cathedra.class, cathedraId);
		Key<Teacher> teacherKey = KeyHelper.getKey(Teacher.class, cathedraKey, teacherId);
		Key<Wish> wishKey = KeyHelper.getKey(Wish.class, teacherKey, wishId);
		ofy().delete(wishKey);
	}
	
	public void deleteWishes(long teacherId, long cathedraId, List<Long> wishIds) {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		Key<Cathedra> cathedraKey = KeyHelper.getKey(Cathedra.class, cathedraId);
		Key<Teacher> teacherKey = KeyHelper.getKey(Teacher.class, cathedraKey, teacherId);
		List<Key<Wish>> wishKeys = new ArrayList<Key<Wish>>();
		for(Long wishId : wishIds)
		{
			wishKeys.add(KeyHelper.getKey(Wish.class, teacherKey, wishId));
		}
		ofy().delete(wishKeys);
	}
	
	public void updateWishes(final long teacherId, final long cathedraId, final List<Wish> wishes, final List<Long> wishesIdToDelete) throws Exception {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
		deleteWishes(teacherId, cathedraId,  wishesIdToDelete);
		addWishes(teacherId, cathedraId, wishes);
	}

}
