package com.timetabling.client.ui.pages.teacher.setters;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;

public class CathedraListProvider implements SingleSelectListPanelDataProvider<CathedraProxy> {
	
	private List<CathedraProxy> cathedras = null;
	private List<String> cathedrasNames;
	private Communicator communicator;
	private Integer selectedIndex = 0;
	private Runnable onSelect;
	
	public CathedraListProvider(Communicator communicator, Runnable onSelect) {
		this.communicator = communicator;
		this.onSelect = onSelect;
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
	public void setSelectedIndex(final int newSelectedIndex) {
		if (cathedras == null)
			requestData(new Runnable() {
				@Override
				public void run() {
					selectedIndex = newSelectedIndex;
					onSelect.run();
				}
			});
		else {
			selectedIndex = newSelectedIndex;
			onSelect.run();
		}			
	}
	@Override
	public String getSelectedItem() {
		return cathedras.get(selectedIndex).getName();
	}
	@Override
	public void setSelectedItem(CathedraProxy selectedItem) {
		int i=0;
		for (String cathedra : cathedrasNames) {
			if (cathedra.equals(selectedItem.getName())) {
				selectedIndex = i;
				return;
			}
			i++;
		}
	}
	@Override
	public int getSelectedIndex() {
		return selectedIndex;
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
	public CathedraProxy getValue() {
		return cathedras.get(selectedIndex);
	}
}
