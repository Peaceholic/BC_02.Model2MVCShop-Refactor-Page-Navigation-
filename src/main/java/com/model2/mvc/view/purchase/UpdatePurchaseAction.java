package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;


public class UpdatePurchaseAction extends Action{

	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int tran = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseDAO purchaseDAO = new PurchaseDAO();
		Purchase purchase = new Purchase();
		
		purchase = purchaseDAO.getPurchaseByTran(tran);
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		purchaseDAO.updatePurchase(purchase);
		
		return "forward:/getPurchase.do?tranNo="+tran;
	}
}