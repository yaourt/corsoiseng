package com.yaourtprod.corsoiseng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TotozServiceTest {
	private TotozService undertest;
	
	@Before
	public void before() {
		undertest = new TotozService();
	}

	@Test
	public void isRealTotozTest() {
		String url = null;
		
		url = TotozService.TOTOZ_URL_PREFIX + "gedebol";
		assertTrue(undertest.isRealTotoz(url));
		
		url = TotozService.TOTOZ_URL_PREFIX + "ppppmmmmiiillllooouuuujjjj";
		assertFalse(undertest.isRealTotoz(url));

	}
	
	@Test
	public void buildTotozURLTest() {
		String delimitedTotoz = null;
		String totoz = null;
		
		assertNull(undertest.buildTotozURL(null));
		assertNull(undertest.buildTotozURL(""));
		assertNull(undertest.buildTotozURL("[:]"));

		delimitedTotoz = "[:pasglop]";
		totoz = "pasglop";
		assertEquals(TotozService.TOTOZ_URL_PREFIX+totoz, undertest.buildTotozURL(delimitedTotoz));

		delimitedTotoz = "[:pas glop]";
		totoz = "pas+glop";
		assertEquals(TotozService.TOTOZ_URL_PREFIX+totoz, undertest.buildTotozURL(delimitedTotoz));

		delimitedTotoz = "[:pas >glop]";
		totoz = "pas+%3Eglop";
		assertEquals(TotozService.TOTOZ_URL_PREFIX+totoz, undertest.buildTotozURL(delimitedTotoz));
	}
	
	@Test
	@Ignore
	public void processTotozTest() {
		String input = null;
		String result = null;
		
		input ="blah blah blah [:1st totoz], blah blah blah [:   2nd   totoz] and [:last tototz]";
		result = undertest.processTotoz(input);
		
		assertEquals("pif", result);
	}
	
	@Test
	@Ignore
	public void patternTest() {
		Matcher m = null;
		
		m = TotozService.PATTERN.matcher("totoz");
		assertFalse(m.matches());

		m = TotozService.PATTERN.matcher("[:totoz]");
		assertTrue(m.matches());

		m = TotozService.PATTERN.matcher("blah blah [:totoz] blah blah");
		assertTrue(m.matches());
		
		m = TotozService.PATTERN.matcher("blah blah [:totoz] blah [:totoz] blah [:totoz]");
		int count = 0;
		while(m.find()) {
			System.out.println("Start : " + m.start() + ", End : " + m.end());
			count++;
		}
		assertEquals(1, count);
	}

}
