package com.company.web.command;


import com.company.Paths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Default command for handling wrong request commands
 */

public class NoCommand extends Command {

	private static final Logger logger = Logger.getLogger(NoCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Starting command");
		String errorMessage = Paths.ERROR_NO_SUCH_COMMAND;
		request.setAttribute("errorMessage", errorMessage);


		logger.debug("Done. Sending forward");
		return Paths.PAGE_ERROR_PAGE;
	}

}