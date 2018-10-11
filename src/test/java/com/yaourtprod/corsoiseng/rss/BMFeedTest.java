package com.yaourtprod.corsoiseng.rss;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BMFeedTest {
	
	/** This class static logger.*/
	private final static Logger LOGGER = LoggerFactory.getLogger(BMFeedTest.class);
	
	@Test
	public void feedParserTest() throws XMLStreamException, IOException {
		BMFeed feed = new BMFeed();
		assertTrue(feed.data.asMap().isEmpty());
		final Collection<String> result = feed.getPicturesURLs();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		for(final String str : result) {
			LOGGER.debug("Image link : {}", str);
			assertTrue(str.startsWith("https://"));
		}
		assertFalse(feed.data.asMap().isEmpty());
	}

}
