package com.comarch.danielkurosz.exceptions;

import com.comarch.danielkurosz.exceptions.mapper.AppExceptionMapper;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class AppExceptionMapperTest {

    private AppExceptionMapper testObject = new AppExceptionMapper();

    @Test
    public void toResponse_WhenExceptionIsPassed_ThenReturnResponse() {
        AppException appException = new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "message", "advice", "link");
        Response response = testObject.toResponse(appException);
        Assert.assertEquals(400, response.getStatus());
    }
}