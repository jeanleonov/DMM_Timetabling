package com.timetabling.client.ui.pages.wishes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.timetabling.client.ui.pages.BasePage;

public class WishesPage extends BasePage {

	private static WishesPageUiBinder uiBinder = GWT
			.create(WishesPageUiBinder.class);

	interface WishesPageUiBinder extends UiBinder<Widget, WishesPage> {
	}

	public WishesPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
