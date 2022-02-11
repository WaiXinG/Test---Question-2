package com.assessment.klsestock.service;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.klsestock.dao.IKlseStockRepo;
import com.assessment.klsestock.entity.KlseStock;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KlseStockService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(KlseStockService.class);
	
	@Autowired
	IKlseStockRepo repo;
	
	public void saveLatestKlseStock() throws IOException {
		KlseStock klseStock = getLatestKlseStock();
		//add current datetime
		klseStock.setRecordDateTime(new Date());
		LOGGER.debug("KlseStockService|saveLatestKlseStock|klseStock: {}", convertToJsonString(klseStock));
		repo.save(klseStock);
	}
	
	public KlseStock getLatestKlseStock() throws IOException {
		KlseStock klseStock = new KlseStock();
		
		//Fetch html information from the given url
		String url = "https://klse.i3investor.com/index.jsp";
		Document document = Jsoup.connect(url).get();

		//Get gainer and loser element by using class name
		Element gainerElement = document.getElementsByClass("gainer").first();
		Element loserElement = document.getElementsByClass("loser").first();
		
		//Add obtained value to klseStock entity
		try { 
			int gainerValue = Integer.parseInt(gainerElement.html());
			klseStock.setNumGainers(gainerValue);
			
			int loserValue = Integer.parseInt(loserElement.html());
			klseStock.setNumLoser(loserValue);
			
		} catch(NumberFormatException e) { 
	        LOGGER.debug("KlseStockService|getLatestKlseStock|Value obtained is not Integer.",e);
	    } catch(NullPointerException e) {
	    	LOGGER.debug("KlseStockService|getLatestKlseStock|No value found.",e);
	    }
		
		return klseStock;
	}
	
	public static String convertToJsonString(Object object){
		ObjectMapper mapper = new ObjectMapper();
		try{
			return mapper.writeValueAsString(object);
		}
		catch(Exception e){
			LOGGER.error("", e);
		}
		return "";
	}
}
