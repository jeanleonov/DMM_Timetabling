package com.timetabling.client.communication.providers;

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
	private int selectedIndex = 0;
	private Runnable onSelect;
	
	public CathedraListProvider(Communicator communicator) {
		this(communicator, null);
	}
	
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
					if (onSelect != null)
						onSelect.run();
				}
			});
		else {
			selectedIndex = newSelectedIndex;
			if (onSelect != null)
				onSelect.run();
		}			
	}
	
	@Override
	public String getSelectedItem() {
		return cathedrasNames.get(selectedIndex);
	}
	
	@Override
	public void setSelectedItem(CathedraProxy selectedItem) {
		int i=0;
		if (selectedItem != null)
			for (String cathedra : cathedrasNames) {
				if (cathedra.equals(selectedItem.getName())) {
					selectedIndex = i;
					if (onSelect != null)
						onSelect.run();
					return;
				}
				i++;
			}
		selectedIndex = 0;
		if (onSelect != null)
			onSelect.run();
	}
	
	public void setSelectedCathedraName(String cathedraName) {
		int i=0;
		if (cathedraName != null)
			for (String cathedra : cathedrasNames) {
				if (cathedra.equals(cathedraName)) {
					selectedIndex = i;
					if (onSelect != null)
						onSelect.run();
					return;
				}
				i++;
			}
		selectedIndex = 0;
		if (onSelect != null)
			onSelect.run();
	}
	
	@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	@Override
	public void requestData(final Runnable onSuccess) {
		communicator.requestFactory.createCathedraRequest().getAllCathedras().
		fire(new Receiver<List<CathedraProxy>>() {
			@Override
			public void onSuccess(List<CathedraProxy> response) {
				cathedras = response;
				cathedrasNames.clear();
				cathedrasNames.add(" - ");
				for (CathedraProxy cathedra : cathedras)
					cathedrasNames.add(cathedra.getName());
				onSuccess.run();
			}
		});
	}

	@Override
	public CathedraProxy getValue() {
		if (selectedIndex != 0)
			return cathedras.get(selectedIndex-1);
		return null;
	}
}
