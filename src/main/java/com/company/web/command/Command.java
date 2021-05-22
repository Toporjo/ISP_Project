package com.company.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;


/**
 *Abstract class for the command pattern implementation
 */

public abstract class Command implements Serializable {	
	private static final long serialVersionUID = 8879403039606311780L;

	/**
	 * Method for executing user requests
	 * @param request http request
	 * @param response http response
	 * @return a path to jsp page to be displayed
	 * @throws IOException
	 * @throws ServletException
	 */
	public abstract String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException;
	
	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}