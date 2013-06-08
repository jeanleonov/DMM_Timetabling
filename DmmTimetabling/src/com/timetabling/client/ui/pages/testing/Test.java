package com.timetabling.client.ui.pages.testing;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.timetabling.client.base.communication.Communicator;

public class Test extends Composite {

	private static TestUiBinder uiBinder = GWT.create(TestUiBinder.class);

	interface TestUiBinder extends UiBinder<Widget, Test> {
	}

	@UiField Button read;
	@UiField Button algorithm;

	public Test() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("read")
	void onRead(ClickEvent e) {
		Communicator.get().requestFactory.createCurriculumReaderRequest().runReading().fire();
	}

	@UiHandler("algorithm")
	void onAlgorithm(ClickEvent e) {
		Communicator.get().requestFactory.createCurriculumReaderRequest().testAlgorithm().fire();
	}

}
