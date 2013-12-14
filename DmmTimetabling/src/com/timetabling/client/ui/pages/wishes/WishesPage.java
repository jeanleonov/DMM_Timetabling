package com.timetabling.client.ui.pages.wishes;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.communication.entities.TeacherProxy;
import com.timetabling.client.communication.entities.TimeProxy;
import com.timetabling.client.communication.entities.WishProxy;
import com.timetabling.client.communication.requests.TeacherRequest;
import com.timetabling.client.ui.pages.BasePage;

public class WishesPage extends BasePage {

	private static WishesPageUiBinder uiBinder = GWT
			.create(WishesPageUiBinder.class);

	interface WishesPageUiBinder extends UiBinder<Widget, WishesPage> {
	}
	@UiField HorizontalPanel wishesOnWeek;
	String times[] = {"08:00 - 09:35", 
    		"09:55 - 11:30", 
    		"11:40 - 13:15", 
    		"13:15 - 15:00", 
    		"15:15 - 16:50", 
    		"17:00 - 18:35", 
    		"18:45 - 20:20", 
    		"20:30 - 21:45" }; 
	
	List<WishProxy> wishes = new ArrayList<WishProxy>();	
	WishesPageCell cells[] = new WishesPageCell[80];
		
	long teacherID;
	long cathedraID;
	TeacherProxy teacher;
	public WishesPage() {
		initWidget(uiBinder.createAndBindUi(this));
		teacherID = Communicator.get().context.getTeacherID();
		cathedraID = Communicator.get().context.getCathedraID();		
		loadSavedWishes();
		
	}
	
	private void loadSavedWishes(){
		Receiver<List<WishProxy>> onWishesLoaded = new Receiver<List<WishProxy>>() {
			@Override
			public void onSuccess(List<WishProxy> response) {
				wishes = response;	
				initTable();
			}
		};
		loadWishes(onWishesLoaded);	
	}
	
	private void loadWishes(Receiver<List<WishProxy>> onWishesLoaded){		
		TeacherRequest requestContext = Communicator.get().requestFactory.createTeacherRequest();
		Request<List<WishProxy>> teacherRequest = requestContext.getAllWishesFor(teacherID, cathedraID);
		teacherRequest.fire(onWishesLoaded);
	}
	
	private void initTable(){

		int numCell = 0;
		for(int day = 1; day <= 5; day ++){
			for(int weekType = 0; weekType <= 1; weekType++){
				VerticalPanel verticalPanel = new VerticalPanel();
				for(int lesson = 1; lesson <= times.length; lesson++)
				{
					int timeKey = getTimeKey(weekType, day, lesson);
		    		cells[numCell] = new WishesPageCell(timeKey, getWishPriorityOnTime(timeKey));
		    		cells[numCell].setText(times[lesson - 1]);
		    		verticalPanel.add(cells[numCell]);	
		    		numCell++;
				}
				wishesOnWeek.add(verticalPanel);
			}
			
		}

	}
	public int getTimeKey(int weekTypeCode, int day, int lessonNumber) {
		return weekTypeCode + 100*day + 10000*lessonNumber;
	}
	private int getWishPriorityOnTime(int timeKey){
		for(WishProxy w : wishes)
			if(w.getTimeKey() == timeKey)
				return w.getPriorityCode();		
		return -1;	
	}
	
	private WishProxy getWishOnTime(int timeKey){
		for(WishProxy w : wishes)
			if(w.getTimeKey() == timeKey)
				return w;		
		return null;	
	}
	
	@UiHandler("saveButton")
	void onSave(ClickEvent e) {		
		
		List<WishProxy> toSave = new ArrayList<WishProxy>();
		List<WishProxy> toUpdate = new ArrayList<WishProxy>();
		List<Long> toDelete = new ArrayList<Long>();
		List<Long> toUpdateId = new ArrayList<Long>();
		
		TeacherRequest requestContextSaving = Communicator.get().requestFactory.createTeacherRequest();
		TeacherRequest requestContextUpdating = Communicator.get().requestFactory.createTeacherRequest();
		for(int i = 0; i < 80; i++)
		{			
			switch(cells[i].getStatus()){
			case 0:				
				WishProxy wish = requestContextSaving.create(WishProxy.class);
				wish.setTimeKey(cells[i].getTimeKey());
				wish.setPriorityCode(cells[i].getPriority());
				toSave.add(wish);
				break;
			case 1:
				toUpdateId.add(getWishOnTime(cells[i].getTimeKey()).getId());
				WishProxy wishUpdate = requestContextUpdating.create(WishProxy.class);
				wishUpdate.setTimeKey(cells[i].getTimeKey());
				wishUpdate.setPriorityCode(cells[i].getPriority());
				toUpdate.add(wishUpdate);
				break;
			case 2:
				toDelete.add(getWishOnTime(cells[i].getTimeKey()).getId());
				break;
			}
			
		}	
		
		TeacherRequest requestContext = Communicator.get().requestFactory.createTeacherRequest();
		if(toDelete != null)
			requestContext.deleteWishes(teacherID, cathedraID, toDelete).fire();		
		if(toSave != null)
			requestContextSaving.addWishes(teacherID, cathedraID, toSave).fire();
		if(toUpdate != null)
			requestContextUpdating.updateWishes(teacherID, cathedraID, toUpdate, toUpdateId).fire();
		//loadSavedWishes();
		
	}
	

}

