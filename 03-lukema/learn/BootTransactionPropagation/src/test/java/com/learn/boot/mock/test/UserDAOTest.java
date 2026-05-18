package com.learn.boot.mock.test;


/*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.engine.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Fields;
*/

public class UserDAOTest {

    /*
    @Mock
    DataSource mockDataSource;

    @Mock
    Connection mockConn;

    @Mock
    PreparedStatement mockPreparedStmnt;

    @Mock
    ResultSet mockResultSet;
    int userId = 100;

    @BeforeClass
    public static void setUpClass()
        throws Exception {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp()
        throws SQLException {
        Mockito.when(mockDataSource.getConnection()).thenReturn(mockConn);
        Mockito.when(mockDataSource.getConnection(anyString(), anyString())).thenReturn(mockConn);
        Mockito.doNothing().when(mockConn).commit();
        Mockito.when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStmnt);
        Mockito.doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        Mockito.when(mockPreparedStmnt.execute()).thenReturn(Boolean.TRUE);
        Mockito.when(mockPreparedStmnt.getGeneratedKeys()).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.when(mockResultSet.getInt(Fields.GENERATED_KEYS)).thenReturn(userId);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateWithNoExceptions()
        throws SQLException {

        UserDAO instance = new UserDAO(mockDataSource);
        instance.create(new User());

        //verify and assert
        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.anyString(), Mockito.anyInt());
        Mockito.verify(mockPreparedStmnt, Mockito.times(6)).setString(Mockito.anyInt(), Mockito.anyString());
        Mockito.verify(mockPreparedStmnt, Mockito.times(1)).execute();
        Mockito.verify(mockConn, Mockito.times(1)).commit();
        Mockito.verify(mockResultSet, Mockito.times(2)).next();
        Mockito.verify(mockResultSet, Mockito.times(1)).getInt(Fields.GENERATED_KEYS);
    }

    @Test(expected = SQLException.class)
    public void testCreateWithPreparedStmntException()
        throws SQLException {

        //mock
        when(mockConn.prepareStatement(Mockito.anyString(), Mockito.anyInt())).thenThrow(new SQLException());

        try {
            UserDAO instance = new UserDAO(mockDataSource);
            instance.create(new User());
        } catch (SQLException se) {
            //verify and assert
            Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.anyString(), Mockito.anyInt());
            Mockito.verify(mockPreparedStmnt, Mockito.times(0)).setString(Mockito.anyInt(), Mockito.anyString());
            Mockito.verify(mockPreparedStmnt, Mockito.times(0)).execute();
            Mockito.verify(mockConn, Mockito.times(0)).commit();
            Mockito.verify(mockResultSet, Mockito.times(0)).next();
            Mockito.verify(mockResultSet, Mockito.times(0)).getInt(Fields.GENERATED_KEYS);
            throw se;
        }

    }
    */

}
