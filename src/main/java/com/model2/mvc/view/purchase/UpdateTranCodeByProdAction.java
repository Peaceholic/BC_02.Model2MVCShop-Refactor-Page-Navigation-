package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;


public class UpdateTranCodeByProdAction extends Action{

	public String execute(	HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchaseByProd(prodNo);

		purchase.setTranCode(request.getParameter("tranCode"));
		service.updateTranCode(purchase);

		return "forward:/listProduct.do?menu=manage";
	}
}