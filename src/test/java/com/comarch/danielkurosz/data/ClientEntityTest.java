package com.comarch.danielkurosz.data;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class ClientEntityTest {

    @Test
    public void ShouldReturnCorrectDate() throws ParseException {

        String string = "January 2, 2010";
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = format.parse(string);

        ClientEntity clientEntity = new ClientEntity.ClientEntityBuilder().creationDate(date).build();

        String stringDate = clientEntity.getCreationDate();

        Assert.assertEquals("date should be the same","2010-01-02T00:00:00Z",stringDate);

    }
}