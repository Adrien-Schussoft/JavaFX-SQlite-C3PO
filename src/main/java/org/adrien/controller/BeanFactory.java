package org.adrien.controller;

import org.adrien.model.dao.IDao;
import org.adrien.model.entity.IClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanFactory {

    public BeanFactory(){
    }

    private static IDao<IClient> clientDaoFactory() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String className = "org.adrien.model.dao.ClientDAO";
        Class cClientDAO = Class.forName(className);
        IDao IDao = (IDao) cClientDAO.getDeclaredConstructor().newInstance();
        return IDao;
    }

    private static IClient clientFactory() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String className = "org.adrien.model.entity.Client";
        Class cClient = Class.forName(className);
        IClient client = (IClient) cClient.getDeclaredConstructor().newInstance();
        return client;
    }

    private static Method findById() throws ClassNotFoundException, NoSuchMethodException {
        String className = "org.adrien.model.dao.ClientDAO";
        Class cClientDAO = Class.forName(className);
        Method methodFindById = cClientDAO.getDeclaredMethod("findById",Integer.TYPE) ;
        return methodFindById;
    }

    private static Method insert() throws ClassNotFoundException, NoSuchMethodException {
        String className = "org.adrien.model.dao.ClientDAO";
        Class cClientDAO = Class.forName(className);
        Method methodInsert = cClientDAO.getDeclaredMethod("insert", IClient.class) ;
        return methodInsert;
    }

    private static Method update() throws ClassNotFoundException, NoSuchMethodException {
        String className = "org.adrien.model.dao.ClientDAO";
        Class cClientDAO = Class.forName(className);
        Method methodUpdate = cClientDAO.getDeclaredMethod("update",IClient.class) ;
        return methodUpdate;
    }

    private static Method delete() throws ClassNotFoundException, NoSuchMethodException {
        String className = "org.adrien.model.dao.ClientDAO";
        Class cClientDAO = Class.forName(className);
        Method methodDelete = cClientDAO.getDeclaredMethod("delete",IClient.class) ;
        return methodDelete;
    }

    private static Method List() throws ClassNotFoundException, NoSuchMethodException {
        String className = "org.adrien.model.dao.ClientDAO";
        Class cClientDAO = Class.forName(className);
        Method method = cClientDAO.getMethod("list");
        return method;
    }

    public static IClient getClient() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clientFactory();
    }

    public static Method getFindById() throws NoSuchMethodException, ClassNotFoundException {
        return findById();
    }

    public static Method getList() throws NoSuchMethodException, ClassNotFoundException {
        return List();
    }

    public static IDao<IClient> getClientDao() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clientDaoFactory();
    }
    public static Method getInsert() throws NoSuchMethodException, ClassNotFoundException {
        return insert();
    }

    public static Method getUpdate() throws NoSuchMethodException, ClassNotFoundException {
        return update();
    }

    public static Method getDelete() throws NoSuchMethodException, ClassNotFoundException {
        return delete();
    }
}
