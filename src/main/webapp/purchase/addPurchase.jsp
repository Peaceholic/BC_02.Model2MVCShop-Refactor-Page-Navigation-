<%@ page contentType="text/html; charset=EUC-KR" %>

<%@page import="com.model2.mvc.service.domain.Purchase"%>

<%
	Purchase purchase = (Purchase)request.getAttribute("purchase");
	System.out.println("aaa" +purchase);
%>

<html>
<head>
<title>Insert title here</title>
<script type="text/javascript">
<!--
function fncupdatePurchase() {
	document.updatePurchase.submit();
}
-->
</script>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=<%=purchase.getTranNo()%>" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1 cellspacing="5">
	<tr>
		<td width="200" height="50">��ǰ��ȣ</td>
		<td><%=purchase.getPurchaseProd().getProdNo() %></td>

	</tr>
	<tr>
		<td width="200" height="50">�����ھ��̵�</td>
		<td><%=purchase.getBuyer().getUserId() %></td>

	</tr>
	<tr>
		<td width="200" height="50">���Ź��</td>
		<td>
		<%if(purchase.getPaymentOption().trim().equals("1")){%>
			���ݱ���
		<%}else{%>
			�ſ뱸��
		<%} %>
		</td>
	
	</tr>
	<tr>
		<td width="200" height="50">�������̸�</td>
		<td><%=purchase.getReceiverName() %></td>

	</tr>
	<tr>
		<td width="200" height="50">�����ڿ���ó</td>
		<td><%=purchase.getReceiverPhone() %></td>

	</tr>
	<tr>
		<td width="200" height="50">�������ּ�</td>
		<td width="500" ><%=purchase.getDivyAddr() %></td>

	</tr>
		<tr>
		<td width="200" height="50">���ſ�û����</td>
		<td><%=purchase.getDivyRequest() %></td>

	</tr>
	<tr>
		<td width="200" height="50">����������</td>
		<td><%=purchase.getDivyDate() %></td>
	</tr>
</table>


</form>

</body>
</html>