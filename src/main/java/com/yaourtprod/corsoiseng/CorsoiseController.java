package com.yaourtprod.corsoiseng;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CorsoiseController {
	@Autowired
	private final Service service;
	
	@Autowired
	public CorsoiseController(final Service service) {
		this.service = service;
	}
	
	@RequestMapping("/")
	public String root() {
		return "index";
	}
	
	@RequestMapping("/signIn")
	public String signIn(@RequestParam(required = true) final String pseudo, final Model model, final HttpServletResponse response) throws Exception {
		final String lpseudo = service.normalizePseudo(pseudo);
		final UUID uid = service.uuid(pseudo); 

		addAuthCookie(uid, lpseudo, response);
		return "redirect:/";
	}
	
	private void addAuthCookie(final UUID uuid, final String pseudo, HttpServletResponse response) {
		CorsoiseSecurity.setUser(pseudo, uuid);

		Cookie cookie = new Cookie(CookieInterceptor.CORSOISENG_COOKIE, uuid.toString());
		cookie.setComment("Corsoise NG");
		// cookie valid for up to 4 weeks
		cookie.setMaxAge(60 * 60 * 24 * 7 * 4);
		response.addCookie(cookie);
	}
}
