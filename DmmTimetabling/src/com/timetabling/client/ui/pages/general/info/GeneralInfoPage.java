package com.timetabling.client.ui.pages.general.info;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.timetabling.client.ui.pages.BasePage;

public class GeneralInfoPage extends BasePage {

	private static GeneralInfoPageUiBinder uiBinder = GWT
			.create(GeneralInfoPageUiBinder.class);

	interface GeneralInfoPageUiBinder extends UiBinder<Widget, GeneralInfoPage> {
	}

	public GeneralInfoPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
