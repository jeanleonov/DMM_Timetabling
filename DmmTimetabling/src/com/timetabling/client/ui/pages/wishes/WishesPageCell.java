package com.timetabling.client.ui.pages.wishes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.timetabling.client.communication.entities.WishProxy;

public class WishesPageCell extends Composite implements HasText {

	private static WishesPageCellUiBinder uiBinder = GWT
			.create(WishesPageCellUiBinder.class);

	interface WishesPageCellUiBinder extends UiBinder<Widget, WishesPageCell> {
	}
	
	@UiField Label time;
	@UiField MyStyle style;
	
	private WishProxy wish;
	private int timeKey;
	private int priority;
	private int priorityPrime;
	
	public WishesPageCell(WishProxy wish, int timeKey) {
		initWidget(uiBinder.createAndBindUi(this));
		this.wish = wish;
		if (wish != null)
			priority = wish.getPriorityCode();
		else
			priority = -1;
		priorityPrime = priority;
		this.timeKey = timeKey;
		setStyle();
	}

	
	interface MyStyle extends CssResource {
        String neutral();
        String good();
        String bad();        
        String impossible();
    }
	
	public WishesPageCell(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		time.setText(firstName);
	}

	@UiHandler("time")
	void onClick(ClickEvent e) {		
		priority++;
		if(priority == 3)
			priority = -1;
		setStyle();
	}

	public void setStyle(){
		switch(priority){
		case -1:
			time.removeStyleName(style.impossible());
			time.addStyleName(style.neutral());
			break;
		case 0:
			time.removeStyleName(style.neutral());
			time.addStyleName(style.good());
			break;
		case 1:
			time.removeStyleName(style.good());
			time.addStyleName(style.bad());
			break;
		case 2:
			time.removeStyleName(style.bad());
			time.addStyleName(style.impossible());
			break;
		}
	}
	public void setText(String text) {
		time.setText(text);
	}

	public String getText() {
		return time.getText();
	}

	public int getTimeKey() {
		return timeKey;
	}
	public int getPriority() {
		return priority;
	}
	public WishProxy getWish() {
		return wish;
	}
	
	/** 
	 * possible values to return are:
	 * -1  - do nothing
	 *  0  - to save
	 *  1  - to update
	 *  2  - to delete
	 */
	public int getStatus() {
		if(priorityPrime == -1)
			if(priority == -1)
				return -1;
			else
				return 0;
		if(priority == -1)
			return 2;
		if (priority == priorityPrime)
			return -1;
		return 1;
	}
}
