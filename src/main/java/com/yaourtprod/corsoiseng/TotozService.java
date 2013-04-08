package com.yaourtprod.corsoiseng;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TotozService {
	/* package */ static final Pattern PATTERN = Pattern.compile(".*\\[:\\p{Graph}+\\].*");
	/* package */ static final String TOTOZ_URL_PREFIX = "http://totoz.eu/img/";

	public String processTotoz(final String original) {
		final Matcher m = TotozService.PATTERN.matcher(original);
		final StringBuffer strbuf = new StringBuffer();

		while(m.find()) {
			int start = m.start();
			int end = m.end();
			String totoz = original.substring(start, end);
			String url = buildTotozURL(totoz);
			if(null != url && isRealTotoz(url)) {
				m.appendReplacement(strbuf, url);
			} else {
				m.appendReplacement(strbuf, "");
			}
		}
		m.appendTail(strbuf);
		return strbuf.toString();
	}
	
	/* package */ String buildTotozURL(final String totoz) {
		if(null != totoz && totoz.length() > 3) {
			StringBuilder strb = null;
			try {
				strb = new StringBuilder(TOTOZ_URL_PREFIX)
						.append(URLEncoder.encode(
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
			try {
				HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
				con.setInstanceFollowRedirects(false);
				con.setRequestMethod("HEAD");
				return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
			} catch (final Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
