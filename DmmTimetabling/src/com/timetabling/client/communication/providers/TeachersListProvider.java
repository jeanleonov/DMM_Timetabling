package com.timetabling.client.communication.providers;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.client.communication.entities.TeacherProxy;
import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;

public class TeachersListProvider implements SingleSelectListPanelDataProvider<TeacherProxy> {
	
	private List<TeacherProxy> teachers = null;
	private List<String> teachersNames;
	private Communicator communicator;
	private int selectedIndex = 0;
	private CathedraProxy cathedra;
	private Runnable onSelect;
	
	public TeachersListProvider(Communicator communicator, CathedraProxy cathedra) {
		this.communicator = communicator;
		teachersNames = new ArrayList<String>();
		this.cathedra = cathedra;
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
		return teachersNames;
	}
	
	@Override
	public List<Pair<String, List<String>>> getLastLoadedFilteredMap() {
		// this provider is not isDataGroupable()
		return null;
	}

	
	
//=== ChosenFilterPanelDataProvider: ==========================

	@Override
	public void setSelectedIndex(final int newSelectedIndex) {
		if (teachers == null)
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
		return teachersNames.get(selectedIndex);
	}
	
	@Override
	public void setSelectedItem(TeacherProxy selectedItem) {
		int i=0;
		if (selectedItem != null)
			for (String teacher : teachersNames) {
				if (teacher.equals(selectedItem.getName())) {
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
		communicator.requestFactory.createTeacherRequest().getAllTeachersFrom(cathedra).
		fire(new Receiver<List<TeacherProxy>>() {
			@Override
			public void onSuccess(List<TeacherProxy> response) {
				teachers = response;
				teachersNames.clear();
				teachersNames.add(" - ");
				for (TeacherProxy cathedra : teachers)
					teachersNames.add(cathedra.getName());
				onSuccess.run();
			}
		});
	}

	@Override
	public TeacherProxy getValue() {
		if (selectedIndex != 0)
			return teachers.get(selectedIndex-1);
		return null;
	}
}
