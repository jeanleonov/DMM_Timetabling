package com.timetabling.client.communication.providers;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;

public class CourseListProvider implements SingleSelectListPanelDataProvider<Byte> {
	
	private List<Byte> courses = null;
	private List<String> coursesNames = null;
	private int selectedIndex = 0;
	private Runnable onSelect;
	private final static int NUMBER_OF_COURSES = 5;
	
	public CourseListProvider(Runnable onSelect) {
		this.onSelect = onSelect;
		courses = new ArrayList<Byte>(NUMBER_OF_COURSES);
		coursesNames = new ArrayList<String>(NUMBER_OF_COURSES);
		coursesNames.add(" - ");
		for (byte i=1; i<=NUMBER_OF_COURSES; i++) {
			courses.add(i);
			coursesNames.add(i + "-й курс");
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
		onSelect.run();
	}
	
	@Override
	public String getSelectedItem() {
		return coursesNames.get(selectedIndex);
	}
	
	@Override
	public void setSelectedItem(Byte courseItem) {
		int i=0;
		for (String course : coursesNames) {
			if (course.contains(courseItem.toString())) {
				selectedIndex = i;
				onSelect.run();
				return;
			}
			i++;
		}
		selectedIndex = 0;
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
			return courses.get(selectedIndex-1);
		return null;
	}
}
