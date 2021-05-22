package com.company.web.command.common;


import com.company.Paths;
import com.company.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User logout command
 *
 */
public class LogoutCommand extends Command {

	private static final Logger logger = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Starting logout command");
		String forward = Paths.COMMAND_HOME;
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		logger.debug("Logout done. Sending redirect");

		response.sendRedirect(forward);
		return null;
	}

}