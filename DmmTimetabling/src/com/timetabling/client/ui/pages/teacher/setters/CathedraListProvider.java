package com.timetabling.client.ui.pages.teacher.setters;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.view.client.ListDataProvider;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.client.ui.widgets.chosen.ChosenDataProvider.Pair;
import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;

public class CathedraListProvider  extends ListDataProvider<CathedraProxy> implements SingleSelectListPanelDataProvider<List<String>> {
	
	private List<CathedraProxy> cathedras = null;
	private List<String> cathedrasNames;
	private Communicator communicator;
	private CathedraProxy selected = null;
	
	private CathedraListProvider(Communicator communicator, Runnable onFinish) {
		this.communicator = communicator;
		cathedrasNames = new ArrayList<String>();
	}
	
	//=== ChosenDataProvider: =====================================

	@Override
	public boolean isDataGroupable() {
		return false;
	}
	@Override
	public boolean isAsync() {
		return true;
	}	
	@Override
	public boolean isMultipleSelect() {
		return false;
	}
	@Override
	public List<String> getLastLoadedFilteredList() {
		return cathedrasNames;
	}
	@Override
	public List<Pair<String, List<String>>> getLastLoadedFilteredMap() {
		// this provider is not isDataGroupable()
		return null;
	}

	
	
//=== ChosenFilterPanelDataProvider: ==========================

	@Override
	public void setSelectedIndex(final int selectedIndex) {
		if (cathedras == null)
			requestData(new Runnable() {
				@Override
				public void run() {
					selected = cathedras.get(selectedIndex);
				}
			});
		else
			selected = cathedras.get(selectedIndex);
			
	}
	@Override
	public String getSelectedItem() {
			selected = cathedras.get(selectedIndex);
	}
	@Override
	public void setSelectedItem(List<String> selectedItem) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void requestData(final Runnable onSucces) {
		communicator.requestFactory.createCathedraRequest().getAllCathedras().
		fire(new Receiver<List<CathedraProxy>>() {
			@Override
			public void onSuccess(List<CathedraProxy> response) {
				cathedras = response;
				cathedrasNames.clear();
				for (CathedraProxy cathedra : cathedras)
					cathedrasNames.add(cathedra.getName());
				onSucces.run();
			}
		});
	}

	@Override
	public List<String> getValue() {
		return getSelectedItems();
	}
}
