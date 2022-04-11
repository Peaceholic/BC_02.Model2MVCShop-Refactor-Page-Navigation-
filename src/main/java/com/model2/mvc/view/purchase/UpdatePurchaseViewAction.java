package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;


public class UpdatePurchaseViewAction extends Action{

	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int tran = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseDAO purchaseDAO = new PurchaseDAO();
		Purchase purchase = purchaseDAO.getPurchaseByTran(tran);
		
		System.out.println("aaaa"+purchase);
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
}
