package com.company.db.dao;


import com.company.db.dao.mysql.MySqlDaoFactory;

/**
 *  Class for DAO pattern realization
 */
public abstract class DaoFactory {

    public static final String MYSQL = "MySQL";


    public static DaoFactory getDaoFactory(){
        return MySqlDaoFactory.getInstance();
    }

    public static DaoFactory getDaoFactory(String name) {
        if (MYSQL.equalsIgnoreCase(name)) {
            return MySqlDaoFactory.getInstance();
        }
        throw new RuntimeException("Unknown factory");
    }

    public abstract UserDao getUserDao();

    public abstract TariffDao getTariffDao();


}
