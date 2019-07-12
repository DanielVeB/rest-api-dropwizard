package com.comarch.danielkurosz.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SortingConverterTest {

    @Test
    public void getSorts_IfIsCorrectPassedString_ThenReturnHashMapWithSorts(){
        String sortString = "firstName:asc,lastName:desc,email:asc";
        HashMap<String,String> sorts = SortingConverter.getSorts(sortString);

        assert sorts != null;
        Assert.assertEquals("size of hashmap shoul be 3",3,sorts.size());

        Assert.assertEquals("Value of firstName should be asc","asc",sorts.get("firstName"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSorts_IfIsSplitByDots_ThenThrowIllegalArgumentException()throws IllegalArgumentException{
        String sortString = "firstName:asc.lastName:desc.email:asc";
        HashMap<String,String> sorts = SortingConverter.getSorts(sortString);

    }

}