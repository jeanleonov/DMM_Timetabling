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
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.communication.entities.SpecialtyProxy;
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
	@UiField FlowPanel wishesOnWeek;
	String [] styleNames = {"neutral", "good", "bad", "impossible"};

	HTML t[] = new HTML[80];
	Grid grid = new Grid(8, 10);
	String times[] = {"08:00 - 09:35", 
    		"09:55 - 11:30", 
    		"11:40 - 13:15", 
    		"13:15 - 15:00", 
    		"15:15 - 16:50", 
    		"17:00 - 18:35", 
    		"18:45 - 20:20", 
    		"20:30 - 21:45" }; 
	
	List<WishProxy> wishes = new ArrayList<WishProxy>();
		
	long teacherID;
	long cathedraID;
	Integer teacherRank;
	TeacherProxy teacher;
	public WishesPage() {
		initWidget(uiBinder.createAndBindUi(this));
		
		teacherID = Communicator.get().context.getTeacherID();
		cathedraID = Communicator.get().context.getCathedraID();			
		teacherRank = getRankTeacher();

		initTable();
		
	/*	TeacherRequest requestContext = Communicator.get().requestFactory.createTeacherRequest();
		Request<List<WishProxy>> wish = requestContext.getAllWishesFor(teacherID, cathedraID);		
		wish.fire(new Receiver<List<WishProxy>>() {
			@Override
			public void onSuccess(List<WishProxy> response) {
				wishes = response;
			}
		});
		for (WishProxy w : wishes)
		{
			Window.alert(""+w.getPriorityCode());
		}*/

		
	}
	
	private Integer getRankTeacher(){		
		TeacherRequest requestContext = Communicator.get().requestFactory.createTeacherRequest();
		Request<TeacherProxy> teacherRequest = requestContext.getById(cathedraID, teacherID);
		teacherRequest.fire(new Receiver<TeacherProxy>() {
			@Override
			public void onSuccess(TeacherProxy response) {
				teacher = response;
			}
		});
		return teacher.getRankCode();
	}
	private void initTable(){
		int numRows = grid.getRowCount();
	    int numColumns = grid.getColumnCount();
	    for (int row = 0; row < numRows; row++) {
	        for (int col = 0; col < numColumns; col++) {
	        	int i = row*numColumns + col;
	        	t[i] = new HTML(times[row]);
	        	t[i].setStylePrimaryName("myCellStyle");
		    	t[i].addStyleName("neutral");
		    	t[i].addClickHandler(new ClickCellHandler(t[i]));
	            grid.setWidget(row, col, t[i]);
	         }
	      }
	    wishesOnWeek.add(grid);
	}
	@UiHandler("saveButton")
	void onSave(ClickEvent e) {
		TeacherRequest requestContext = Communicator.get().requestFactory.createTeacherRequest();		
		
		int numRows = grid.getRowCount();
	    int numColumns = grid.getColumnCount();
	    for (int row = 0; row < numRows; row++) {
	        for (int col = 0; col < numColumns; col++) {
	        	int i = row*numColumns + col;
	        	int priorityCode = getPriorityCodeByStyle(t[i].getStyleName());	
	        	if(priorityCode == -1)
	        		continue;

	        	WishProxy wish = requestContext.create(WishProxy.class);		
	    		TimeProxy time = requestContext.create(TimeProxy.class);
	    		time.setLessonNumber(row+1);
	    		time.setWeekDay(col/2+1);
	    		time.setWeekTypeCode(col%2);
	    		wish.setTime(time);
	    		wish.setPriorityCode(priorityCode);	    		
	    		requestContext.addWish(Communicator.get().context.getTeacherID(), Communicator.get().context.getCathedraID(), wish)
	    		.fire();
	    		}
	      }
	    
		
	}
	
	private int getPriorityCodeByStyle(String style){
		for(int i = 0; i < styleNames.length; i++)
			if (style.equals("myCellStyle " + styleNames[i]))
				return i-1;
		return -1;
	}

}

class ClickCellHandler implements ClickHandler{
	int i = 0;
	String [] styleNames = {"neutral", "good", "bad", "impossible"};
	HTML current;
	
	public ClickCellHandler(HTML current){
		this.current = current;
	}
	public void onClick(ClickEvent event) {
		current.removeStyleName(styleNames[i%4]);	
		i++;
		current.addStyleName(styleNames[i%4]);
	}
}
