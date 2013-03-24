package com.rosinka.tt.server.entities;

import com.googlecode.objectify.annotation.Unindexed;
import com.rosinka.tt.server.base.data.entities.DatastoreLongEntity;

public class Account extends DatastoreLongEntity {
	
	public enum Role {
		
		ADMIN (0)	/** can everything */,
		
		MARGO (1)	/** can 
						- confirm that all information is collected and start generation of timetable;
						- edit generated timetable;
						- everything what can DEANERY;
						- everything what can CATHEDRA;
						- everything what can TEACHER;*/, 
						
		DEANERY (2)	/** can load and extend curriculum */, 
		
		CATHEDRA (3)/** can assign itself to ~lessons (CurriculumRecord), assign it teacher to lessons*/, 
		
		TEACHER (4)	/** can post some wishes */, 
		
		STUDENT (5)	/** watching timetable and.. (in the future we can add some "sociality" to timetabling) */, 
		
		GUEST (6)	/** just watching timetable */;
		
		int code;
		private Role(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		static public Role getByCode(int code) {
			switch (code) {
			case 0: return ADMIN;
			case 1: return MARGO;
			case 2: return DEANERY;
			case 3: return CATHEDRA;
			case 4: return TEACHER;
			case 5: return STUDENT;
			default: return GUEST;
			}
		}
	}
	
	private String login;
	@Unindexed private String password;
	@Unindexed private int roleCode = -1;
	

	public Account() {
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return roleCode == -1? null : Role.getByCode(roleCode);
	}

	public void setRole(Role role) {
		this.roleCode = role.getCode();
	}
}
