package com.timetabling.client.communication.providers;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;

public class HoursListProvider implements SingleSelectListPanelDataProvider<Byte> {
	
	private List<Byte> courses = null;
	private List<String> coursesNames = null;
	private Integer selectedIndex = 0;
	private final static int NUMBER_OF_COURSES = 6;
	
	public HoursListProvider() {
		courses = new ArrayList<Byte>(NUMBER_OF_COURSES);
		coursesNames = new ArrayList<String>(NUMBER_OF_COURSES);
		coursesNames.add(" - ");
		for (byte i=4; i<=NUMBER_OF_COURSES; i++) {
			courses.add(i);
			coursesNames.add(i + " Course");
		}
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
		return coursesNames;
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
		return coursesNames.get(selectedIndex);
	}
	
	@Override
	public void setSelectedItem(Byte courseItem) {
		int i=0;
		for (String course : coursesNames) {
			if (course.equals(courseItem)) {
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
		// it is not async provider
	}

	@Override
	public Byte getValue() {
		return courses.get(selectedIndex+1);
	}
}
