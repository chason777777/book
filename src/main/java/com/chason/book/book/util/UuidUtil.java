package com.chason.book.book.util;

import java.util.UUID;

public class UuidUtil {
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
