package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class ProductDAO {

	public ProductDAO() {
	}

	public Product findProduct(int prodNo) throws Exception {

		System.out.println("\n* [ ProductDAO : findProduct ] ");

		Connection con = DBUtil.getConnection();

		String sql = "select * from PRODUCT where PROD_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product productVO = null;

		while (rs.next()) {
			productVO = new Product();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
		}

		con.close();

		return productVO;
	}

	public Map<String, Object> getProductList(Search search) throws Exception {

		System.out.println("\n* [ ProductDAO : getProductList() ] ");

		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT p.prod_no, p.prod_name, p.price, p.reg_date, t.tran_status_code FROM product p, transaction t \n "
	            +"where ";
	      
	      if (search.getSearchCondition() != null) {
	         if (search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("")) {
	            sql += " p.prod_no LIKE '%" + search.getSearchKeyword()
	                  + "%' and";
	         } else if (search.getSearchCondition().equals("1") &&  !search.getSearchKeyword().equals("")) {
	            sql += " p.prod_name LIKE '%" + search.getSearchKeyword()
	                  + "%' and";
	         } else if (search.getSearchCondition().equals("2") &&  !search.getSearchKeyword().equals("")) {
	            sql += " p.price LIKE '%" + search.getSearchKeyword()
	                  + "%' and";
	         }         
	      }
	      sql += " p.prod_no = t.prod_no(+) \n"
	            + "order by prod_no";
		
		System.out.println("ProductDAO :: Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount :: " + totalCount);
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setProdNo(rs.getInt(1));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setPrice(rs.getInt("PRICE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			if (rs.getString("TRAN_STATUS_CODE") == null) {
				product.setProTranCode("0");
			} else {
				product.setProTranCode(rs.getString("TRAN_STATUS_CODE").trim());
			}
			list.add(product);

			if (!rs.next())
				break;
		}
		
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

	public void insertProduct(Product provo) throws Exception {

		System.out.println("\n* [ ProductDAO : insertProduct() ] ");

		Connection conn = DBUtil.getConnection();

		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval, ?, ?, ?, ?, ?, SYSDATE)";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, provo.getProdName());
		pstmt.setString(2, provo.getProdDetail());
		pstmt.setString(3, provo.getManuDate());
		pstmt.setInt(4, provo.getPrice());
		pstmt.setString(5, provo.getFileName());
		pstmt.executeUpdate();

		conn.close();
	}

	public void updateProduct(Product provo) throws Exception {

		System.out.println("\n* [ ProductDAO : updateProduct() ] ");

		Connection conn = DBUtil.getConnection();

		String sql = "UPDATE product set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=? where PROD_NO=?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, provo.getProdName());
		pstmt.setString(2, provo.getProdDetail());
		pstmt.setString(3, provo.getManuDate());
		pstmt.setInt(4, provo.getPrice());
		pstmt.setString(5, provo.getFileName());
		pstmt.setInt(6, provo.getProdNo());
		pstmt.executeUpdate();

		conn.close();
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

	// �Խ��� currentPage Row �� return
	private String makeCurrentPageSql(String sql, Search search) {
		
		System.out.println("hh: " +search.getCurrentPage());
		System.out.println("gg : " +search.getPageSize());
		
		
		sql = "SELECT * " 
				+ "FROM ( SELECT inner_table. * ,  ROWNUM AS row_seq " 
						+ " FROM ( " + sql + " ) inner_table " 
						+ "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ "WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("ProductDAO :: make SQL :: " + sql);

		return sql;
	}
}