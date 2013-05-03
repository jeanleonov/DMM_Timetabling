package com.timetabling.client.communication.providers;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;
import com.timetabling.shared.enums.TeacherRank;

public class RankListProvider implements SingleSelectListPanelDataProvider<Integer> {
	
	private List<Integer> teacherRankCodes = null;
	private List<String> rankNames = null;
	private Integer selectedIndex = 0;
	
	public RankListProvider() {
		teacherRankCodes = new ArrayList<Integer>(TeacherRank.values().length);
		rankNames = new ArrayList<String>(TeacherRank.values().length);
		for (TeacherRank rank : TeacherRank.values()) {
			teacherRankCodes.add(rank.getCode());
			rankNames.add(rank.getDisplayName());
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
		return rankNames;
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
		return rankNames.get(selectedIndex);
	}
	
	@Override
	public void setSelectedItem(Integer rankName) {
		int i=0;
		for (String rank : rankNames) {
			if (rank.equals(rankName)) {
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
	public Integer getValue() {
		return teacherRankCodes.get(selectedIndex);
	}
}
