package com.company.web.command.form;

import com.company.Paths;
import com.company.db.constant.Language;
import com.company.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFormCommand extends Command {

    private static final Logger logger = Logger.getLogger(LoginFormCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting command");
        String forward = Paths.PAGE_LOGIN;


        logger.debug("Done. Sending forward");
        return forward;
    }
}
