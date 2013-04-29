package com.timetabling.client.ui.pages.timetable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.timetabling.client.ui.pages.BasePage;

public class TimetablePage extends BasePage {

	private static TimetablePageUiBinder uiBinder = GWT
			.create(TimetablePageUiBinder.class);

	interface TimetablePageUiBinder extends UiBinder<Widget, TimetablePage> {
	}

	public TimetablePage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
