package org.adrien.model.dao;

import org.adrien.model.entity.Client;
import org.adrien.model.entity.IClient;

import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDao<T> {

    public void delete(IClient id) throws SQLException;
    public T findById(int id) throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
    public void insert(IClient client) throws SQLException;

    public  ArrayList<IClient> list() throws SQLException, PropertyVetoException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;



    public void update(IClient client) throws SQLException;
}
