package com.yaourtprod.corsoiseng;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CorsoiseControllerTest {
	private CorsoiseController undertest;
	@Mock
	private Service serviceMock;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		this.undertest = new CorsoiseController(serviceMock);
		CorsoiseSecurity.clean();
	}

	@Test
	public void rootTest() {
		String result = null;
		
		result = undertest.root();
		assertEquals("forward:/signIn", result);
		
		CorsoiseSecurity.setUser("pseudo", UUID.randomUUID());
		result = undertest.root();
		assertEquals("index", result);
	}
	
	@Test
	public void signInTest() throws Exception {
		HttpServletResponse responseMock = mock(HttpServletResponse.class);
		String result = null;
		
		result = undertest.signIn(null, responseMock);
		assertEquals("signIn", result);
		
		String pseudo = "pseudo";
		UUID uuid = UUID.randomUUID();
		when(serviceMock.normalizePseudo(pseudo)).thenReturn(pseudo);
		when(serviceMock.uuid(pseudo)).thenReturn(uuid);
		
		result = undertest.signIn(pseudo, responseMock);
		assertEquals("redirect:/", result);
	}
}
