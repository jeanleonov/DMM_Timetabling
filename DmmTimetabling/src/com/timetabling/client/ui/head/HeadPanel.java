package com.timetabling.client.ui.head;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class HeadPanel extends Composite {

	private static HeadPanelUiBinder uiBinder = GWT
			.create(HeadPanelUiBinder.class);

	interface HeadPanelUiBinder extends UiBinder<Widget, HeadPanel> {
	}

	public HeadPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
