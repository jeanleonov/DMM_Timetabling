package com.timetabling.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.timetabling.client.ui.left.LeftPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DmmTimetabling implements EntryPoint {


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get("left").add(new LeftPanel());
	}
}
