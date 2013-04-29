package com.timetabling.client.ui.pages.lessons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.timetabling.client.ui.pages.BasePage;

public class LessonsPage extends BasePage {

	private static LessonsPageUiBinder uiBinder = GWT
			.create(LessonsPageUiBinder.class);

	interface LessonsPageUiBinder extends UiBinder<Widget, LessonsPage> {
	}

	public LessonsPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
