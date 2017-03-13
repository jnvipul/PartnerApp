package com.restaurant.partnerapp.tables.presenters;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.restaurant.partnerapp.tables.network.TableDataService;
import com.restaurant.partnerapp.tables.ui.ITableListView;
import com.restaurant.partnerapp.utility.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vJ on 3/14/17.
 */
public class TableListPresenterTest {

    @Mock
    Cursor mockedCursor;

    @Mock
    TableDataService mockedService;

    TableListPresenter presenter;

    @Mock
    ITableListView mockedView;

    @Mock
    SQLiteDatabase mDatabase;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        presenter = new TableListPresenter(mockedService, mDatabase);
        presenter.attachView(mockedView);
    }

    @Test
    public void fetchTablesFromInternetIfRequiredTest() throws Exception {
        when(mockedCursor.getCount()).thenReturn(2);
        doNothing().when(mockedView).hideProgressBar();
        doNothing().when(mockedView).showTableList(mockedCursor);

        presenter.fetchTablesFromInternetIfRequired(mockedCursor);

        verify(mockedView).hideProgressBar();
        verify(mockedView).showTableList(mockedCursor);

    }

}