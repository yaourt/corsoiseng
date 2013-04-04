package com.yaourtprod.corsoiseng;

import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;

import org.junit.Test;

public class ServiceTest {
	@Test
	public void regexpTest() {
		Matcher m = null;
		
		m = Service.pattern.matcher(" a b c A B C      0123'#");
		assertTrue(m.matches());
	}

}
