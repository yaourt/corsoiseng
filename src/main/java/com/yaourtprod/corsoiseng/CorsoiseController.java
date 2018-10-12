package com.yaourtprod.corsoiseng;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaourtprod.corsoiseng.rss.BMFeed;

@Controller
public class CorsoiseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CorsoiseController.class);
	
	@Autowired
	private final Service service;
	
	@Autowired
	private BMFeed bmfeed;
	
	@Autowired
	public CorsoiseController(final Service service) {
		this.service = service;
	}
	
	public void setBMFeed(final BMFeed bmfeed) {
		this.bmfeed = bmfeed;
	}
	
	@RequestMapping("/")
	public String root() {
		LOGGER.debug("/ called");
		if(CorsoiseSecurity.isSignedIn()) {
			LOGGER.debug("Routing to index");
			return "index";
		} else {
			LOGGER.debug("Forwarding to signIn");
			return "forward:/signIn";
		}
	}
	
	@RequestMapping("/signIn")
	public String signIn(@RequestParam(required = false) final String pseudo, final HttpServletResponse response) throws Exception {
		LOGGER.debug("/signIn called");
		if(null != pseudo) {
			final String lpseudo = service.normalizePseudo(pseudo);
			final UUID uid = service.uuid(pseudo); 
	
			addAuthCookie(uid, lpseudo, response);
			service.create(uid, lpseudo);
			LOGGER.debug("{}:{} created !", uid, lpseudo);
			
			LOGGER.debug("Redirecting to /");
			return "redirect:/";
		} else {
			LOGGER.debug("Routing to signIn");
			return "signIn";
		}
	}
	
	@RequestMapping(value = "/data.json", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public OthersAndMe data() {
		final UUID uuid = CorsoiseSecurity.getUid();
		final OthersAndMe result = service.getAll(uuid);
		
		return result;
		
	}
	
	@RequestMapping(value="/incCount")
	public void incCount() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.incCombienKilEst(uuid);
	}

	@RequestMapping(value="/decCount")
	public void decCount() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.decCombienKilEst(uuid);
	}
	
	@RequestMapping(value="/incPietra")
	public void incPietra() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.incPietra(uuid);
	}

	@RequestMapping(value="/decPietra")
	public void decPietra() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.decPietra(uuid);
	}
	
	@RequestMapping(value="/incTerrine")
	public void incTerrine() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.incTerrine(uuid);
	}

	@RequestMapping(value="/decTerrine")
	public void decTerrine() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.decTerrine(uuid);
	}

	@RequestMapping(value="/incBurger")
	public void incBurger() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.incBurger(uuid);
	}

	@RequestMapping(value="/decBurger")
	public void decBurger() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.decBurger(uuid);
	}

	@RequestMapping(value="/incTiramisu")
	public void incTiramisu() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.incTiramisu(uuid);
	}

	@RequestMapping(value="/decTiramisu")
	public void decTiramisu() throws ExecutionException {
		final UUID uuid = CorsoiseSecurity.getUid();
		service.decTiramisu(uuid);
	}

	@RequestMapping(value="/deleteMe")
	public void deleteMe() {
		final UUID uuid = CorsoiseSecurity.getUid();
		if(null != uuid) {
			service.delete(uuid);
		}
	}
	
	@RequestMapping(value="/ping")
	@ResponseBody
	public String ping() {
		final UUID uuid = CorsoiseSecurity.getUid();
		LOGGER.info("Ping from {}", null != uuid ? uuid : "Anonymous");
		return "pong";
	}

	@RequestMapping(value="/bm.json", method = RequestMethod.GET, produces="application/json")
	public Collection<String> bm() throws XMLStreamException, IOException {
		if(null != bmfeed) {
			return bmfeed.getPicturesURLs();
		} else {
			return null;
		}
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
