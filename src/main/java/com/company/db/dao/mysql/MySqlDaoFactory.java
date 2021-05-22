package com.company.db.dao.mysql;


import com.company.db.dao.*;

/**
 * A realization of DAO factory for MySQL DAO objects
 */

public class MySqlDaoFactory extends DaoFactory {

    private static MySqlDaoFactory instance;

    private MySqlDaoFactory(){}

    public synchronized static DaoFactory getInstance() {
        if(instance == null){
            instance = new MySqlDaoFactory();
        }
        return instance;
    }

    @Override
    public UserDao getUserDao() {
        return MySqlUserDao.getInstance();
    }

    @Override
    public TariffDao getTariffDao() {
        return MySqlTariffDao.getInstance();
    }

}
