<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

<#--Introduce sidebar-->
    <#include "../common/nav.ftl">

<#--main content-->
  <div id="page-content-wrapper">
    <div class="container-fluid">
      <div class="row clearfix">
        <div class="col-md-12 column">
          <table class="table table-bordered table-condensed">
            <thead>
            <tr>
              <th>Order ID</th>
              <th>Name</th>
              <th>Phone Number</th>
              <th>Address</th>
              <th>Amount</th>
              <th>Payment Status</th>
              <th>Order Time</th>
              <th colspan="2">Action</th>
            </tr>
            </thead>
            <tbody>
                <#list orderDTOPage.content as orderDTO>
                <tr>
                  <td>${orderDTO.orderId}</td>
                  <td>${orderDTO.buyerName}</td>
                  <td>${orderDTO.buyerPhone}</td>
                  <td>${orderDTO.buyerAddress}</td>
                  <td>$ ${orderDTO.orderAmount}</td>
                  <td>${orderDTO.getOrderStatusEnum().message}</td>
                  <td>${orderDTO.createTime}</td>
                  <td><a
                      href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">Detail</a>
                  </td>
                  <td>
                    <#if orderDTO.getOrderStatusEnum().message == "NEW">
                      <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">Cancel</a>
                    </#if>
                  </td>
                </tr>
                </#list>
            </tbody>
          </table>
        </div>

      <#--paging-->
        <div class="col-md-12 column">
          <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">Prev</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">Prev</a>
                        </li>
                    </#if>

          <#--1 - some page-->
                    <#list 1..orderDTOPage.getTotalPages() as index>
                      <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                      <#else>
                            <li><a
                                href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                            </li>
                      </#if>
                    </#list>

                    <#if currentPage gte orderDTOPage.getTotalPages()>
                        <li class="disabled"><a href="#">Next</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">Next</a>
                        </li>
                    </#if>
          </ul>
        </div>
      </div>
    </div>
  </div>

</div>

<#--Pop up for websocket-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="myModalLabel">
          Remainder
        </h4>
      </div>
      <div class="modal-body">
        You have a new order!
      </div>
      <div class="modal-footer">
        <button onclick="javascript:document.getElementById('notice').pause()" type="button"
                class="btn btn-default" data-dismiss="modal">Close
        </button>
        <button onclick="location.reload()" type="button" class="btn btn-primary">View the order
        </button>
      </div>
    </div>
  </div>
</div>

<#--play music, a function provided by html5-->
<audio id="notice" loop="loop">
  <source src="/sell/mp3/song.mp3" type="audio/mpeg"/>
</audio>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<#--Configure websocket client side below-->
<script>
  var websocket = null;
  // If websocket exists in the browser
  if ('WebSocket' in window) {
    websocket = new WebSocket('ws://localhost:8080/sell/webSocket');
  } else {
    alert("You browser doesn't support websocket!");
  }
  // websocket is open
  websocket.onopen = function (event) {
    console.log('Connected');
  }

  // websocket is closed
  websocket.onclose = function (event) {
    console.log('Connection Closed');
  }

  // when there is message
  websocket.onmessage = function (event) {
    console.log('Get a Message:' + event.data)
    //pop ups shows, and play music, using jquey
    $('#myModal').modal('show');

    document.getElementById('notice').play();
  }

  // when there is error
  websocket.onerror = function () {
    alert('websocket Errors！');
  }

  // close websocket when closing browser
  window.onbeforeunload = function () {
    websocket.close();
  }

</script>

</body>
</html>