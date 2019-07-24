package com.comarch.danielkurosz.logic;

import java.util.Date;

public interface IBirthdayProviderLogic {
    void provide(String operatorId, Date now);
}
