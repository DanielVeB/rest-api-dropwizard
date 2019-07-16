package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.exceptions.AppException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class SortingConverterTest {

    private SortingConverter sortingConverter;

    @Before
    public void init() {
        sortingConverter = new SortingConverter();
    }

    @Test
    public void getSorts_IfIsCorrectPassedString_ThenReturnHashMapWithSorts() throws AppException {
        String sortString = "firstName:asc,lastName:desc,email:asc";
        HashMap<String, String> sorts = sortingConverter.getSorts(sortString);

        assert sorts != null;
        Assert.assertEquals("size of hashmap shoul be 3", 3, sorts.size());

        Assert.assertEquals("Value of firstName should be asc", "asc", sorts.get("firstName"));
    }

    @Test(expected = AppException.class)
    public void getSorts_IfIsSplitByDots_ThenThrowIllegalArgumentException() throws AppException {
        String sortString = "firstName:asc.lastName:desc.email:asc";
        HashMap<String, String> sorts = sortingConverter.getSorts(sortString);

    }

}