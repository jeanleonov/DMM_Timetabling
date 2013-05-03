package com.timetabling.client.ui.pages.curriculum;

import com.github.gwtbootstrap.client.ui.RadioButton;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.timetabling.client.ui.pages.BasePage;

public class CurriculumPage extends BasePage {

	private static CurriculumPageUiBinder uiBinder = GWT
			.create(CurriculumPageUiBinder.class);

	interface CurriculumPageUiBinder extends UiBinder<Widget, CurriculumPage> {
	}

	public CurriculumPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
