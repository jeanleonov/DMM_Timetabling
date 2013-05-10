package com.timetabling.client.ui.widgets.chosen.multiple;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

public class MultipleSelectList <T> extends Composite {

	private static MultipleSelectListUiBinder uiBinder = GWT
			.create(MultipleSelectListUiBinder.class);

	interface MultipleSelectListUiBinder extends UiBinder<Widget, MultipleSelectList<?>> {
	}
	
	public interface MultipleSelectListPanelDataProvider <ValueType> extends ChosenDataProvider {
		
		/** Is used by MultipleSelectList when selection is changed (step 1) */
		void setSelectedIndexes(List<Integer> selectedIndexes);

		/** Is used by MultipleSelectList when selection is changed (step 2) */
		List<String> getSelectedItems();

		/** Is used by MultipleSelectList when it is initiating (step 1) */
		void setSelectedItems(ValueType selectedItems);

		/** Is used by MultipleSelectList when it is initiating (step 2)*/
		List<Integer> getSelectedIndexes();
		
		/** Is used by Cell */
		ValueType getValue();
		
		void requestData(final Runnable onSucces);
	}

	private MultipleSelectListPanelDataProvider<T> dataProvider;

	@UiField FlowPanel panel;
	@UiField HTMLPanel loading;
	@UiField FlowPanel chosenPlace;
	private ChosenListBox chosen=null;

	public MultipleSelectList(MultipleSelectListPanelDataProvider<T> dataProvider, T initialValue) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dataProvider = dataProvider;
		dataProvider.setSelectedItems(initialValue);
		update();
	}
	
	private ChosenChangeHandler getNewHandler() {
		return new ChosenChangeHandler() {
			@Override
			public void onChange(ChosenChangeEvent event) {
				List<Integer> previouslySelected = dataProvider.getSelectedIndexes();
				List<Integer> nowSelected = getSelectedIndexes();
				if (previouslySelected.size() > nowSelected.size())
					findAndDeleteUnselectedIndex(previouslySelected, nowSelected);
				else
					findAndAddSelectedIndex(previouslySelected, nowSelected);
				dataProvider.setSelectedIndexes(previouslySelected);
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
		
	private void recreateChosen() {
		if (chosen != null)
			chosenPlace.clear();
		chosen = new ChosenListBox(dataProvider.isMultipleSelect());
		chosenPlace.add(chosen);
		chosen.addChosenChangeHandler(getNewHandler());
		if (dataProvider.isDataGroupable())
			updateChosenMap( dataProvider.getLastLoadedFilteredMap() );
		else
			updateChosenList( dataProvider.getLastLoadedFilteredList() );
		updateSelection(dataProvider.getSelectedIndexes());
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
	
	private List<Integer> getSelectedIndexes() {
		List<Integer> selected = new ArrayList<Integer>();
		for (int i=0; i<chosen.getItemCount(); i++)
			if (chosen.isItemSelected(i))
				selected.add(i);
		return selected;
	}
	
	private void updateSelection(List<Integer> selectedIndexes) {
		for (int i=0; i<chosen.getItemCount(); i++)
			chosen.setItemSelected(i, false);
		if (selectedIndexes == null || selectedIndexes.size() == 0)
			return;
		for (int i=0; i<selectedIndexes.size(); i++)
			chosen.setItemSelected(selectedIndexes.get(i), true);
	}
	
	public MultipleSelectListPanelDataProvider<T> getDataProvider() {
		return dataProvider;
	}
	
	public T getValue() {
		return dataProvider.getValue();
	}
	
	private void findAndDeleteUnselectedIndex(List<Integer> previouslySelected, List<Integer> nowSelected) {
		ListIterator<Integer> iterator = previouslySelected.listIterator();
		while (iterator.hasNext()) {
			boolean stillIsSelected = false;
			Integer oldIndex = iterator.next();
			for (Integer newIndex : nowSelected)
				if (oldIndex.equals(newIndex)) {
					stillIsSelected = true;
					break;
				}
			if (!stillIsSelected) {
				iterator.remove();
				break;
			}
		}
	}
	
	private void findAndAddSelectedIndex(List<Integer> previouslySelected, List<Integer> nowSelected) {
		for (Integer newIndex : nowSelected) {
			boolean isOld = false;
			for (Integer oldIndex : previouslySelected)
				if (newIndex.equals(oldIndex)) {
					isOld = true;
					continue;
				}
			if (!isOld) {
				previouslySelected.add(newIndex);
				break;
			}
		}
	}
	
	public void setEnabled(boolean enabled) {
		if (chosen != null)
			chosen.setEnabled(enabled);
	}
}
