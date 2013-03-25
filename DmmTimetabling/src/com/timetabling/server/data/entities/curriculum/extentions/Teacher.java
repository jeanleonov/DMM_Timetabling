package com.timetabling.server.data.entities.curriculum.extentions;

import java.util.List;

import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;

public class Teacher extends DatastoreLongEntity {
	
	public enum TeacherRank {
		DOZENT (0), 
		PROFESSOR (1), 
		LECTURER (2), 
		ASSISTANT (3);
		int code;
		private TeacherRank(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		static public TeacherRank getByCode(int code) {
			switch (code) {
			case 0: return DOZENT;
			case 1: return PROFESSOR;
			case 2: return LECTURER;
			default: return ASSISTANT;
			}
		}
	}
	
	@Parent private Key<Cathedra> parent;
	private String name;
	private int rankCode = -1;
	
	@Transient private List<Wish> wishes;

	public Teacher() {
	}

	public Key<Cathedra> getParent() {
		return parent;
	}

	public void setParent(Key<Cathedra> parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** @return teacher's rank (DOZENT, PROFESSOR, LECTURER or ASSISTANT) */
	public TeacherRank getRank() {
		return rankCode == -1? null : TeacherRank.getByCode(rankCode);
	}

	/** @param rank - teacher's rank (DOZENT, PROFESSOR, LECTURER or ASSISTANT) */
	public void setRank(TeacherRank rank) {
		this.rankCode = rank.getCode();
	}

	public List<Wish> getWishes() {
		return wishes;
	}

	public void setWishes(List<Wish> wishes) {
		this.wishes = wishes;
	}
}
