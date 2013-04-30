package com.timetabling.client.base.datagrid;

import java.util.List;

import com.google.gwt.user.cellview.client.AbstractHasData;
import com.google.gwt.user.cellview.client.LoadingStateChangeEvent;
import com.google.gwt.user.cellview.client.LoadingStateChangeEvent.LoadingState;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ProvidesKey;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public abstract class BaseAsyncDataProvider<E> extends AsyncDataProvider<E> {
	
	public abstract ProvidesKey<E> getKeyProvider();
	protected abstract Request<List<E>> createListRequest();

	protected Receiver<List<E>> currentListReceiver = null;
	private Runnable preSucces = null;
	private Runnable postSucces = null;
	private Runnable onFailure = null;

	public BaseAsyncDataProvider() {
	}

	public void update() {
		for (HasData<E> hasData : getDataDisplays())
			if (hasData != null)
				onRangeChanged(hasData);
	}

	@Override
	protected void onRangeChanged(final HasData<E> display) {
		final AbstractHasData<E> cellTable;
		if (!(display instanceof BaseDataGrid) && !(display instanceof AbstractHasData))
			return;
		cellTable = (AbstractHasData<E>) display;
		cellTable.fireEvent(new LoadingStateChangeEvent(LoadingState.LOADING));
		cellTable.setVisibleRangeAndClearData(cellTable.getVisibleRange(), false);
		currentListReceiver = getReceiver(cellTable);
		Request<List<E>> request = createListRequest(); 
		if (request != null)
			request.fire(currentListReceiver);
	}
	
	private Receiver<List<E>> getReceiver(final AbstractHasData<E> cellTable) {
		return new Receiver<List<E>>() {
			@Override
			public void onFailure(ServerFailure error) {
				cellTable.fireEvent(new LoadingStateChangeEvent(LoadingState.LOADED));
				if (onFailure != null)
					onFailure.run();
			}
			@Override
			public void onSuccess(List<E> response) {
				if (preSucces != null)
					preSucces.run();
				cellTable.fireEvent(new LoadingStateChangeEvent(LoadingState.LOADED));
				if (currentListReceiver == this) {
					updateRowCount(response.size(), true);
					updateRowData(cellTable, 0, response);
				}
				if (postSucces != null)
					postSucces.run();
			}
		};
	}
	
	public void setPreSucces(Runnable preSucces) {
		this.preSucces = preSucces;
	}
	
	public void setPostSucces(Runnable postSucces) {
		this.postSucces = postSucces;
	}
	
	public void setOnFailure(Runnable onFailure) {
		this.onFailure = onFailure;
	}
}
