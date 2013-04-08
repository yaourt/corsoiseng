package com.yaourtprod.corsoiseng;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class CookieInterceptorTest {
	private CookieInterceptor undertest;
	
	@Mock
	private Service serviceMock;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		this.undertest = new CookieInterceptor(serviceMock);
		
	}
	
	@Test
	public void preHandleTest() throws Exception {
		MockHttpServletRequest requestMock = new MockHttpServletRequest();
		MockHttpServletResponse responseMock = new MockHttpServletResponse();
		
		undertest.preHandle(requestMock, responseMock, null);
		verify(serviceMock, never()).get((UUID)anyObject());
		
		UUID uuid = UUID.randomUUID();
		Cookie cookie = null;
		
		cookie = new Cookie("dummyname", uuid.toString());
		requestMock.setCookies(cookie);
		undertest.preHandle(requestMock, responseMock, null);
		verify(serviceMock, never()).get((UUID)anyObject());

		cookie = new Cookie("corsoisengauth", null);
		requestMock.setCookies(cookie);
		undertest.preHandle(requestMock, responseMock, null);
		verify(serviceMock, never()).get((UUID)anyObject());

		cookie = new Cookie("corsoisengauth", "");
		requestMock.setCookies(cookie);
		undertest.preHandle(requestMock, responseMock, null);
		verify(serviceMock, never()).get((UUID)anyObject());

		cookie = new Cookie("corsoisengauth", "blabla");
		requestMock.setCookies(cookie);
		undertest.preHandle(requestMock, responseMock, null);
		verify(serviceMock, never()).get((UUID)anyObject());

		cookie = new Cookie("corsoisengauth", uuid.toString());
		requestMock.setCookies(cookie);
		undertest.preHandle(requestMock, responseMock, null);
		verify(serviceMock, times(1)).get((UUID)anyObject());
		assertFalse(CorsoiseSecurity.isSignedIn());
		
		Corsoiseur corsoiseur= new Corsoiseur("pseudo");
		
		when(serviceMock.get(uuid)).thenReturn(corsoiseur);
		undertest.preHandle(requestMock, responseMock, null);
		verify(serviceMock, times(2)).get((UUID)anyObject());
		assertTrue(CorsoiseSecurity.isSignedIn());
	}

}
