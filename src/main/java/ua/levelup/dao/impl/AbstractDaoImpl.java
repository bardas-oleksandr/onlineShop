package ua.levelup.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Properties;

public class AbstractDaoImpl {
    
    @Autowired
    protected Properties messagesProperties;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
