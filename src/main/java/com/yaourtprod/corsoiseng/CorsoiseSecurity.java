package com.yaourtprod.corsoiseng;

import java.util.UUID;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;

public class CorsoiseSecurity {

	private static class UserInfo {
		String pseudo;
		UUID uid;
	}

	private static final ThreadLocal<UserInfo> user = new NamedThreadLocal<UserInfo>("CorsoiseNG-id");

	public static String getPseudo() {
		UserInfo userInfo = user.get();
		return (userInfo != null ? userInfo.pseudo : null);
	}

	public static UUID getUid() {
		UserInfo userInfo = user.get();
		return (userInfo != null ? userInfo.uid : null);
	}

	public static void setUser(final String pseudo, final UUID uuid) {
		UserInfo userInfo = new UserInfo();
		userInfo.pseudo = pseudo;
		userInfo.uid = uuid;
		user.set(userInfo);
	}

	public static boolean isUserSignedIn(final String pseudo) {
		UserInfo userInfo = user.get();
		return userInfo != null && userInfo.pseudo.equals(pseudo);
	}

	public static boolean isSignedIn() {
		return StringUtils.hasText(getPseudo());
	}

	public static void clean() {
		user.set(null);
	}
}