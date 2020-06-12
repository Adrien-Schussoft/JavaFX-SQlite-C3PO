package org.adrien.model.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.*;

public class SQliteCreateDb {

    public SQliteCreateDb() throws PropertyVetoException {
        ComboPooledDataSource dataSource = DatabaseUtility.getDataSource();
    }
    public static void createNewDatabase(String fileName) throws PropertyVetoException, SQLException {

        Connection conn = null;
        ComboPooledDataSource dataSource = DatabaseUtility.getDataSource();
        String url = "jdbc:sqlite:src/main/resources/database/" + fileName;
        try {
            if (conn == null) {
                conn = dataSource.getConnection();
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            conn.rollback();
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() throws SQLException, PropertyVetoException {
        // SQLite connection string
        Connection conn = null;
        ComboPooledDataSource dataSource = DatabaseUtility.getDataSource();
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS user (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	firstname text NOT NULL,\n"
                + "	city text NOT NULL\n"
                + ");";
        try {
            // create a new table
            conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            conn.rollback();
            System.out.println(e.getMessage());
        }
    }

    public static void selectAll() throws PropertyVetoException, SQLException {
        Connection conn = null;
        String sql = "SELECT id, name, firstname, city FROM user";
        ComboPooledDataSource dataSource = DatabaseUtility.getDataSource();
        try {
            conn = dataSource.getConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {

                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("firstname") + "\t" +
                        rs.getString("city"));
            }
        } catch (SQLException e) {
            conn.rollback();
            System.out.println(e.getMessage());
        }
    }
}
