package com.yaourtprod.corsoiseng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TotozServiceTest {
	private TotozService undertest;
	
	@Before
	public void before() {
		undertest = new TotozService();
		undertest.init();
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
		totoz = "pas%20glop";
		assertEquals(TotozService.TOTOZ_URL_PREFIX+totoz, undertest.buildTotozURL(delimitedTotoz));

		delimitedTotoz = "[:pas >glop]";
		totoz = "pas%20%3Eglop";
		assertEquals(TotozService.TOTOZ_URL_PREFIX+totoz, undertest.buildTotozURL(delimitedTotoz));
	}
	
	@Test
	public void processTotozTest() {
		String input = null;
		String result = null;

		assertNull(undertest.checkedTotozes.getIfPresent("https://totoz.eu/img/totoz"));
		
		input ="blah blah [:depardieu:3] blah [:totoz] blah [: LAST totoz123] blah blah!";
		result = undertest.processTotoz(input);
		
		assertEquals("blah blah <img class=\"totoz\" src=\"https://totoz.eu/img/depardieu:3\" /> blah <img class=\"totoz\" src=\"https://totoz.eu/img/totoz\" /> blah  blah blah!", result);
		
		assertTrue(undertest.checkedTotozes.getIfPresent("https://totoz.eu/img/totoz"));
	}

	@Test
	public void processTotoz2Test() {
		String input = null;
		String result = null;

		assertNull(undertest.checkedTotozes.getIfPresent("https://totoz.eu/img/et%20mes%20fesses%20tu%20les%20aimes"));

		input ="blah blah [:et mes fesses tu les aimes] blah [:totoz] blah [: LAST totoz123] blah blah!";
		result = undertest.processTotoz(input);

		assertEquals("blah blah <img class=\"totoz\" src=\"https://totoz.eu/img/et%20mes%20fesses%20tu%20les%20aimes\" /> blah <img class=\"totoz\" src=\"https://totoz.eu/img/totoz\" /> blah  blah blah!", result);

		assertTrue(undertest.checkedTotozes.getIfPresent("https://totoz.eu/img/totoz"));
	}
}
