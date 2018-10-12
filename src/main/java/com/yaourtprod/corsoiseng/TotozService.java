package com.yaourtprod.corsoiseng;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import com.google.common.base.Ticker;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;

@Named
public class TotozService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TotozService.class);

	/* package */ static final Pattern PATTERN = Pattern.compile("\\[:(\\p{Alnum}|\\p{Space}|:|_)+\\]");
	/* package */ static final String TOTOZ_URL_PREFIX = "https://totoz.eu/img/";
	/* package */ static final String TOTOZ_HTML_PREFIX = "<img class=\"totoz\" src=\"";
	/* package */ static final String TOTOZ_HTML_SUFFIX = "\" />";
	
	private Ticker ticker = Ticker.systemTicker();

	/* package */ Cache<String, Boolean> checkedTotozes;
	
	@PostConstruct
	/* package */ void init() {
		this.checkedTotozes =
				CacheBuilder
				.newBuilder()
				.expireAfterWrite(8, TimeUnit.HOURS)
				.maximumSize(200)
				.ticker(this.ticker)
				.build();
	}
	
	/* package */ void setTicker(final Ticker ticker) {
		this.ticker = ticker;
	}


	public String processTotoz(final String original) {
		LOGGER.debug("Processing Totoz, input : {}", original);
		final Matcher m = TotozService.PATTERN.matcher(original);
		final StringBuffer strbuf = new StringBuffer();

		while(m.find()) {
			int start = m.start();
			int end = m.end();
			String totoz = original.substring(start, end);
			String url = buildTotozURL(totoz);
			if(null != url && isRealTotoz(url)) {
				m.appendReplacement(strbuf, buildTotozHTML(url));
			} else {
				m.appendReplacement(strbuf, "");
			}
		}
		m.appendTail(strbuf);
		final String totozout = strbuf.toString();
		LOGGER.debug("Processing Totoz, output : {}", totozout);
		return totozout;
	}
	
	/* package */ String buildTotozURL(final String totoz) {
		if(null != totoz && totoz.length() > 3) {
			StringBuilder strb = null;
			try {
				strb = new StringBuilder(TOTOZ_URL_PREFIX)
						.append(UriUtils.encodePath(
									totoz.substring(2, totoz.length()-1),
									"UTF-8"
								)
						);
			} catch (UnsupportedEncodingException e) {}
			if(null != strb) {
				return strb.toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/* package */ boolean isRealTotoz(final String url) {
		if(null != url) {
			final Boolean checked = checkedTotozes.getIfPresent(url); 
			if(null != checked) {
				//This totoz has already been checked ...
				return checked;
			}
			try {
				HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
				con.setInstanceFollowRedirects(false);
				con.setRequestMethod("HEAD");
				if(HttpURLConnection.HTTP_OK == con.getResponseCode()) {
					checkedTotozes.put(url, Boolean.TRUE);
					return true;
				} else {
					checkedTotozes.put(url, Boolean.FALSE);
					return false;
				}
			} catch (final Exception e) {
				checkedTotozes.put(url, Boolean.FALSE);
				return false;
			}
		} else {
			return false;
		}
	}
	
	/* package */ String buildTotozHTML(final String url) {
		return
				new StringBuilder(TOTOZ_HTML_PREFIX)
				.append(url)
				.append(TOTOZ_HTML_SUFFIX)
				.toString();
	}
}
