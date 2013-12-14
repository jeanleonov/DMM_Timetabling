package com.timetabling.client.ui.pages;

import com.google.gwt.user.client.ui.RootPanel;

public class PagesDispatcher {
	
	public static void setPage(PageName page) {
		for (PageName pageName : PageName.values())
			if (pageName.isInitiated()  &&  pageName != page)
				pageName.getPage().setVisible(false);

		
		if (page.isInitiated())
			page.getPage().setVisible(true);
		else
			RootPanel.get("content").add(page.getPage());
	}
}
