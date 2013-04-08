package com.yaourtprod.corsoiseng;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import com.google.common.base.Ticker;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;

@Named
public class Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(Service.class);

	private static final String KEY_ANONYMOUS = "ANO";
	private static final String KEY_FUCKEDUP = "FU";
	
	private Ticker ticker = Ticker.systemTicker();

	/* package */static final Pattern PATTERN = Pattern.compile("[\\p{Alnum}\\p{Blank}'#\\[\\]:]*");

	private Cache<UUID, Corsoiseur> data;

	private Cache<String, AtomicInteger> counters;

	public Service() {
	}
	
	@PostConstruct
	/* package */ void init() {
		this.data = CacheBuilder
				.newBuilder()
				.expireAfterWrite(8, TimeUnit.HOURS)
				.maximumSize(200)
				.ticker(this.ticker)
				.build();

		this.counters = CacheBuilder
				.newBuilder()
				.expireAfterWrite(8, TimeUnit.HOURS)
				.maximumSize(2)
				.ticker(this.ticker)
				.build();
	}
	
	/* package */ void setTicker(final Ticker ticker) {
		this.ticker = ticker;
	}

	public UUID uuid(final String pseudo) throws NoSuchAlgorithmException {
		final UUID uuid = UUID.randomUUID();
		return uuid;
	}

	public String normalizePseudo(final String pseudo) throws ExecutionException {
		if (null == pseudo || pseudo.trim().isEmpty()) {
			return "Ann Onymous #" + getAnonymousCounter().incrementAndGet();
		} else {
			String lpseudo = pseudo.trim();
			final Matcher m = Service.PATTERN.matcher(lpseudo);
			if (m.matches()) {
				if(lpseudo.length() > 40) {
					lpseudo = HtmlUtils.htmlEscape(lpseudo.substring(0, 40) + "...");
				}
				return lpseudo;
			} else {
				return "Fucked up #" + getFuckedupCounter().incrementAndGet();
			}
		}
	}

	public OthersAndMe getAll(final UUID uuid) {
		final Map<UUID, Corsoiseur> result = ImmutableMap.copyOf(data.asMap());
		
		Set<Corsoiseur> others = new HashSet<Corsoiseur>(result.size());
		Corsoiseur me = null;
		if (null != uuid) {
			for(final Entry<UUID, Corsoiseur> entry : result.entrySet()) {
				if(entry.getKey().equals(uuid)) {
					me = entry.getValue();
				} else {
					others.add(entry.getValue());
				}
			}
		} else {
			others.addAll(result.values()); 
		}
		return new OthersAndMe(me, others);
	}

	public Corsoiseur get(final UUID uuid) {
		if(null != uuid) {
			final Corsoiseur result = data.getIfPresent(uuid);
			if (null != result)
				LOGGER.debug("{} found => {}", uuid, result.getPseudo());
			else
				LOGGER.debug("{} not found", uuid);
	
			return result;
		} else {
			return null;
		}
	}

	public Corsoiseur create(final UUID uuid, final String pseudo) throws ExecutionException {
		final String lpseudo = normalizePseudo(pseudo);
		final Corsoiseur c = data.get(uuid, new Callable<Corsoiseur>() {
			public Corsoiseur call() throws Exception {
				return new Corsoiseur(lpseudo);
			}
		});
		return c;
	}

	/* package */ AtomicInteger getAnonymousCounter() throws ExecutionException {
		return counters.get(KEY_ANONYMOUS, new Callable<AtomicInteger>() {
			public AtomicInteger call() throws Exception {
				return new AtomicInteger(0);
			}
		});
	}

	/* package */ AtomicInteger getFuckedupCounter() throws ExecutionException {
		return counters.get(KEY_FUCKEDUP, new Callable<AtomicInteger>() {
			public AtomicInteger call() throws Exception {
				return new AtomicInteger(0);
			}
		});
	}

	public void incCombienKilEst(final UUID uuid) throws ExecutionException {
		if(null != uuid) {
			final Corsoiseur c = get(uuid);
			if(null != c) c.incCombienKilest();
		}
	}

	public void incPietra(final UUID uuid) throws ExecutionException {
		if(null != uuid) {
			final Corsoiseur c = get(uuid);
			if(null != c) c.incPietra();
		}
	}

	public void incTerrine(final UUID uuid) throws ExecutionException {
		if(null != uuid) {
			final Corsoiseur c = get(uuid);
			if(null != c) c.incTerrine();
		}
	}

	public void decCombienKilEst(final UUID uuid) throws ExecutionException {
		if(null != uuid) {
			final Corsoiseur c = get(uuid);
			if(null != c) c.decCombienKilest();
		}
	}

	public void decPietra(final UUID uuid) throws ExecutionException {
		if(null != uuid) {
			final Corsoiseur c = get(uuid);
			if(null != c) c.decPietra();
		}
	}

	public void decTerrine(final UUID uuid) throws ExecutionException {
		if(null != uuid) {
			final Corsoiseur c = get(uuid);
			if(null != c) c.decTerrine();
		}
	}
}
