package com.comarch.danielkurosz.auth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuthUserTest {

    AuthUser authUser;

    @Before
    public void setUp() {
        authUser = new AuthUser("test");
    }

    @Test
    public void constructorTest() {
        Assert.assertEquals(AuthUser.class, authUser.getClass());
    }
}