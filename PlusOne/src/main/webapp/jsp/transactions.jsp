<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>PlusOner</title>
    <link rel="icon" href="/assets/images/logo.png" type="image/png">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/transactions.css"/>
</head>


<body>
   <%@ include file="/jsp/navBar.jsp" %>

   <div class="wrap">
      <div class="topBar">
           <button id="prevPage">
                   <i class="fas fa-angle-double-left"></i>
           </button>

           <p id="pagination"> 1/1 </p>

           <button id="nextPage">
               <i class="fas fa-angle-double-right"></i>
           </button>
       </div>
       <div class="data">
           <table id="productTable">
             <tr>
               <th>SKU</th>
               <th>Name</th>
               <th>Units Before</th>
               <th>Transaction</th>
               <th>Units Before</th>
               <th>Updated time</th>
             </tr>
           </table>
       </div>
      </div>

</body>
</html>

<script src="/js/transactions.js"></script>
