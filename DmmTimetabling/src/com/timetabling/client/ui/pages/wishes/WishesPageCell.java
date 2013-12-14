package com.timetabling.client.ui.pages.wishes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class WishesPageCell extends Composite implements HasText {

	private static WishesPageCellUiBinder uiBinder = GWT
			.create(WishesPageCellUiBinder.class);

	interface WishesPageCellUiBinder extends UiBinder<Widget, WishesPageCell> {
	}
	
	@UiField
	HTML time;
	int timeKey;
	int priority;
	int priorityPrime;
	int status;
	/* -1 - do nothing
	 * 0 - to save
	 * 1 - to update
	 * 2 - to delete
	 */
	
	@UiField
    MyStyle style;
	
	public WishesPageCell(int timeKey, int priority) {
		initWidget(uiBinder.createAndBindUi(this));		
		this.timeKey = timeKey;
		this.priority = priority;
		priorityPrime = priority;
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
	public int getStatus() {
		if(priorityPrime == -1){
			if(priority == -1)
				return -1;
			else
				return 0;
		}
		else{
			if(priority == -1)
				return 2;
			else if (priority == priorityPrime)
				return -1;
			else
				return 1;
		}
	}
}
