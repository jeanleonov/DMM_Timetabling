package com.timetabling.client.base.datagrid;

import com.google.web.bindery.requestfactory.shared.EntityProxy;

public interface DataSelectionListener <Entity extends EntityProxy> {

	void onRowSelected(Entity entity);
	
}
