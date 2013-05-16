package com.timetabling.client.communication.providers;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;

public class HoursListProvider implements SingleSelectListPanelDataProvider<Byte> {
	
	private List<String> hoursNames = null;
	private int selectedIndex = 0;
	private Runnable onSelect = null;
	
	public HoursListProvider() {
		hoursNames = new ArrayList<String>(5);
		hoursNames.add(" - ");
		for (int i=1; i<=4; i++)
			hoursNames.add(i+"/2 пары");
	}
	
	public HoursListProvider(Runnable onSelect) {
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
		return hoursNames;
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
		return hoursNames.get(selectedIndex);
	}
	
	@Override
	public void setSelectedItem(Byte hourItem) {
		if (hourItem != null)
			selectedIndex = hourItem;
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
