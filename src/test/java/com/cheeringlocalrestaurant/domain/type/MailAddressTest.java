package com.cheeringlocalrestaurant.domain.type;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MailAddressTest {

	@Test
	void _正常な場合は例外が発生しない() {
		try {
			new MailAddress("iroha@example.com");
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void _nullの場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, () -> {new MailAddress(null);});
	}

	@Test
	void _長さ0の場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, () -> {new MailAddress("");});
	}

	// 参考サイト: https://www.asobou.co.jp/blog/web/mail-rfc
	@ParameterizedTest
	@ValueSource(strings = {
			".abcd@example.co.jp",
			"abcd.@example.co.jp",
			"abcd..@example.co.jp",
			"ab..cd@example.co.jp",
			"ab[cd@example.co.jp",
			"ab@cd@example.co.jp"})
	void _不正なメールアドレスの場合は例外が発生する(String mailAddress) {
		assertThrows(IllegalArgumentException.class, () -> {new MailAddress(mailAddress);});
	}
}
