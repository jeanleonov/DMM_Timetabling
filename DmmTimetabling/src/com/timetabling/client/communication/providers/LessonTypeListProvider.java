package com.timetabling.client.communication.providers;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;
import com.timetabling.shared.enums.LessonType;

public class LessonTypeListProvider implements SingleSelectListPanelDataProvider<Integer> {
	
	private List<Integer> lessonTypeCodes = null;
	private List<String> typeNames = null;
	private int selectedIndex = 0;
	
	public LessonTypeListProvider() {
		lessonTypeCodes = new ArrayList<Integer>(LessonType.values().length);
		typeNames = new ArrayList<String>(LessonType.values().length);
		for (LessonType type : LessonType.values()) {
			lessonTypeCodes.add(type.getCode());
			typeNames.add(type.getDisplayName());
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
		return typeNames;
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
		return typeNames.get(selectedIndex);
	}
	
	@Override
	public void setSelectedItem(Integer typeCode) {
		int i=0;
		for (Integer type : lessonTypeCodes) {
			if (type.equals(typeCode)) {
				selectedIndex = i;
				return;
			}
			i++;
		}
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
	public Integer getValue() {
		return lessonTypeCodes.get(selectedIndex);
	}
}
