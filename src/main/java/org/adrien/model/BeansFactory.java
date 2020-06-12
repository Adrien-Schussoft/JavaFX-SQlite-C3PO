package org.adrien.model;

import org.adrien.model.dao.IDao;
import org.adrien.model.entity.Client;
import org.adrien.model.entity.IClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeansFactory {

    public BeansFactory(){
    }

    private static IDao<IClient> clientDaoFactory() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String className = "org.adrien.model.dao.ClientDAO";
        Class cClientDAO = Class.forName(className);
        IDao IDao = (IDao) cClientDAO.getDeclaredConstructor().newInstance();
        return IDao;
    }

    private static Client clientFactory() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String className = "org.adrien.model.entity.Client";
        Class cClient = Class.forName(className);
        Client client = (Client) cClient.getDeclaredConstructor().newInstance();
        return client;
    }

    private static Method findById() throws ClassNotFoundException, NoSuchMethodException {
        String className = "org.adrien.model.dao.ClientDAO";
        Class cClientDAO = Class.forName(className);
        Method methodFindById = cClientDAO.getDeclaredMethod("findById",Integer.TYPE) ;
        return methodFindById;
    }

    private static Method List() throws ClassNotFoundException, NoSuchMethodException {
        String className = "org.adrien.model.dao.ClientDAO";
        Class cClientDAO = Class.forName(className);
        Method method = cClientDAO.getMethod("list");
        return method;
    }

    public static Client getClient() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
}
