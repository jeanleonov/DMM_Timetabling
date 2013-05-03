package com.timetabling.client.communication.providers;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.communication.entities.SpecialtyProxy;
import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList.SingleSelectListPanelDataProvider;

public class SpecialtyListProvider implements SingleSelectListPanelDataProvider<SpecialtyProxy> {
	
	private List<SpecialtyProxy> specialties = null;
	private List<String> specialtiesNames;
	private Communicator communicator;
	private Integer selectedIndex = 0;
	private Runnable onSelect;
	
	public SpecialtyListProvider(Communicator communicator) {
		this(communicator, null);
	}
	
	public SpecialtyListProvider(Communicator communicator, Runnable onSelect) {
		this.communicator = communicator;
		this.onSelect = onSelect;
		specialtiesNames = new ArrayList<String>();
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
		return specialtiesNames;
	}
	@Override
	public List<Pair<String, List<String>>> getLastLoadedFilteredMap() {
		// this provider is not isDataGroupable()
		return null;
	}

	
	
//=== ChosenFilterPanelDataProvider: ==========================

	@Override
	public void setSelectedIndex(final int newSelectedIndex) {
		if (specialties == null)
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
		return specialties.get(selectedIndex+1).getName();
	}
	@Override
	public void setSelectedItem(SpecialtyProxy selectedItem) {
		int i=0;
		for (String specialty : specialtiesNames) {
			if (specialty.equals(selectedItem.getName())) {
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
		communicator.requestFactory.createSpecialtyRequest().getAllSpecialties().
		fire(new Receiver<List<SpecialtyProxy>>() {
			@Override
			public void onSuccess(List<SpecialtyProxy> response) {
				specialties = response;
				specialtiesNames.clear();
				specialtiesNames.add(" - ");
				for (SpecialtyProxy cathedra : specialties)
					specialtiesNames.add(cathedra.getName());
				onSucces.run();
			}
		});
	}

	@Override
	public SpecialtyProxy getValue() {
		return specialties.get(selectedIndex+1);
	}
}
