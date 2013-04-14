package com.timetabling.server.data.entities.curriculum.extentions;

import com.timetabling.server.base.data.entities.DatastoreLongEntity;

/** It describes how many rooms is available */
public class RoomsInfo extends DatastoreLongEntity {
	
	private int numberOfComuterRooms;
	private int numberOfRooms;
	
	public RoomsInfo() {
	}

	public int getNumberOfComuterRooms() {
		return numberOfComuterRooms;
	}

	public void setNumberOfComuterRooms(int numberOfComuterRooms) {
		this.numberOfComuterRooms = numberOfComuterRooms;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
}
