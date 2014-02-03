package com.timetabling.client.ui.pages.wishes;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.communication.entities.TeacherProxy;
import com.timetabling.client.communication.entities.WishProxy;
import com.timetabling.client.communication.requests.TeacherRequest;
import com.timetabling.client.ui.pages.BasePage;
import com.timetabling.client.ui.pages.PageName;
import com.timetabling.client.ui.pages.PagesDispatcher;

public class WishesPage extends BasePage {

	private static WishesPageUiBinder uiBinder = GWT
			.create(WishesPageUiBinder.class);

	interface WishesPageUiBinder extends UiBinder<Widget, WishesPage> {
	}
	
	@UiField InlineLabel teacherName;
	@UiField HorizontalPanel wishesOnWeek;
	String times[] = {
			"08:00-09:35 ", 
    		"09:55-11:30 ", 
    		"11:40-13:15 ", 
    		"13:15-15:00 ", 
    		"15:15-16:50 ", 
    		"17:00-18:35 ", 
    		"18:45-20:20 " };
	final static int daysInWeek = 5;
	final static int numberOfWeekTypes = 2;
	
	List<WishProxy> wishes = new ArrayList<WishProxy>();	
	WishesPageCell cells[] = new WishesPageCell[times.length * daysInWeek * numberOfWeekTypes];
		
	long teacherID;
	long cathedraID;
	TeacherProxy teacher;
	public WishesPage() {
		initWidget(uiBinder.createAndBindUi(this));
		teacherID = Communicator.get().context.getTeacherID();
		cathedraID = Communicator.get().context.getCathedraID();
		teacherName.setText(Communicator.get().context.getTeacherName());
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
	
	private void initTable() {
		int numCell = 0;
		for(int day = 1; day <= daysInWeek; day ++){
			for(int weekType = 0; weekType < numberOfWeekTypes; weekType++){
				VerticalPanel verticalPanel = new VerticalPanel();
				for(int lesson = 1; lesson <= times.length; lesson++) {
					int timeKey = getTimeKey(weekType, day, lesson);
		    		cells[numCell] = new WishesPageCell(getWishOnTime(timeKey), timeKey);
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
	
	private WishProxy getWishOnTime(int timeKey){
		for(WishProxy wish : wishes)
			if(wish.getTimeKey() == timeKey)
				return wish;		
		return null;	
	}
	
	@UiHandler("saveButton")
	void onSave(ClickEvent e) {		
		List<WishProxy> toUpdate = new ArrayList<WishProxy>();
		List<Long> toDelete = new ArrayList<Long>();
		TeacherRequest requestContext = Communicator.get().requestFactory.createTeacherRequest();
		for(int i = 0; i < cells.length; i++)		
			switch(cells[i].getStatus()) {
			case 0:				
				WishProxy wish = requestContext.create(WishProxy.class);
				wish.setTimeKey(cells[i].getTimeKey());
				wish.setPriorityCode(cells[i].getPriority());
				toUpdate.add(wish);
				break;
			case 1:
				WishProxy wishUpdate = requestContext.edit(cells[i].getWish());
				wishUpdate.setTimeKey(cells[i].getTimeKey());
				wishUpdate.setPriorityCode(cells[i].getPriority());
				toUpdate.add(wishUpdate);
				break;
			case 2:
				toDelete.add(getWishOnTime(cells[i].getTimeKey()).getId());
				break;
			}
		if(toUpdate.size() > 0 || toDelete.size() > 0)
			requestContext.updateWishes(teacherID, cathedraID, toUpdate, toDelete).fire(new OnSavedReceiver());
		else
			PagesDispatcher.setPage(PageName.TEACHERS);
	}
	
	private static class OnSavedReceiver extends Receiver<Void> {
		@Override
		public void onSuccess(Void response) {
			PagesDispatcher.setPage(PageName.TEACHERS);
		}
	}
}

