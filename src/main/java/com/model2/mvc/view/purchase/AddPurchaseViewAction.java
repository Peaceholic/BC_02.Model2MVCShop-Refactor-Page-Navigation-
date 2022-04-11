package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.Product;


public class AddPurchaseViewAction extends Action{
	
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));

		ProductService sevice = new ProductServiceImpl();
		Product product = sevice.getProduct(prodNo); 
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		request.setAttribute("product", product);
		request.setAttribute("user", user);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}