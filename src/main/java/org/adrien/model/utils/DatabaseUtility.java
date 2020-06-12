package org.adrien.model.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;

public class DatabaseUtility {

        public static ComboPooledDataSource getDataSource() throws PropertyVetoException
        {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setJdbcUrl("jdbc:sqlite:src/main/resources/database/test.db");
            cpds.setUser("");
            cpds.setPassword("");

            // Optional Settings
            cpds.setInitialPoolSize(5);
            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(20);
            cpds.setMaxStatements(100);

            return cpds;
        }

    }


