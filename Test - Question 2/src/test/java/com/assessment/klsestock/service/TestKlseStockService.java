package com.assessment.klsestock.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.assessment.klsestock.entity.KlseStock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestKlseStockService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestKlseStockService.class);
	
	@Autowired
	private KlseStockService service;
	
	@Test
	public void test_getLatestKlseStock() throws IOException{
		KlseStock klseStock = service.getLatestKlseStock();
		LOGGER.debug("klseStock: {}", KlseStockService.convertToJsonString(klseStock));
	}
}
