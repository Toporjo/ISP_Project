package com.company.db.dao.mysql;

import com.company.db.DBUtil;
import com.company.db.EntityMapper;
import com.company.db.Fields;
import com.company.db.constant.Language;
import com.company.db.dao.TariffDao;
import com.company.db.entity.Tariff;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class MySqlTariffDao implements TariffDao {

    private static MySqlTariffDao instance;

    public static synchronized MySqlTariffDao getInstance() {
        if (instance == null) {
            instance = new MySqlTariffDao();
        }
        return instance;
    }

    private MySqlTariffDao() {
    }

    @Override
    public List<Tariff> getTariffsByServiceId(int id, Language lang) throws SQLException {
        String query = "select * from tariffs\n" +
                " inner join tariff_info on tariff_info.tariff_id = tariffs.id\n" +
                " where service_id= ? and language_id = ?; ";
        PreparedStatement statement;
        ResultSet rs;
        List<Tariff> tariffs = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            statement = con.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, lang.getId());

            rs = statement.executeQuery();
            TariffAndInfoMapper mapper = new TariffAndInfoMapper();

            while (rs.next()) {
                tariffs.add(mapper.mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return tariffs;
    }

    @Override
    public Tariff getTariffById(int id) throws SQLException {
        String query = "SELECT * FROM tariffs\n" +
                        "where id=?;";
        PreparedStatement statement;
        ResultSet rs;
        Tariff tariff = null;
        try (Connection con = DBUtil.getConnection()) {
            statement = con.prepareStatement(query);
            statement.setInt(1, id);

            rs = statement.executeQuery();
            TariffMapper mapper = new TariffMapper();

            if (rs.next()) {
                tariff = mapper.mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return tariff;
    }


    @Override
    public Map<Language, Tariff> getTariffInfoByTariffId(int id) throws SQLException {
        String query = "select ti.tariff_name,ti.description,ti.language_id from tariffs t\n" +
                "join tariff_info ti on ti.tariff_id = t.id\n" +
                "where t.id = ?;";
        PreparedStatement statement;
        ResultSet rs;
        Map<Language, Tariff> tariffInfo = new LinkedHashMap<>();
        try (Connection con = DBUtil.getConnection()) {
            statement = con.prepareStatement(query);
            statement.setInt(1, id);

            rs = statement.executeQuery();

            while (rs.next()) {
                Tariff tariff = new Tariff();
                tariff.setTariffName(rs.getString(Fields.TARIFF_NAME));
                tariff.setDescription(rs.getString(Fields.TARIFF_DESCRIPTION));
                tariffInfo.put(Language.values()[rs.getInt(Fields.LANGUAGE_ID)],
                        tariff);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return tariffInfo;
    }

    @Override
    public List<Tariff> getAllTariffs(Language lang) throws SQLException {
        String query = "select * from tariffs\n" +
                " inner join tariff_info on tariff_info.tariff_id = tariffs.id\n" +
                " where language_id = ?; ";
        PreparedStatement statement;
        ResultSet rs;
        List<Tariff> events = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            statement = con.prepareStatement(query);
            statement.setInt(1, lang.getId());

            rs = statement.executeQuery();
            TariffAndInfoMapper mapper = new TariffAndInfoMapper();

            while (rs.next()) {
                events.add(mapper.mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return events;
    }

    @Override
    public void updateTariffAndTariffInfo(Tariff tariff, Map<Language, Tariff> tariffInfoMap)throws SQLException {
        String query = "UPDATE tariffs SET price = ?, discount = ? WHERE (id = ?);";
        PreparedStatement statement;
        Connection con;
        con = DBUtil.getConnection();
        con.setAutoCommit(false);
        try {
            statement = con.prepareStatement(query);
            statement.setDouble(1, tariff.getPrice());
            statement.setInt(2, tariff.getDiscount());
            statement.setInt(3, tariff.getId());
            statement.executeUpdate();
            updateTariffInfoByTariffId(tariff.getId(),con,tariffInfoMap);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            con.rollback();
            throw throwables;
        } finally {

            con.close();
        }
    }

    @Override
    public void insertTariffAndTariffInfo(Tariff tariff, Map<Language, Tariff> tariffInfoMap) throws SQLException{
        String query = "INSERT INTO tariffs (price, discount, service_id) VALUES (?, ?, ?);";
        PreparedStatement statement;
        Connection con;
        ResultSet rs;

        con = DBUtil.getConnection();
        con.setAutoCommit(false);
        try {
            statement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, tariff.getPrice());
            statement.setInt(2, tariff.getDiscount());
            statement.setInt(3, tariff.getServiceId());
            if(statement.executeUpdate() > 0){
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    tariff.setId(rs.getInt(1));
                }
            }
            insertTariffInfoByTariffId(tariff.getId(),con,tariffInfoMap);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            con.rollback();
            throw throwables;
        } finally {

            con.close();
        }
    }

    @Override
    public void deleteTariff(int id) throws SQLException {
        String query = "delete from tariffs where id = ?;";
        PreparedStatement statement;
        try(Connection con = DBUtil.getConnection()) {
            statement = con.prepareStatement(query);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    @Override
    public List<Tariff> getTariffsByServiceId(int serviceId, Language language, String order, String orderBy) throws SQLException {
        String query = "select * from tariffs\n" +
                "inner join tariff_info on tariff_info.tariff_id = tariffs.id\n" +
                "where service_id= ? and language_id = ?\n" +
                "order by %s %s; ";
        query = String.format(query, orderBy,order);
        PreparedStatement statement;
        ResultSet rs;
        List<Tariff> tariffs = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            statement = con.prepareStatement(query);
            statement.setInt(1, serviceId);
            statement.setInt(2, language.ordinal());

            rs = statement.executeQuery();
            TariffAndInfoMapper mapper = new TariffAndInfoMapper();

            while (rs.next()) {
                tariffs.add(mapper.mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return tariffs;
    }

    private void insertTariffInfoByTariffId(int tariffId, Connection con,
                                            Map<Language, Tariff> tariffInfoMap) throws SQLException {
        String query = "INSERT INTO tariff_info (language_id, tariff_id, tariff_name, description) VALUES (?, ?, ?, ?);";
        PreparedStatement statement;
        try {
            for (Language lan:Language.values()){
                Tariff tariffInfo = tariffInfoMap.get(lan);
                statement = con.prepareStatement(query);
                statement.setInt(1,lan.ordinal());
                statement.setInt(2,tariffId);
                statement.setString(3,tariffInfo.getTariffName());
                statement.setString(4,tariffInfo.getDescription());
                statement.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }



    private void updateTariffInfoByTariffId(int tariffId, Connection connection,
                                            Map<Language, Tariff> tariffInfoMap) throws SQLException{
        String query = "UPDATE tariff_info SET tariff_name = ?, description = ? WHERE (tariff_id=? and language_id=?);";
        PreparedStatement statement;
        try{

            for (Language lan:Language.values()){
                Tariff tariffInfo = tariffInfoMap.get(lan);
                statement = connection.prepareStatement(query);
                statement.setString(1,tariffInfo.getTariffName());
                statement.setString(2,tariffInfo.getDescription());
                statement.setInt(3,tariffId);
                statement.setInt(4,lan.ordinal());
                statement.executeUpdate();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }


    private static class TariffAndInfoMapper implements EntityMapper<Tariff> {

        @Override
        public Tariff mapRow(ResultSet rs) {
            try {
                Tariff tariff = new Tariff();
                tariff.setId(rs.getInt(Fields.ID));
                tariff.setTariffName(rs.getString(Fields.TARIFF_NAME));
                tariff.setPrice(rs.getBigDecimal(Fields.TARIFF_PRICE).doubleValue());
                tariff.setDescription(rs.getString(Fields.TARIFF_DESCRIPTION));
                tariff.setDiscount(rs.getInt(Fields.TARIFF_DISCOUNT));
                tariff.setServiceId(rs.getInt(Fields.TARIFF_SERVICE_ID));
                return tariff;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class TariffMapper implements EntityMapper<Tariff> {

        @Override
        public Tariff mapRow(ResultSet rs) {
            try {
                Tariff tariff = new Tariff();
                tariff.setId(rs.getInt(Fields.ID));
                tariff.setPrice(rs.getBigDecimal(Fields.TARIFF_PRICE).doubleValue());
                tariff.setDiscount(rs.getInt(Fields.TARIFF_DISCOUNT));
                tariff.setServiceId(rs.getInt(Fields.TARIFF_SERVICE_ID));
                return tariff;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

