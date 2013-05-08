package com.timetabling.client.communication.providers;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;

public class HoursListProvider implements SingleSelectListPanelDataProvider<Byte> {
	
	private List<Byte> hours = null;
	private List<String> hoursNames = null;
	private int selectedIndex = 0;
	public static final String FLUSHING = "Мигалка";
	public static final String ONE = "1 пара";
	public static final String TWO = "2 пары";
	
	public HoursListProvider() {
		hours = new ArrayList<Byte>(3);
		hoursNames = new ArrayList<String>(4);
		hoursNames.add(" - ");
		hours.add((byte)1);
		hoursNames.add(FLUSHING);
		hours.add((byte)2);
		hoursNames.add(ONE);
		hours.add((byte)4);
		hoursNames.add(TWO);
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
	}
	
	@Override
	public String getSelectedItem() {
		return hoursNames.get(selectedIndex);
	}
	
	@Override
	public void setSelectedItem(Byte hourItem) {
		if (hourItem.equals(FLUSHING))
			selectedIndex = 1;
		else if (hourItem.equals(ONE))
			selectedIndex = 2;
		else if (hourItem.equals(TWO))
			selectedIndex = 3;
		else
			selectedIndex = 0;
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
			return hours.get(selectedIndex-1);
		return null;
	}
}
