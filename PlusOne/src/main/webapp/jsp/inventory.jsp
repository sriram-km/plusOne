<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>PlusOner</title>
    <link rel="icon" href="/assets/images/logo.png" type="image/png">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/inventory.css"/>
</head>


<body>
   <%@ include file="/jsp/navBar.jsp" %>

   <div class="wrap">
   <div class="topBar">
        <button id="syncData">
            <i class="fas fa-sync-alt"></i>
        </button>

        <button id="addData">
            <i class="fas fa-plus"></i>
        </button>

        <input type="text" id="searchDataInput"/>
        <button id="searchData">
            <i class="fas fa-search"></i>
        </button>

        <button id="prevPage">
                <i class="fas fa-angle-double-left"></i>
        </button>

        <p id="pagination"> 1/10 </p>

        <button id="nextPage">
            <i class="fas fa-angle-double-right"></i>
        </button>
    </div>
    <div class="data">
        <table id="productTable">
          <tr>
            <th>SKU</th>
            <th>Name</th>
            <th>Units</th>
            <th>Unit price</th>
            <th>Updated time</th>
            <th>Action</th>
          </tr>
        </table>
    </div>
   </div>

   <div class="formContainer" id="addProductFormDiv">
     <button id="closeButton">
        <i class="fas fa-times"></i>
     </button>
     <form id="addProductForm">
       <label for="sku">SKU</label>
       <input type="text" id="sku" name="SKU" placeholder="ABC1234X">

       <label for="name">Name</label>
       <input type="text" id="name" name="NAME" placeholder="Your product name">

        <label for="units">Units</label>
        <input type="number" id="units" name="UNITS" placeholder="Units count">

        <label for="unitprice">Unit Price</label>
        <input type="number" step=0.01 id="units_price" name="Unit Price" placeholder="123.45">

       <label for="description">Description</label>
       <textarea id="description" name="DESCRIPTION" placeholder="Write something.." style="height:200px"></textarea>

       <input id="addProductButton" type="button" value="Add">
     </form>
   </div>

   <div class="formContainer" id="editProductFormDiv">
        <button id="closeButton">
           <i class="fas fa-times"></i>
        </button>
        <form id="editProductForm">
          <label for="sku">SKU</label>
          <input type="text" id="sku" name="SKU" disabled>

          <label for="name">Name</label>
          <input type="text" id="name" name="NAME" disabled>

           <label for="units">Units</label>
           <input type="number" id="units" name="UNITS" placeholder="Units count">

           <label for="unitprice">Unit Price</label>
           <input type="number" step=0.01 id="units_price" name="Unit Price" placeholder="123.45">

          <label for="description">Description</label>
          <textarea id="description" name="DESCRIPTION" placeholder="Write something.." style="height:200px"></textarea>

          <input id="updateProductButton" type="button" value="Edit">
        </form>
      </div>
</body>
</html>

<script src="/js/inventory.js"></script>