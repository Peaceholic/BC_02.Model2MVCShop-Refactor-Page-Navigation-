package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;


public class UpdateTranCodeByTranAction extends Action{

	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int tran = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchaseByTran(tran);
		purchase.setTranCode(request.getParameter("tranCode"));
		
		service.updateTranCode(purchase);
		
		return "forward:/listPurchase.do";
	}
}