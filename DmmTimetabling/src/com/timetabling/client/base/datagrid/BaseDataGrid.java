package com.timetabling.client.base.datagrid;

import com.github.gwtbootstrap.client.ui.DataGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.requestfactory.shared.EntityProxy;

public abstract class BaseDataGrid<E extends EntityProxy> extends DataGrid<E> {
	
    private static final int DEFAULT_PAGESIZE = Integer.MAX_VALUE;
    
	public interface GwtCssDataGridResources extends DataGrid.Resources {
		@Source({ Style.DEFAULT_CSS, "DataGrid.css" })
		Style dataGrid();
	}
	public static final GwtCssDataGridResources gwtCssDataGridResources = GWT.create(GwtCssDataGridResources.class);
    static { gwtCssDataGridResources.dataGrid().ensureInjected(); }

//    public interface BaseDataGridResource extends DataGrid.Resources {
//        public interface DataGridStyle extends DataGrid.Style { };
//        public BaseDataGridResource INSTANCE = GWT.create( BaseDataGridResource.class );
//        @Source("DataGrid.css") DataGridStyle cellTableStyle();
//    };
	
    protected abstract void initiateColumns();
    protected abstract BaseAsyncDataProvider<E> createDataProvider();

    protected final SingleSelectionModel<E> selectionModel;
    protected final BaseAsyncDataProvider<E> dataProvider;
    protected DataSelectionListener<E> selectionListener;

    private boolean dataLoadInitiated = false;

	public BaseDataGrid() {
        super( DEFAULT_PAGESIZE, gwtCssDataGridResources );
		dataProvider = createDataProvider();
		selectionModel = new SingleSelectionModel<E>(dataProvider.getKeyProvider());
		initiateSelectionModel();
		initiateColumns();
		initiateTable();
	}
	
	public BaseDataGrid(DataSelectionListener<E> selectionListener) {
		this();
		this.selectionListener = selectionListener;
	}
	
	private void initiateSelectionModel() {
		selectionModel.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				fireEvent(event);
			}
		});
        selectionModel.addSelectionChangeHandler( new Handler() {
            @Override
            public void onSelectionChange( SelectionChangeEvent event ) {
            	E selectedEntity = selectionModel.getSelectedObject();
            	if (selectedEntity != null && selectionListener != null)
            		selectionListener.onRowSelected(selectedEntity);
            }
        });
		this.setSelectionModel(selectionModel);
		this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	}
    
    public void initiateTable() {
        this.setPageSize( DEFAULT_PAGESIZE );
        dataProvider.addDataDisplay( this );
        dataLoadInitiated = true;
    }

    public void addColumn( Column<E, ?> col, String header, String width ) {
        addColumn( col, header );
        setColumnWidth( col, width );
    }

    public void reload() {
        if ( !dataLoadInitiated )
            initiateTable();
        selectionModel.setSelected( null, true );
        dataProvider.update();
    }

    public E getSelected() {
        return selectionModel.getSelectedObject();
    }

    @Override
    public ProvidesKey<E> getKeyProvider() {
        return dataProvider.getKeyProvider();
    }

    public BaseAsyncDataProvider<E> getProvider() {
        return dataProvider;
    }
	
	public void setPreSucces(Runnable preSucces) {
		dataProvider.setPreSucces(preSucces);
	}
	
	public void setPostSucces(Runnable postSucces) {
		dataProvider.setPostSucces(postSucces);
	}
	
	public void setOnFailure(Runnable onFailure) {
		dataProvider.setOnFailure(onFailure);
	}
}
