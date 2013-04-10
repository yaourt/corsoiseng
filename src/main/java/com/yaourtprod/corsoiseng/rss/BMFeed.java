package com.yaourtprod.corsoiseng.rss;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.xml.stream.XMLStreamException;

import com.google.common.base.Ticker;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Named
public class BMFeed extends RSSFeedParser {
	private static final String FEED_URL = "http://feeds.feedburner.com/BonjourMadame?format=xml";

	private Ticker ticker = Ticker.systemTicker();
	/* package */ Cache<Integer, String> data;

	/* package */ BMFeed() throws MalformedURLException {
		super(FEED_URL);
		this.data = CacheBuilder
				.newBuilder()
				.expireAfterWrite(8, TimeUnit.HOURS)
				.maximumSize(200)
				.ticker(this.ticker)
				.build();
	}

	/* package */ void setTicker(final Ticker ticker) {
		this.ticker = ticker;
	}
	
	public Collection<String> getPicturesURLs() throws XMLStreamException, IOException {
		final Map<Integer, String> map = data.asMap();
		if(map.isEmpty()) {
			final List<String> result = consumeFeed();
			int count = 0;
			for(final String str : result) {
				if(isRealBabe(str)) {
					map.put(count++, str);
				}
			}
		}
		return map.values();
	}
	
	private List<String> consumeFeed() throws XMLStreamException, IOException {
		final Feed feed = readFeed();
		final LinkedList<String> result = new LinkedList<String>();
		for(final FeedMessage message : feed.getMessages()) {
			final String desc = message.getDescription();
			int end = desc.indexOf("\"/>");
			result.add(desc.substring(10, end));
		}
		return result;
	}
	
	/* package */ boolean isRealBabe(final String url) {
		if(null != url) {
			try {
				HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
				con.setInstanceFollowRedirects(false);
				con.setRequestMethod("HEAD");
				if(HttpURLConnection.HTTP_OK == con.getResponseCode()) {
					return true;
				} else {
					return false;
				}
			} catch (final Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

}
