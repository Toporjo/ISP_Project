package com.company.web.command.admin;

import com.company.Paths;
import com.company.db.constant.Language;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.TariffDao;
import com.company.db.entity.Tariff;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class NewTariffCommand extends Command {

    private static final Logger logger = LogManager.getLogger(NewTariffCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        TariffDao tariffDao = DaoFactory.getDaoFactory().getTariffDao();

        HttpSession session = request.getSession();
        String forward = Paths.PAGE_ERROR_PAGE;

        String[] names = request.getParameterValues("names");
        String[] descriptions = request.getParameterValues("descriptions");
        String[] langs = request.getParameterValues("language");
        String price = request.getParameter("price");
        String discount = request.getParameter("discount");
        String serviceId = request.getParameter("serviceId");

        System.out.println(Arrays.toString(names));
        System.out.println(Arrays.toString(descriptions));
        System.out.println(Arrays.toString(langs));
        Tariff tariff = new Tariff();
        tariff.setDiscount(Integer.parseInt(discount));
        tariff.setPrice(Double.parseDouble(price));
        tariff.setServiceId(Integer.parseInt(serviceId));
        Map<Language,Tariff> tariffInfoMap = new HashMap<>();
        for (int a=0;a<names.length;a++){
            Tariff tariffInfo = new Tariff();
            tariffInfo.setTariffName(names[a]);
            tariffInfo.setDescription(descriptions[a]);
            tariffInfoMap.put(Language.values()[Integer.parseInt(langs[a])],tariffInfo);
        }

        try {
            tariffDao.insertTariffAndTariffInfo(tariff,tariffInfoMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        forward = Paths.PAGE_TARIFF_MANAGER;
        response.sendRedirect(Paths.COMMAND_TARIFF_MANAGER);
        return null;
    }
}
