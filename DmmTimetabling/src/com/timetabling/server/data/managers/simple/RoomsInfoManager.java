package com.timetabling.server.data.managers.simple;

import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.extentions.RoomsInfo;

public class RoomsInfoManager extends GenericDAO<RoomsInfo> {
	
	private static final long idOfInstance = 0;

	public RoomsInfoManager() {
		super(RoomsInfo.class);
	}
	
	public void setRoomsInfoForSemester(int year, boolean season, final RoomsInfo info) throws Exception {
		Utils.setNamespaceForSemester(year, season);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				info.setId(idOfInstance);
				daot.getOfy().put(info);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Setting of semester info";
			}
		});
	}
	
	public RoomsInfo getRoomsInfoForSemester(int year, boolean season) {
		Utils.setNamespaceForSemester(year, season);
		return ofy().get(KeyHelper.getKey(RoomsInfo.class, idOfInstance));
	}

}
