package com.yyhz.utils;

import java.util.ResourceBundle;

public class MessagesUtils {

	private static final String BUNDLE_NAME = "resources.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private MessagesUtils() {
	}

	public static String getString(String key) {
		try {
			return new String(RESOURCE_BUNDLE.getString(key).getBytes(
					"ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			return '!' + key + '!';
		}
	}
}
