package com.company.web.command.common;

import com.company.web.WebWriter;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.util.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TariffListCommand extends Command {
    private static final Logger logger = LogManager.getLogger(ServiceCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=sample.txt");
        WebWriter webWriter = new WebWriter();

        webWriter.write("test output string",response.getOutputStream());

        return null;
    }
}
