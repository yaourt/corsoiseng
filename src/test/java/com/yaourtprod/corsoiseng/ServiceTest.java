package com.yaourtprod.corsoiseng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Ticker;

public class ServiceTest {
	
	private class TickerForTest extends Ticker {
		private long startTime = System.nanoTime();
		private long tickerTime = startTime;

		@Override
		public long read() {
			return tickerTime;
		}
		
		public void setTime(final int hours) {
			this.tickerTime = startTime + hours * 60 * 60 * 1000000000L + 1;  
		}
		
	}

	private TickerForTest ticker;
	private Service undertest;
	
	@Before
	public void before() {
		this.ticker = new TickerForTest();
		undertest = new Service();
		
		undertest.setTicker(ticker);
		
		final TotozService totozService = new TotozService();
		totozService.init();
		
		undertest.setTotozService(totozService);
		
		undertest.init();
	}
	
	@Test
	public void getBothCounterTest() throws ExecutionException {
		AtomicInteger result = null;
		
		// Anonymous
		result = undertest.getAnonymousCounter();
		assertEquals(0, result.intValue());
		
		result.incrementAndGet();

		result = undertest.getAnonymousCounter();
		assertEquals(1, result.intValue());
		
		ticker.setTime(9);
		result = undertest.getAnonymousCounter();
		assertEquals(0, result.intValue());
		
		// Fucked up
		ticker.setTime(-9);
		result = undertest.getFuckedupCounter();
		assertEquals(0, result.intValue());
		
		result.incrementAndGet();

		result = undertest.getFuckedupCounter();
		assertEquals(1, result.intValue());
		
		ticker.setTime(9);
		result = undertest.getFuckedupCounter();
		assertEquals(0, result.intValue());

	}
	
	@Test
	public void normalizePseudoTest() throws ExecutionException {
		String src = null;
		String result = null;
		
		src = " a b c A B C      0123'#[:totoz]    ";
		result = undertest.normalizePseudo(src);
		assertEquals(src.trim(), result);
		
		src = "012345678901234567890123456789012345678901234567890123456789";
		result = undertest.normalizePseudo(src);
		assertEquals("0123456789012345678901234567890123456789...", result);
		
		src = "   ";
		result = undertest.normalizePseudo(src);
		assertEquals("Ann Onymous #1", result);
		
		src = null;
		result = undertest.normalizePseudo(src);
		assertEquals("Ann Onymous #2", result);

		src = "scrpit=\"javascript:alert('toto');\"";
		result = undertest.normalizePseudo(src);
		assertEquals("Fucked up #1", result);
	}
	
	@Test
	public void createGetAndGetAllTest() throws ExecutionException {
		OthersAndMe result = null;
		
		//**** Empty @ start
		//GetAll
		result = undertest.getAll(null);
		assertNotNull(result);
		assertNull(result.getMe());
		assertNotNull(result.getOthers());
		assertEquals(0, result.getOthers().size());

		result = undertest.getAll(UUID.randomUUID());
		assertNotNull(result);
		assertNull(result.getMe());
		assertNotNull(result.getOthers());
		assertEquals(0, result.getOthers().size());
		
		// Get
		assertNull(undertest.get(null));
		assertNull(undertest.get(UUID.randomUUID()));
		
		
		//**** Create
		UUID uuid = UUID.randomUUID();
		String pseudo = "pseudo";
		undertest.create(uuid, pseudo);
		
		//**** 1 record now
		//GetAll
		result = undertest.getAll(null);
		assertNotNull(result);
		assertNull(result.getMe());
		assertNotNull(result.getOthers());
		assertEquals(1, result.getOthers().size());

		result = undertest.getAll(uuid);
		assertNotNull(result);
		assertNotNull(result.getMe());
		assertNotNull(result.getOthers());
		assertEquals(0, result.getOthers().size());
		
		// Get
		assertNotNull(undertest.get(uuid));
		assertNull(undertest.get(UUID.randomUUID()));
		
		
		//**** Create another one
		UUID uuid2 = UUID.randomUUID();
		String pseudo2 = "pseudo2";
		undertest.create(uuid2, pseudo2);

		//**** 2 records now
		//GetAll
		result = undertest.getAll(null);
		assertNotNull(result);
		assertNull(result.getMe());
		assertNotNull(result.getOthers());
		assertEquals(2, result.getOthers().size());

		result = undertest.getAll(uuid);
		assertNotNull(result);
		assertNotNull(result.getMe());
		assertNotNull(result.getOthers());
		assertEquals(1, result.getOthers().size());
		
		// Get
		assertNotNull(undertest.get(uuid));
		assertNull(undertest.get(UUID.randomUUID()));
		
		//**** Expire time ==> should be empty
		ticker.setTime(9);
		//GetAll
		result = undertest.getAll(null);
		assertNotNull(result);
		assertNull(result.getMe());
		assertNotNull(result.getOthers());
		assertEquals(0, result.getOthers().size());
	}
}
