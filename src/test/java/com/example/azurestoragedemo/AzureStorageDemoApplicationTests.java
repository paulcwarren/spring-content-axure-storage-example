package com.example.azurestoragedemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class AzureStorageDemoApplicationTests {

	@Autowired
	private AzureStorageDemoApplication.PromoRepository repo;

	@Autowired
	private AzureStorageDemoApplication.PromoContentStore store;

	@Test
	void contextLoads() {
		Assert.notNull(repo, "repository is null");
		Assert.notNull(store, "store is null");
	}

}
