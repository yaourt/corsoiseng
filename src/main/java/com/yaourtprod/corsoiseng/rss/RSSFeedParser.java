package com.yaourtprod.corsoiseng.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSSFeedParser {
	
	/** This class static logger.*/
	private final static Logger LOGGER = LoggerFactory.getLogger(RSSFeedParser.class);


	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String LANGUAGE = "language";
	private static final String COPYRIGHT = "copyright";
	private static final String LINK = "link";
	private static final String AUTHOR = "author";
	private static final String ITEM = "item";
	private static final String PUB_DATE = "pubDate";
	private static final String GUID = "guid";

	private final URL url;

	/* package */ RSSFeedParser(final String feedUrl) throws MalformedURLException {
		this.url = new URL(feedUrl);
	}

	/* package */ Feed readFeed() throws XMLStreamException, IOException {
		Feed feed = null;
		boolean isFeedHeader = true;
		// Set header values intial to the empty string
		String description = "";
		String title = "";
		String link = "";
		String language = "";
		String copyright = "";
		String author = "";
		String pubdate = "";
		String guid = "";

		// First create a new XMLInputFactory
		final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		inputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
		// Setup a new eventReader
		final InputStream in = read();
		final XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		// Read the XML document
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				final String localPart = event.asStartElement().getName().getLocalPart();
				if(ITEM.equals(localPart)) {
					if (isFeedHeader) {
						isFeedHeader = false;
						feed = new Feed(title, link, description, language, copyright, pubdate);
					}
					event = eventReader.nextEvent();
				} else if(TITLE.equals(localPart)) {
					title = getCharacterData(event, eventReader);
				} else if(DESCRIPTION.equals(localPart)) {
					description = getCharacterData(event, eventReader);
				} else if(LINK.equals(localPart)) {
					link = getCharacterData(event, eventReader);
				} else if(GUID.equals(localPart)) {
					guid = getCharacterData(event, eventReader);
				} else if(LANGUAGE.equals(localPart)) {
					language = getCharacterData(event, eventReader);
				} else if(AUTHOR.equals(localPart)) {
					author = getCharacterData(event, eventReader);
				} else if(PUB_DATE.equals(localPart)) {
					pubdate = getCharacterData(event, eventReader);
				} else if(COPYRIGHT.equals(localPart)) {
					copyright = getCharacterData(event, eventReader);
				} else {
					LOGGER.debug("Ignoring : {}", localPart);
				}
			} else if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
					FeedMessage message = new FeedMessage();
					message.setAuthor(author);
					message.setDescription(description);
					message.setGuid(guid);
					message.setLink(link);
					message.setTitle(title);
					feed.getMessages().add(message);
					event = eventReader.nextEvent();
					continue;
				}
			}
		}
		return feed;
	}

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
		return eventReader.getElementText();
//		String result = "";
//		event = eventReader.nextEvent();
//		if (event instanceof Characters) {
//			result = event.asCharacters().getData();
//		}
//		return result;
	}

	private InputStream read() throws IOException {
		return url.openStream();
	}
}