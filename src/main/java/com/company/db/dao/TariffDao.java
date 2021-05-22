package com.company.db.dao;

import com.company.db.constant.Language;
import com.company.db.entity.Tariff;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TariffDao {

    List<Tariff> getTariffsByServiceId(int id, Language lang) throws SQLException;

    Tariff getTariffById(int id) throws SQLException;

    Map<Language,Tariff> getTariffInfoByTariffId(int id) throws SQLException;


    List<Tariff> getAllTariffs(Language lang) throws SQLException;

    List<Tariff> getAllTariffs(Language lang,int page, int size) throws SQLException;

    int getTariffsNumber() throws SQLException;


    void updateTariffAndTariffInfo(Tariff tariff, Map<Language, Tariff> tariffInfoMap) throws SQLException;

    void insertTariffAndTariffInfo(Tariff tariff, Map<Language, Tariff> tariffInfoMap) throws SQLException;

    void deleteTariff(int id) throws SQLException;

    List<Tariff> getTariffsByServiceId(int serviceId, Language language, String order, String orderBy) throws SQLException;

}
