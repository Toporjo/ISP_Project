package com.company.web.filter;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.servlet.*;
import java.io.IOException;

/**
 * Request encoding filter.
 */


public class EncodingFilter implements Filter {

	private static final Logger logger = Logger.getLogger(EncodingFilter.class);

	private String encoding;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		logger.debug("Starting encoding filter");

		String requestEncoding = request.getCharacterEncoding();
		if (requestEncoding == null) {
			request.setCharacterEncoding(encoding);
		}
		logger.debug("Filter finished");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		logger.debug("Initializing encoding filter");
		encoding = fConfig.getInitParameter("encoding");
		logger.debug("Initialization done");
	}

}