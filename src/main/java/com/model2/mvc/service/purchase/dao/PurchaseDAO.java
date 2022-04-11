package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class PurchaseDAO {

	public PurchaseDAO() {
	}

	public void addPurchase(Purchase purchase) throws Exception {

		System.out.println("\n* [ PurchaseDAO : insertPurchase() ] ");

		Connection con = DBUtil.getConnection();

		String sql = "insert into TRANSACTION values (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, '1', sysdate, ?)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getDivyDate());
		stmt.executeUpdate();

		con.close();
	}
	
	public Map<String,Object> getPurchaseList(Search search,String buyerId) throws Exception{
	      
	      Map<String, Object> map = new HashMap<String, Object>();
	      
	      Connection con = DBUtil.getConnection();
	      
	      String sql = "SELECT t.tran_no, t.buyer_id, t.receiver_name, t.receiver_phone, t.tran_status_code from users u, transaction t \n "
	            + "WHERE u.user_id = t.buyer_id and t.buyer_id='"+buyerId+"' \n"
	            + "ORDER BY t.tran_no";
	      
	      System.out.println("PurchaseDAO ::Original SQL :: " + sql);
	      
	      int totalCount = this.getTotalCount(sql);
	      System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
	      
	      sql = makeCurrentPageSql(sql, search);
	      PreparedStatement stmt = con.prepareStatement(sql);
	      ResultSet rs = stmt.executeQuery();
	      
	      System.out.println(search);
	      
	      List<Purchase> list = new ArrayList<Purchase>();
	      
	      if (totalCount > 0) {
	         while(rs.next()){
	            Purchase purchase = new Purchase();
	            purchase.setTranNo(rs.getInt("TRAN_NO"));
	            purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
	            purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
	            purchase.setTranCode(rs.getString("TRAN_STATUS_CODE").trim());
	                                    
	            list.add(purchase);
	         }
	      }
	      
	      System.out.println("aaaaa : "+list);
	      
	      map.put("totalCount", new Integer(totalCount));
	      map.put("list", list);
	            
	      rs.close();
	      stmt.close();
	      con.close();
	      
	      System.out.println("aaaaa : "+map);

	         
	      return map;
	   }
		
		
	
	public Purchase getPurchaseByTran(int tranNo) throws Exception {

		System.out.println("\n* [ PurchaseDAO : findPurchase() ] ");

		Connection con = DBUtil.getConnection();

		String sql = "select * from TRANSACTION where TRAN_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		ResultSet rs = stmt.executeQuery();

		User user = null;
		Product product = null;
		Purchase purchase = null;

		while (rs.next()) {
			user = new User();
			product = new Product();
			purchase = new Purchase();
			purchase.setTranNo(rs.getInt(1));
			product.setProdNo(rs.getInt(2));
			purchase.setPurchaseProd(product);
			user.setUserId(rs.getString(3));
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString(4));
			purchase.setReceiverName(rs.getString(5));
			purchase.setReceiverPhone(rs.getString(6));
			purchase.setDivyAddr(rs.getString(7));
			purchase.setDivyRequest(rs.getString(8));
			purchase.setTranCode(rs.getString(9));
			purchase.setOrderDate(rs.getDate(10));
			purchase.setDivyDate(rs.getString(11));
		}

		con.close();

		return purchase;
	}
	
	public Purchase getPurchaseByProd(int prodNo) throws Exception {

		System.out.println("\n* [ PurchaseDAO : findPurchase2() ] ");

		Connection con = DBUtil.getConnection();

		String sql = "select * from TRANSACTION where PROD_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		User user = null;
		Product product = null;
		Purchase purchase = null;

		while (rs.next()) {
			user = new User();
			product = new Product();
			purchase = new Purchase();
			purchase.setTranNo(rs.getInt(1));
			product.setProdNo(rs.getInt(2));
			purchase.setPurchaseProd(product);
			user.setUserId(rs.getString(3));
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString(4));
			purchase.setReceiverName(rs.getString(5));
			purchase.setReceiverPhone(rs.getString(6));
			purchase.setDivyAddr(rs.getString(7));
			purchase.setDivyRequest(rs.getString(8));
			purchase.setTranCode(rs.getString(9));
			purchase.setOrderDate(rs.getDate(10));
			purchase.setDivyDate(rs.getString(11));
		}

		con.close();

		return purchase;
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {

		System.out.println("\n* [ PurchaseDAO : updatePurchase() ] ");

		Connection con = DBUtil.getConnection();

		String sql = "update TRANSACTION set RECEIVER_PHONE=?, DEMAILADDR=?, DLVY_REQUEST=?, DLVY_DATE=? where tran_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getReceiverPhone());
		stmt.setString(2, purchase.getDivyAddr());
		stmt.setString(3, purchase.getDivyRequest());
		stmt.setString(4, purchase.getDivyDate());
		stmt.setInt(5, purchase.getTranNo());
		stmt.executeUpdate();

		con.close();
	}

	public void updateTranCode(Purchase purchase) throws Exception {

		System.out.println("\n* [ PurchaseDAO : updateTranCode() ] ");

		Connection con = DBUtil.getConnection();

		String sql = "update TRANSACTION set TRAN_STATUS_CODE=? where tran_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());
		stmt.executeUpdate();

		con.close();
	}
	
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	// 게시판 currentPage Row 만 return
	private String makeCurrentPageSql(String sql, Search search) {
		
		System.out.println("1jj : " +search.getCurrentPage());
		System.out.println("kk: " +search.getPageSize());
		
		sql = "SELECT * " 
				+ "FROM ( SELECT inner_table. * ,  ROWNUM AS row_seq " 
						+ " FROM ( " + sql + " ) inner_table " 
						+ "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ "WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("PurchaseDAO :: make SQL :: " + sql);

		return sql;
	}
	
}