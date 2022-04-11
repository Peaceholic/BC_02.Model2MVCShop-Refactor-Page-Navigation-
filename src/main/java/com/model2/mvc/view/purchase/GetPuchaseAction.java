package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;


public class GetPuchaseAction extends Action{

	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int tran = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseDAO purchaseDAO = new PurchaseDAO();
		Purchase purchase = purchaseDAO.getPurchaseByTran(tran);
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchaseView.jsp";
	}
}