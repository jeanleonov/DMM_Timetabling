package com.timetabling.client.ui.widgets.chosen.single;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.timetabling.client.ui.widgets.chosen.ChosenDataProvider;
import com.timetabling.client.ui.widgets.chosen.ChosenDataProvider.Pair;
import com.watopi.chosen.client.event.ChosenChangeEvent;
import com.watopi.chosen.client.event.ChosenChangeEvent.ChosenChangeHandler;
import com.watopi.chosen.client.gwt.ChosenListBox;

public class SingleSelectList <T> extends Composite {

	private static SingleSelectListUiBinder uiBinder = GWT
			.create(SingleSelectListUiBinder.class);

	interface SingleSelectListUiBinder extends UiBinder<Widget, SingleSelectList<?>> {
	}
	
	public interface SingleSelectListPanelDataProvider <ValueType> extends ChosenDataProvider {
		
		/** Is used by SingleSelectList when selection is changed (step 1) */
		void setSelectedIndex(int selectedIndex);

		/** Is used by SingleSelectList when selection is changed (step 2) */
		String getSelectedItem();

		/** Is used by SingleSelectList when it is initiating (step 1) */
		void setSelectedItem(ValueType selectedItem);

		/** Is used by SingleSelectList when it is initiating (step 2)*/
		int getSelectedIndex();
		
		/** Is used by Cell */
		ValueType getValue();
		
		void requestData(final Runnable onSucces);
	}

	private SingleSelectListPanelDataProvider<T> dataProvider;

	@UiField FlowPanel panel;
	@UiField HTMLPanel loading;
	@UiField FlowPanel chosenPlace;
	private ChosenListBox chosen=null;

	public SingleSelectList(SingleSelectListPanelDataProvider<T> dataProvider) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dataProvider = dataProvider;
		update();
	}

	public SingleSelectList(SingleSelectListPanelDataProvider<T> dataProvider, T initialValue) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dataProvider = dataProvider;
		dataProvider.setSelectedItem(initialValue);
		update();
	}
	
	private ChosenChangeHandler getNewHandler() {
		return new ChosenChangeHandler() {
			@Override
			public void onChange(ChosenChangeEvent event) {
				dataProvider.setSelectedIndex(chosen.getSelectedIndex());
			}
		};
	}
	
	public void update(){
		if (dataProvider.isAsync())
			dataProvider.requestData(new Runnable() {
				@Override
				public void run() {
					recreateChosen();
				}
			});
		else
			recreateChosen();
	}
	
	public void setSelectedItem(T item) {
		dataProvider.setSelectedItem(item);
	}
		
	private void recreateChosen() {
		if (chosen != null)
			chosenPlace.clear();
		chosen = new ChosenListBox(false);
		chosenPlace.add(chosen);
		chosen.addChosenChangeHandler(getNewHandler());
		if (dataProvider.isDataGroupable())
			updateChosenMap( dataProvider.getLastLoadedFilteredMap() );
		else
			updateChosenList( dataProvider.getLastLoadedFilteredList() );
		updateSelection(dataProvider.getSelectedIndex());
	}
	
	private void updateChosenList(List<String> items) {
		for (int itemIndex=2; itemIndex<items.size()+2; itemIndex++) {
			String displayItem;
			if (items.get(itemIndex-2).length() > 26)
				displayItem = items.get(itemIndex-2).substring(0, 26) + "..";
			else
				displayItem = items.get(itemIndex-2);
			chosen.insertItem(displayItem, itemIndex);
		}
		chosen.update();
	}
	
	private void updateChosenMap(List<Pair<String, List<String>>> groupedItems) {
		for (int groupIndex=0, itemIndex=2; groupIndex<groupedItems.size(); groupIndex++) {
			int groupItemIndex = itemIndex++;
			Pair<String, List<String>> groupPair = groupedItems.get(groupIndex);
			String groupName = groupPair.first;
			chosen.insertGroup(groupName, groupItemIndex);
			List<String> items = groupPair.second;
			for (int localItemIndex=0; localItemIndex<items.size(); localItemIndex++, itemIndex++) {
				String displayItem;
				if (items.get(localItemIndex).length() > 26)
					displayItem = items.get(localItemIndex).substring(0, 26) + "..";
				else
					displayItem = items.get(localItemIndex);
				chosen.insertItemToGroup(displayItem, groupItemIndex, itemIndex);
			}
		}
	}
	
	private void updateSelection(int selectedIndex) {
		for (int i=0; i<chosen.getItemCount(); i++)
			chosen.setItemSelected(i, false);
		chosen.setItemSelected(selectedIndex, true);
	}
	
	public SingleSelectListPanelDataProvider<T> getDataProvider() {
		return dataProvider;
	}
	
	public T getValue() {
		return dataProvider.getValue();
	}
	
	public void setEnabled(boolean enabled) {
		if (chosen != null) {
			update();
			chosen.setEnabled(enabled);
		}
	}
}
