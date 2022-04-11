package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;

public class PurchaseServiceImpl implements PurchaseService {

	private PurchaseDAO purchaseDAO;

	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}

	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDAO.addPurchase(purchase);
	}

	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		return purchaseDAO.getPurchaseList(search, buyerId);
	}
	
	public Purchase getPurchaseByTran(int tranNo) throws Exception {
		return purchaseDAO.getPurchaseByTran(tranNo);
	}
	
	public Purchase getPurchaseByProd(int prodNo) throws Exception {
		return purchaseDAO.getPurchaseByProd(prodNo);
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDAO.updateTranCode(purchase);
	}

}