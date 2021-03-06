package com.yaourtprod.corsoiseng;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CookieInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CookieInterceptor.class);

	public static final String CORSOISENG_COOKIE = "corsoisengauth";

	private final  Service service;
	
	@Autowired
	public CookieInterceptor(final Service service) {
		this.service = service;
	}
	
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) throws Exception {
		LOGGER.debug("CookieInterceptor preHandle ...");
		// all non-root requests get analyzed
		Cookie[] cookies = request.getCookies();

		if (!ObjectUtils.isEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				if (CORSOISENG_COOKIE.equals(cookie.getName())) {
					LOGGER.debug("Cookie found !");
					final String suuid = cookie.getValue();
					if(null != suuid) {
						UUID uuid = null;
						try {
							uuid = UUID.fromString(suuid);
						} catch(final IllegalArgumentException iae){
							LOGGER.debug("This cookie value is not a UUID : {}", suuid);
						}
						
						if(null != uuid) {
							final Corsoiseur c = service.get(uuid);
							if (c != null) {
								LOGGER.debug("User found");
								CorsoiseSecurity.setUser(c.getPseudo(), uuid);
							} else {
								LOGGER.debug("User not found");
							}
						}
					}
				}
			}
		}
		
		LOGGER.debug("Cookie processing finished");
		return true;
	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex)
			throws Exception {
		CorsoiseSecurity.clean();
	}
}