package com.learn.mockito;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;


@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    private static final Logger LOG = LogManager.getLogger();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    MyClass myClass;

    @Mock
    Iterator<String> it;

    @Spy
    List<String> spiedList;

    @Mock
    List<String> mockedList;

    @Captor
    ArgumentCaptor<String> argCaptor;

    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    MyDictionary dic;

    @Test
    public void testQuery() {
        // create mock
        // MyClass test = Mockito.mock(MyClass.class);

        // define return value for method getUniqueId()
        Mockito.when(myClass.getUniqueId()).thenReturn(43);

        // use mock in test....
        Assert.assertEquals(myClass.getUniqueId(), 43);

    }

    @Test
    public void testMoreThanOneReturnValue() {
        Mockito.when(it.next()).thenReturn("Mockito").thenReturn("rocks");
        String result = it.next() + " " + it.next();
        //assert
        Assert.assertEquals("Mockito rocks", result);
    }

    @Test
    public void testSpy() {
        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        LOG.info("spiedList.size() = " + spiedList.size());
        Mockito.verify(spiedList, Mockito.times(1)).size();

        Mockito.doReturn(100).when(spiedList).size();
        Assert.assertEquals(100, spiedList.size());
        Mockito.verify(spiedList, Mockito.times(2)).size();
        Mockito.verify(spiedList, Mockito.never()).clear();
    }

    @Test
    public void whenUseCaptorAnnotation_thenTheSam() {
        mockedList.add("one");
        Mockito.verify(mockedList).add(argCaptor.capture());

        Assert.assertEquals("one", argCaptor.getValue());
    }

    @Test
    public void whenUseInjectMocksAnnotation_thenCorrect() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

        Assert.assertEquals("aMeaning", dic.getMeaning("aWord"));
    }
}
