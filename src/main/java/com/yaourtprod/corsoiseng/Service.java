package com.yaourtprod.corsoiseng;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Named;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;

@Named
public class Service {
	private static final String KEY_ANONYMOUS = "ANO"; 
	private static final String KEY_FUCKEDUP = "FU";

//	private final AtomicInteger aneCount = new AtomicInteger(0);
//	private final AtomicInteger fuckedupCount = new AtomicInteger(0);
	
	/*package*/ static final Pattern pattern = Pattern.compile("[\\p{Alnum}\\p{Blank}'#]*");
	
	private final Cache<UUID, Corsoiseur> data;

	private final Cache<String, AtomicInteger> counters;
	
	public Service() {
		this.data =
				CacheBuilder.newBuilder()
				.expireAfterWrite(8, TimeUnit.HOURS)
				.maximumSize(200)
				.build();
		
		this.counters =
				CacheBuilder.newBuilder()
				.expireAfterWrite(8, TimeUnit.HOURS)
				.maximumSize(2)
				.build();
	}
	
	public UUID uuid(final String pseudo) throws NoSuchAlgorithmException {
		final UUID uuid = UUID.randomUUID();
//		final MessageDigest digester = MessageDigest.getInstance("SHA-1");
//		final byte[] digest = digester.digest(uuid.getBytes());
//		
		return uuid;
	}

	public String normalizePseudo(final String pseudo) throws ExecutionException {
		if (null == pseudo || pseudo.isEmpty()) {
			return "Ann Onymous #" + getAnonymousCounter().incrementAndGet();
		} else {
			final String lpseudo = pseudo.trim();
			final Matcher m = Service.pattern.matcher(lpseudo);
			if(m.matches()) {
				return lpseudo;
			} else {
				return "Fucked up #" + getFuckedupCounter().incrementAndGet();
			}
		}
	}
	
	public OthersAndMe getAll(final String pseudo) {
		final Set<Corsoiseur> result =  ImmutableSet.copyOf(data.asMap().values());
		Set<Corsoiseur> others = new HashSet<Corsoiseur>(result.size());
		Corsoiseur me = null;
		for(final Corsoiseur c : result) {
			if(pseudo.equals(c.getPseudo())) {
				me = c;
			} else {
				others.add(c);
			}
		}
		return new OthersAndMe(me, ImmutableSet.copyOf(others));
	}
	
	public Corsoiseur get(final UUID uuid) {
		return data.getIfPresent(uuid);
	}
	
	private Corsoiseur getOrCreate(final UUID uuid, final String pseudo) throws ExecutionException {
		final String lpseudo = normalizePseudo(pseudo);
		final Corsoiseur c =
			data.get(uuid, new Callable<Corsoiseur>() {
				public Corsoiseur call() throws Exception {
					return new Corsoiseur(lpseudo);
				}
			});
		return c;
	}
	
	private AtomicInteger getAnonymousCounter() throws ExecutionException {
		return counters.get(KEY_ANONYMOUS, new Callable<AtomicInteger>() {
			public AtomicInteger call() throws Exception {
				return new AtomicInteger(0);
			}
		});
	}
	
	private AtomicInteger getFuckedupCounter() throws ExecutionException {
		return counters.get(KEY_FUCKEDUP, new Callable<AtomicInteger>() {
			public AtomicInteger call() throws Exception {
				return new AtomicInteger(0);
			}
		});
	}

	public OthersAndMe incCombienKilEst(final UUID uuid, final String pseudo) throws ExecutionException {
		final String lpseudo = normalizePseudo(pseudo);
		getOrCreate(uuid, lpseudo).incCombienKilest();
		return getAll(lpseudo);
	}

	public OthersAndMe incPietra(final UUID uuid, final String pseudo) throws ExecutionException {
		final String lpseudo = normalizePseudo(pseudo);
		getOrCreate(uuid, lpseudo).incPietra();
		return getAll(lpseudo);
	}

	public OthersAndMe incTerrine(final UUID uuid, final String pseudo) throws ExecutionException {
		final String lpseudo = normalizePseudo(pseudo);
		getOrCreate(uuid, lpseudo).incTerrine();
		return getAll(lpseudo);
	}

	public OthersAndMe decCombienKilEst(final UUID uuid, final String pseudo) throws ExecutionException {
		final String lpseudo = normalizePseudo(pseudo);
		getOrCreate(uuid, lpseudo).decCombienKilest();
		return getAll(lpseudo);
	}

	public OthersAndMe decPietra(final UUID uuid, final String pseudo) throws ExecutionException {
		final String lpseudo = normalizePseudo(pseudo);
		getOrCreate(uuid, lpseudo).decPietra();
		return getAll(lpseudo);

	}

	public OthersAndMe decTerrine(final UUID uuid, final String pseudo) throws ExecutionException {
		final String lpseudo = normalizePseudo(pseudo);
		getOrCreate(uuid, lpseudo).decTerrine();
		return getAll(lpseudo);
	}
}
