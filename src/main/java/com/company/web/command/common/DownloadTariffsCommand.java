package com.company.web.command.common;

import com.company.Paths;
import com.company.Util;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.TariffDao;
import com.company.db.dao.UserDao;
import com.company.db.entity.Tariff;
import com.company.db.entity.User;
import com.company.jstl.PrintService;
import com.company.web.WebWriter;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DownloadTariffsCommand extends Command {
    private static final Logger logger = LogManager.getLogger(DownloadTariffsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting DownloadTariffsCommand command ");
        String service = request.getParameter("service");
        HttpSession session = request.getSession();
        TariffDao tariffDao = DaoFactory.getDaoFactory().getTariffDao();
        List<Tariff> tariffs;
        try {
            int serviceId = Integer.parseInt(service);
            String fileNameBuilder =
                    Util.readProperty(Paths.RESOURCE_TARIFFS_FILE_NAME_FRAGMENT, Util.determineLocale(session, request)) +
                    ' ' + '(' +
                    Util.readProperty(PrintService.print(serviceId), Util.determineLocale(session, request)) +
                    ')' + ".txt";

            String fileName = URLEncoder.encode(fileNameBuilder,"UTF-8");
            fileName = fileName.replace('+',' ');

            tariffs = tariffDao.getAllTariffs(Util.determineLocale(session,request));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            response.setHeader("Content-disposition", "attachment; filename=\""+fileName+"\"");
            WebWriter webWriter = new WebWriter();

            webWriter.write(Util.formatTariffs(tariffs),response.getOutputStream());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Paths.PAGE_ERROR_PAGE;
        }

        logger.debug("Sending forward");
        return null;
    }
}
