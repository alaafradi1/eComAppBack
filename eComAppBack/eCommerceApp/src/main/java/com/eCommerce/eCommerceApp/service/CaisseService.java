package com.eCommerce.eCommerceApp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Map;

import com.eCommerce.eCommerceApp.entity.Campaign;
import com.eCommerce.eCommerceApp.repository.CampaignRepostiroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Orders;
import com.eCommerce.eCommerceApp.repository.CaisseRepository;
import com.eCommerce.eCommerceApp.repository.ProductRepository;
import com.eCommerce.eCommerceApp.service.HistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CaisseService {

	@Autowired
	CaisseRepository caisseRep;

	@Autowired
	HistoryService historyService;

	@Autowired
	ProductRepository productRep;

	@Autowired
	CampaignRepostiroy campaignRep;

	public List<Caisse> getAllCaisses() {
		return caisseRep.findAll();
	}

	public Map<String, String> getStatisticsByCriteria(String criteria) {
		Map<String, String> result = new HashMap<>();
		if (criteria.equals("All")) {
			result = getAllCaisseStatistics();
		} else if (criteria.equals("Bureau")) {
			result = getCaisseStatisticsWithNullProductId();
		} else {
			result = getCaisseStatisticsWithProductId(Long.parseLong(criteria));
		}
		return result;
	}

	public Map<String, String> getAllCaisseStatistics() {
		Double sumOfAllRevenuAmount = caisseRep.getSumOfAllRevenuAmount();
		Double sumOfAllDepenseAmount = caisseRep.getSumOfAllDepenseAmount();
		Map<String, String> result = new HashMap<>();
		result.put("sumOfRevenuAmount", sumOfAllRevenuAmount != null ? sumOfAllRevenuAmount.toString() : "0.0");
		result.put("sumOfDepenseAmount", sumOfAllDepenseAmount != null ? sumOfAllDepenseAmount.toString() : "0.0");
		return result;
	}

	public Map<String, String> getCaisseStatisticsWithNullProductId() {
		Double sumOfAllRevenuAmount = caisseRep.getSumOfRevenuAmountWithNullProductId();
		Double sumOfAllDepenseAmount = caisseRep.getSumOfDepenseAmountWithNullProductId();
		Map<String, String> result = new HashMap<>();
		result.put("sumOfRevenuAmount", sumOfAllRevenuAmount != null ? sumOfAllRevenuAmount.toString() : "0.0");
		result.put("sumOfDepenseAmount", sumOfAllDepenseAmount != null ? sumOfAllDepenseAmount.toString() : "0.0");
		return result;
	}

	public Map<String, String> getCaisseStatisticsWithProductId(Long productId) {
		Double sumOfAllRevenuAmount = caisseRep.getSumOfRevenuAmountWithProductId(productId);
		Double sumOfAllDepenseAmount = caisseRep.getSumOfDepenseAmountWithProductId(productId);
		Map<String, String> result = new HashMap<>();
		result.put("sumOfRevenuAmount", sumOfAllRevenuAmount != null ? sumOfAllRevenuAmount.toString() : "0.0");
		result.put("sumOfDepenseAmount", sumOfAllDepenseAmount != null ? sumOfAllDepenseAmount.toString() : "0.0");
		return result;
	}

	public void addCaisse(Map<String, String> caisseInfo) {
		Caisse caisse = new Caisse();

		Calendar calendar = Calendar.getInstance();
		caisse.setCreationDate(calendar.getTime());

		caisse.setDescription(caisseInfo.get("description"));

		caisse.setAmount(Float.parseFloat(caisseInfo.get("amount")));

		if (caisseInfo.get("productId") != null) {
			caisse.setProduct(productRep.findById(Long.parseLong(caisseInfo.get("productId"))).get());
		}

		if (caisseInfo.get("campaignId") != null) {
			Long campaignId = Long.parseLong(caisseInfo.get("campaignId") );
			Campaign campaign = campaignRep.findById(campaignId).get();
			caisse.setCampaign(campaign);
		}

		if (caisseInfo.get("operationDate") != null) {
			LocalDate operationDate = LocalDate.parse(caisseInfo.get("operationDate"));
			caisse.setOperationDate(Date.from(operationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		}

		caisse.setOrigin(caisseInfo.get("origin"));

		caisse.setType(caisseInfo.get("type"));

		caisseRep.save(caisse);
		historyService.addCaisseHistory(caisse);
	}

	public ResponseEntity<String> addMultipleCaisses(List<Map<String, String>> caissesInfo) {
		for (Map<String, String> caisseInfo : caissesInfo) {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> caisseMap = new HashMap<>();

			caisseMap.put("operationDate", convertExcelSerialDateToDateFormat(caisseInfo.get("operationDate"))!=null?convertExcelSerialDateToDateFormat(caisseInfo.get("operationDate")) : null  );
			caisseMap.put("creationDate", convertExcelSerialDateToDateFormat(caisseInfo.get("creationDate")));
			caisseMap.put("amount", caisseInfo.get("Amount"));
			caisseMap.put("description", caisseInfo.get("Description"));
			caisseMap.put("involvedParty", caisseInfo.get("involvedParty"));
			caisseMap.put("type", caisseInfo.get("Type"));
			caisseMap.put("origin", caisseInfo.get("origin"));

			String productId = caisseInfo.get("productId");
			productId = "Bureau".equals(productId) ? null : productId;
			caisseMap.put("productId",productId );
			
			try {
				// String caisseDetail = objectMapper.writeValueAsString(caisseMap);
				this.addCaisse(caisseMap);
			} catch (Error e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ResponseEntity.ok("multiple Caisses were not added successfully : " + e);
			}

		}

		return ResponseEntity.ok("Multiple Caisses were added successfully");
	}

	public static String convertExcelSerialDateToDateFormat(String excelSerialDate) {
        // double serialDateValue = Double.parseDouble(excelSerialDate);
		if(excelSerialDate!=null){
			try {
				SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				Date date = inputFormat.parse(excelSerialDate);
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				return outputFormat.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return(null);
		}
        
    }

	public ResponseEntity<String> editCaisse(Long caisseId, Map<String, String> caisseInfo) {
		try {

			Caisse existingCaisse = caisseRep.findById(caisseId).get();
			if (existingCaisse == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caisse not found using caisseId");
			}
			String historyDescription = "";
			String historyDescriptionFirstPart = "id Caisse " + existingCaisse.getIdCaisse().toString();
			if (existingCaisse.getAmount() != Float.parseFloat(caisseInfo.get("amount"))) {
				historyDescription = " || montant : " + existingCaisse.getAmount() + " => " + caisseInfo.get("amount");
			}

			existingCaisse.setDescription(caisseInfo.get("description"));
			existingCaisse.setAmount(Float.parseFloat(caisseInfo.get("amount")));
			existingCaisse.setType(caisseInfo.get("type"));

			existingCaisse.setOrigin(caisseInfo.get("origin"));

			caisseRep.save(existingCaisse);
			historyService.addHistory("Caisse", "Edit", historyDescriptionFirstPart + historyDescription);
			return ResponseEntity.ok("Caisse updated successfully");

		} catch (Exception e) {
			String errorMessage = "Error updating caisse: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}

	}

	public List<Caisse> getCaisseByDateRange(String startDate, String endDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date start = null;
		Date end = null;
		try {
			start = dateFormat.parse(startDate);
			end = dateFormat.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Adjust the start date to be one day before
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		start = calendar.getTime();

		// Adjust the end date to be one day after
		calendar.setTime(end);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		end = calendar.getTime();

		return caisseRep.findByCreationDateBetween(start, end);
	}

	public void deleteCaisse(Long idCaisse) {
		Caisse exitingCaisse = caisseRep.findById(idCaisse).get();
		caisseRep.deleteById(idCaisse);
		if (exitingCaisse != null) {
			String historyDeleteDescription = "caisseId= " + exitingCaisse.getIdCaisse() +
					" || Amount= " + exitingCaisse.getAmount() +
					" || Type=" + exitingCaisse.getType() +
					" || Description= " + exitingCaisse.getDescription();

			historyService.addHistory("Caisse", "Delete", historyDeleteDescription);
		} else {
			System.out.println("the delete history of delete caisse has failed");
		}
	}

}
