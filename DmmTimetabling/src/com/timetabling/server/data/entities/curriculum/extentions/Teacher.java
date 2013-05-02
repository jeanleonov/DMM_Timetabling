package com.timetabling.server.data.entities.curriculum.extentions;

import java.util.List;

import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;
import com.timetabling.shared.enums.TeacherRank;

public class Teacher extends DatastoreLongEntity {
	
	@Parent private Key<Cathedra> parent;
	private String name;
	private Integer rankCode = -1;
	
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
	public Integer getRankCode() {
		return rankCode;
	}

	/** @param rank - teacher's rank (DOZENT, PROFESSOR, LECTURER or ASSISTANT) */
	public void setRankCode(Integer rankCode) {
		this.rankCode = rankCode;
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
