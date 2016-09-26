<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="./js/jquery.timepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="./css/jquery.timepicker.css" />
    <link rel="stylesheet" type="text/css" href="./css/menu.css" />
    <script type="text/javascript" src="./js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="./js/site.js"></script>
    <title>Invoices</title>
</head>
<body>
<%@include file="menu.jsp"%>

<div align="center">


    <table id="dt_basic" border="1"
            >

        <thead>
        <th>Client Name</th>
        <th>Entry Date</th>
        <th>Departure Date</th>
        <th>Parking House</th>
        </thead>
        <tbody>
        <c:forEach items="${parkings}" var="parking">

            <tr>
                <td>
                    ${parking.getClient().getUserName()}
                </td>

                <td>
                    ${parking.getEntryDate()}
                </td>

                <td>
                    ${parking.getDepartureDate()}
                </td>

                <td>
                    ${parking.getParkingHouse().getName()}
                </td>


            <%--<td><fmt:formatDate pattern="MM/dd/yy" value="${league.createdDate}"/></td>--%>

            </tr>

        </c:forEach>

        </tbody>

    </table>


</div>

</body>
<script>
    $(function() {
        $('#time').timepicker();
        $('#time').on('changeTime', function() {
            $('#onselectTarget').text($(this).val());
        });
    });
</script>

</html>