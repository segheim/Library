package com.epam.jwd.library.connection;

import java.sql.Connection;

public interface ConnectionPool {

    boolean init();

    boolean shoutDown();

    Connection takeConnection();

    void returnConnection(Connection connection);

}
