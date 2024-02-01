/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.conexion;

import java.sql.SQLException;
import java.util.Properties;
import oracle.jdbc.pool.OracleConnectionCacheManager;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author RGALICIA
 */
public class DataSource {
    
    private static OracleDataSource ods = null;

    private DataSource() {
        createConnectionPool();
    }
    
    private static void createConnectionPool() {
        try {
            ods = new OracleDataSource();

            Properties prop = new Properties();
            prop.setProperty("MinLimit", "10");
            prop.setProperty("MaxLimit", "20");
//            prop.setProperty("AbandonedConnectionTimeout", "60");
//            prop.setProperty("PropertyCheckInterval", "60");

            ods.setURL("jdbc:oracle:thin:@" + "192.168.14.117" + ":" + "1521" + ":xe");
            ods.setUser("operaciones");
            ods.setPassword("gtxgtx01");
            ods.setConnectionCachingEnabled(true);
            ods.setConnectionCacheProperties(prop);
            ods.setConnectionCacheName("CapturadorImagenesCache");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static OracleDataSource getDataSource() {
        if (ods == null) {
            DataSource dataSource = new DataSource();
        }
        try {
            OracleConnectionCacheManager occm = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
            if (occm.getNumberOfActiveConnections("CapturadorImagenesCache") != 0) {
                System.out.println("*********************************************************************************");
                System.out.println("*********************************************************************************");
                System.out.println("******************* EXISTEN " + occm.getNumberOfActiveConnections("CapturadorImagenesCache") + " CONEXIONES ABIERTAS ************************");
                System.out.println("*********************************************************************************");
                System.out.println("*********************************************************************************");
            }
        } catch (SQLException ex) {
        }
        return ods;
    }

    public static void cerrarPool() {
        if (ods != null) {
            try {
                ods.close();
            } catch (SQLException ex) {
            }
            ods = null;
        }
    }
}
