<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="./js/jquery.timepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="./css/jquery.timepicker.css"/>
    <link rel="stylesheet" type="text/css" href="./css/menu.css"/>
    <script type="text/javascript" src="./js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="./js/site.js"></script>
    <title>Invoices</title>
</head>
<body>
<%@include file="menu.jsp" %>

<div align="center">

    <c:if test="${not empty res }">
        <div style="color:green">
                ${res}
        </div>
    </c:if>
    <%--<c:if test="${not empty res && fn:contains(res,'phone')}">--%>
    <%--<div style="color:red">--%>
    <%--${res}--%>
    <%--</div>--%>
    <%--</c:if>--%>

    <c:if test="${empty res }">

        <div style="margin-left: 200px;">
            <div style="float: left;left:20px;">
                Totals:
                <table id="totals_table" border="1">

                    <thead>
                    <th>Client Name</th>
                    <th>Month</th>
                    <th>&#8364;Total</th>
                    </thead>
                    <tbody>
                    <c:forEach items="${totals}" var="total">

                        <tr>
                            <td>
                                    ${total.getClient().getUserName()}
                            </td>

                            <td>
                                    ${total.month}
                            </td>

                            <td>
                                    ${total.total}
                            </td>


                        </tr>

                    </c:forEach>

                    </tbody>

                </table>
            </div>
            <%if (!((Client) request.getSession().getAttribute("user")).isAdmin()) {%>
            Invoiced Parkings:
            <div style="position: relative;left:20px;">
                <table id="dt_basic" border="1">

                    <thead>
                    <th>Client Name</th>
                    <th>Client Type</th>
                    <th>Issue Date</th>
                    <th>&#8364;Total</th>
                    <th>Parking #</th>
                    </thead>
                    <tbody>
                    <c:forEach items="${invoices}" var="invoice">

                        <tr>
                            <td>
                                    ${invoice.getClient().getUserName()}
                            </td>

                            <td>
                                    ${invoice.getClient().getGroup().getName().name()}
                            </td>

                            <td>
                                    ${invoice.parkingDate}
                            </td>

                            <td>
                                &#8364;${invoice.total}
                            </td>
                            <td>
                               <div> <a href="/listparkings?parkingId=${invoice.getParking().getId()}">${invoice.getParking().getId()}</a></div>
                            </td>
                                <%--<td><fmt:formatDate pattern="MM/dd/yy" value="${league.createdDate}"/></td>--%>

                        </tr>

                    </c:forEach>

                    </tbody>

                </table>
            </div>

            <%}%>
        </div>


    </c:if>


</div>

</body>
<script>
    $(function () {
        $('#time').timepicker();
        $('#time').on('changeTime', function () {
            $('#onselectTarget').text($(this).val());
        });
    });
</script>

</html>