package com.restaurant.partnerapp.tables.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.restaurant.partnerapp.tables.network.TableDataService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;

/**
 * Created by vJ on 3/14/17.
 */
public class TableListInteractorTest {

    @Mock
    TableDataService mockedService;

    TableDataInteractor interactor;

    @Mock
    SQLiteDatabase mDatabase;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        interactor = new TableDataInteractor(mockedService, mDatabase);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void fetchTablesFromInternetTest() throws Exception {
        interactor.fetchTablesFromInternet();
        verify(mockedService).fetchTablesData();
    }

}