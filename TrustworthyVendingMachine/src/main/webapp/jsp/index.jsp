<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vending Machine</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">        
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Black+Ops+One" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="left-side col-md-8">
            <c:forEach var="disItem" items="${items}">
                <a href="Item/selectItem?id=${disItem.itemId}">
                    <div class="col-md-4 box text-center" style="background-image: url(css/${disItem.itemId}.2.png)">
                        <br><br><br>
                        <br><br><br><br>
                        <div>
                            <c:out value="$${disItem.price} | QTY:${disItem.quantity}"/>
                        </div>
                    </div>
                </a>
            </c:forEach>
            </div>
            <div class=" col-md-4">
                <div class="row text-center">
                    <div>
                        <h3>Total $ In</h3>
                    </div>
                    
                    <div class='row text-center'>
                        <div id='total-display'>
                            <c:out value="${total}"/>
                        </div>
                    </div>
                    <form role="form" method="POST" action="Money/insertedTotal">
                    <div class='col-xs-4 text-center'>
                        <button class="btn" id="btn-dollar" type='submit' id='addDollar' name='amount' value='dollar'/>
                    </div>
                    <div class='col-xs-3 text-center'>
                        <button class="btn" id="btn-quarter" type='submit' id='addQuarter' name='amount' value='quarter'/>
                    </div>
                    <div class='col-xs-3 text-center'>
                        <button class="btn" id="btn-dime" type='submit' id='addDime' name='amount' value='dime'/>
                    </div>
                    <div class='col-xs-2 text-center'>
                        <button class="btn" id="btn-nickel" type='submit' id='addNickel' name='amount' value='nickel'/>
                    </div>
                    </form>
                </div>
                <hr>
                <div class="row text-center">
                    <div>
                        <h3>Messages</h3>
                    </div>
                    <div class='row text-center' id='total-require'>
                        <marquee id="messages" behavior="scroll" direction="left">
                            <c:out value="${messages}"/>
                        </marquee>
                    </div>
                    <form role="form" method="POST" action="Item/vendItem">
                    <div class='row'>
                        <label for='qty' class='text-center row control-label' id='qty-box'>
                            <h4 id='itemHead'>Items:</h4>
                        </label>
                        <div class='text-center row'>
                            <div id='total-require'> <c:out value="${item.name}"/></div>
                        </div>
                    </div>
                    <div class='col-md-12'>
                        <button class='btn btn-purchase' type='submit' value="${item.itemId}" name="item" id='purchase-button'>
                            <p>Make a purchase</p>
                        </button>
                    </div>
                    </form>
                </div>
                <hr>
                <div class="row text-center">
                    <div class='row form-group'>
                        <label for='qty' class='row control-label' id='qty-box'>
                            <h4>Change:</h4>
                        </label>
                        <div class='row text-center' id='change-display'>
                            <div id="change">
                                <c:out value="${changes}"/>
                            </div>
                        </div>
                        <div class='row text-center' id='getChangeButtonDiv'>
                            <a href="Money/refund">
                                <button class='btn btn-purchase' type='button' id='refund-button'>
                                    <p>Refund</p>
                                </button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

