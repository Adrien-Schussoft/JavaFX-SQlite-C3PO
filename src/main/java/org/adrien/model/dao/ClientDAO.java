package org.adrien.model.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.adrien.model.entity.IClient;
import org.adrien.model.utils.DatabaseUtility;

import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO extends DatabaseUtility implements IDao<IClient> {

    private Connection conn = null;
    ComboPooledDataSource dataSource = this.getDataSource();

    public ClientDAO() throws PropertyVetoException {
    }

    /**
     * Create a new client in database.
     * @param cli
     */
    @Override
    public void insert(IClient cli) throws SQLException {

        try {
            conn = dataSource.getConnection();
            String query = "INSERT INTO user (name, firstname, city) VALUES (?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, cli.getNom());
            stm.setString(2, cli.getPrenom());
            stm.setString(3, cli.getVille());
            stm.execute();
            stm.close();
            conn.close();
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }
    }

    /**
     * update an existing client in database.
     * @param cli
     */
    @Override
    public void update(IClient cli) throws SQLException {

        try{
            conn = dataSource.getConnection();
            String query = "UPDATE user SET name = (?),firstname = (?),city = (?) WHERE id = (?)";
            PreparedStatement pmt = conn.prepareStatement(query);
            pmt.setString(1, cli.getNom());
            pmt.setString(2, cli.getPrenom());
            pmt.setString(3, cli.getVille());
            pmt.setInt(4,cli.getId());
            pmt.executeUpdate();
            pmt.close();
            conn.close();
        }catch (SQLException e){
            conn.rollback();
            e.printStackTrace();
        }
    }

    /**
     * delete an existing client in database.
     * @param cli
     */
    @Override
    public void delete(IClient cli) throws SQLException {

        try {
            conn = dataSource.getConnection();
            String query = "DELETE FROM user WHERE id = (?)";
            PreparedStatement pmt = conn.prepareStatement(query);
            pmt.setInt(1,cli.getId());
            pmt.execute();
            pmt.close();
            conn.close();
        }catch (SQLException e){
            conn.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Search an existing client by id in database.
     * @param id
     * @return client
     */
    @Override
    public IClient findById(int id) throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        conn = dataSource.getConnection();
        ResultSet rs = null;
        //IClient iclient = new Client();
        IClient iclient = null;
        String className = "org.adrien.model.entity.Client";
        Class cClient = Class.forName(className);
        iclient = (IClient) cClient.getDeclaredConstructor().newInstance();

        try {
            String query = "SELECT * FROM user WHERE id = (?)";
            PreparedStatement stm = conn.prepareStatement(query);

            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                iclient.setId(rs.getInt("id"));
                iclient.setNom(rs.getString("name"));
                iclient.setPrenom(rs.getString("firstname"));
                iclient.setVille(rs.getString("city"));
            }
            else{
                iclient.setId(0);
                iclient.setNom("");
                iclient.setPrenom("");
                iclient.setVille("");
            }
            rs.close();
            stm.close();
            conn.close();
        }
        catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }
        return iclient;
    }

    /**
     * Return a list of  all clients in database.
     * @return resultat
     */

    public  ArrayList<IClient> list() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        conn = dataSource.getConnection();
        ResultSet rs = null;
        IClient client;
        ArrayList<IClient> clientArrayList = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            PreparedStatement pmt = conn.prepareStatement(query);
            rs = pmt.executeQuery();
            while (rs.next()) {
                String className = "org.adrien.model.entity.Client";
                Class cClient = Class.forName(className);
                client = (IClient) cClient.getDeclaredConstructor().newInstance();
                client.setId(rs.getInt(1));
                client.setNom(rs.getString(2));
                client.setPrenom(rs.getString(3));
                client.setVille(rs.getString(4));
                clientArrayList.add(client);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
        return clientArrayList;
    }
}
