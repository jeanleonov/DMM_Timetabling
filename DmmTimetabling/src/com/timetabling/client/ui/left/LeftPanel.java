package com.timetabling.client.ui.left;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.timetabling.client.ui.pages.PageName;
import com.timetabling.client.ui.pages.PagesDispatcher;
import com.timetabling.client.ui.pages.testing.Test;

public class LeftPanel extends Composite {

	private static LeftPanelUiBinder uiBinder = GWT
			.create(LeftPanelUiBinder.class);

	interface LeftPanelUiBinder extends UiBinder<Widget, LeftPanel> {
	}
	
	@UiField Anchor specialties;
	@UiField Anchor subjects;
	@UiField Anchor cathedras;
	@UiField FlowPanel test;

	public LeftPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		test.add(new Test());
	}
	
	@UiHandler ("specialties")
	void onSpecialties(ClickEvent click) {
		PagesDispatcher.setPage(PageName.SPECIALTIES);
	}
	
	@UiHandler ("subjects")
	void onSubjects(ClickEvent click) {
		PagesDispatcher.setPage(PageName.SUBJECTS);
	}
	
	@UiHandler ("cathedras")
	void onCathedras(ClickEvent click) {
		PagesDispatcher.setPage(PageName.CATHEDRAS);
	}
	
	@UiHandler ("teachers")
	void onTeachers(ClickEvent click) {
		PagesDispatcher.setPage(PageName.TEACHERS);
	}
	
	@UiHandler ("curriculum")
	void onCurriculum(ClickEvent click) {
		PagesDispatcher.setPage(PageName.CURRICULUM_EDITING);
	}
	
}
