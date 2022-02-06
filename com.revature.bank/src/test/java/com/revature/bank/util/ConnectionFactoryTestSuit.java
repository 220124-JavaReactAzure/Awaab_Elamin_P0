package com.revature.bank.util;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

import com.revature.bank.util.db.ConnectionFactory;

public class ConnectionFactoryTestSuit {
	@Test
	public void test_getConnection_returnValidConnection_givenProvidenCredentials() {
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre();){
			System.out.println(conn);
			Assert.assertNotNull(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
