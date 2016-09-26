<%@ page import="com.parking.service.persistence.model.Client" %>


<div align="center">
    <nav>
        <!-- Navigation -->
        <ul class="dropdownMenu">
            <li><a href="/listparkings">Parkings</a>
            </li>

            <li><a href="#">Invoices</a>
                <ul class="droprightMenu">
                    <li><a href="/listinvoices">List Invoices</a></li>
                    <li><a href="/generateinvoices">Re-Generate</a></li>
                    </ul>
            </li>
	<li style="position:absolute;right: 10px;"><a href="/logout">Logout</a>
	</li>
        </ul>
    </nav>
</div>
<div style="font-size: 11px;font-weight:bold;position: absolute;left:30px;">
Welcome <%=((Client)request.getSession().getAttribute("user")).getUserName().toUpperCase()%>
</div>