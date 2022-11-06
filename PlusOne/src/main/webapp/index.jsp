<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>PlusOner</title>
    <link rel="icon" href="/assets/images/logo.png" type="image/png">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="/js/homepage.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/homepage.css"/>
</head>

<style>
body{
  background: #EEEEEE;
  text-align: center;
  margin: 0;
}

.wrap{
  width: 100%;
  margin-top:10vh;
}

</style>

<body>
   <%@ include file="/jsp/navBar.jsp" %>
   <div class="wrap">
        <h1>The Local Store</h1>
        <div class="data">
        <div id="ProductCount">
            <h1>Products count</h1>
            <h1 id="ProductCountText"></h1>
        </div>
        <div id="ProductTotalWorth">
            <h1>Total net worth</h1>
            <h1 id="ProductTotalWorthText"></h1>
        </div>
        <div id="EmployeeCount">
            <h1>Employee Count</h1>
            <h1 id="EmployeeCountText">12</h1>
        </div>
        </div>

        <div class="data">
            <div id="LowStock">
                <h1>LowStock items</h1>
                <h1 id="LowStockText">2</h1>
            </div>
            <div id="TodayTransactions">
                <h1>Today Transactions</h1>
                <h1 id="ProductTotalWorthText">15</h1>
            </div>
        </div>
   </div>
</body>
</html>