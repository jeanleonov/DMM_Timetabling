package com.timetabling.client.communication.providers;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;

public class SubgroupsListProvider implements SingleSelectListPanelDataProvider<Byte> {
	
	private List<String> subgroupsNames = null;
	private int selectedIndex = 0;
	private Runnable onSelect = null;
	
	public SubgroupsListProvider() {
		subgroupsNames = new ArrayList<String>(5);
		subgroupsNames.add(" - ");
		subgroupsNames.add("1 подгруппа");
		subgroupsNames.add("2 подгруппы");
		subgroupsNames.add("3 подгруппы");
		subgroupsNames.add("4 подгруппы");
	}
	
	public SubgroupsListProvider(Runnable onSelect) {
		this();
		this.onSelect = onSelect; 
	}
	
	//=== ChosenDataProvider: =====================================

	@Override
	public boolean isDataGroupable() {
		return false;
	}
	@Override
	public boolean isAsync() {
		return false;
	}	
	@Override
	public boolean isMultipleSelect() {
		return false;
	}
	@Override
	public List<String> getLastLoadedFilteredList() {
		return subgroupsNames;
	}
	@Override
	public List<Pair<String, List<String>>> getLastLoadedFilteredMap() {
		// this provider is not isDataGroupable()
		return null;
	}

	
	
//=== ChosenFilterPanelDataProvider: ==========================

	@Override
	public void setSelectedIndex(final int newSelectedIndex) {
		selectedIndex = newSelectedIndex;
		if (onSelect != null)
			onSelect.run();
	}
	
	@Override
	public String getSelectedItem() {
		return subgroupsNames.get(selectedIndex);
	}
	
	@Override
	public void setSelectedItem(Byte subgroupItem) {
		if (subgroupItem != null)
			selectedIndex = subgroupItem-1;
		else
			selectedIndex = 0;
		if (onSelect != null)
			onSelect.run();
	}
	
	@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	@Override
	public void requestData(final Runnable onSucces) {
		// it is not async provider
	}

	@Override
	public Byte getValue() {
		if (selectedIndex != 0)
			return new Byte((byte)selectedIndex);
		return null;
	}
}
