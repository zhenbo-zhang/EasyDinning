# API

###Product List

```
GET /sell/buyer/product/list
```

Parameters

```
None
```

Return

```
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "name": "hot",
            "type": 1,
            "foods": [
                {
                    "id": "123456",
                    "name": "burger",
                    "price": 1.2,
                    "description": "good",
                    "icon": "http://xxx.com",
                }
            ]
        },
        {
            "name": "cheese",
            "type": 2,
            "foods": [
                {
                    "id": "123457",
                    "name": "cheese cake",
                    "price": 10.9,
                    "description": "nice",
                    "icon": "http://xxx.com",
                }
            ]
        }
    ]
}
```


###Create Order

```
POST /sell/buyer/order/create
```

Parameter

```
name: "Sam"
phone: "xxxxxxxxxx"
address: "Happy Ave"
openid: "ew3euwhd7sjw9diwkq" 
items: [{
    productId: "1423113435324",
    productQuantity: 2 
}]

```

Return

```
{
  "code": 0,
  "msg": "success",
  "data": {
      "orderId": "147283992738221" 
  }
}
```

###Order List

```
GET /sell/buyer/order/list
```

Parameter

```
openid: 18eu2jwk2kse3r42e2e
page: 0 
size: 10
```

Return

```
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "orderId": "161873371171128075",
      "buyerName": "Sam",
      "buyerPhone": "xxxxxxxxxx",
      "buyerAddress": "Hope Ave",
      "buyerOpenid": "18eu2jwk2kse3r42e2e",
      "orderAmount": 0,
      "orderStatus": 0,
      "payStatus": 0,
      "createTime": 1490171219,
      "updateTime": 1490171219,
      "orderDetailList": null
    },
    {
      "orderId": "161873371171128076",
      "buyerName": "Same",
      "buyerPhone": "xxxxxxxxxx",
      "buyerAddress": "Hope Ave",
      "buyerOpenid": "18eu2jwk2kse3r42e2e",
      "orderAmount": 0,
      "orderStatus": 0,
      "payStatus": 0,
      "createTime": 1490171219,
      "updateTime": 1490171219,
      "orderDetailList": null
    }]
}
```

###Order Detail

```
GET /sell/buyer/order/detail
```

Parameter

```
openid: 18eu2jwk2kse3r42e2e
orderId: 161899085773669363
```

Return

```
{
    "code": 0,
    "msg": "success",
    "data": {
          "orderId": "161899085773669363",
          "buyerName": "John",
          "buyerPhone": "xxxxxxxxxx",
          "buyerAddress": "Good Place",
          "buyerOpenid": "18eu2jwk2kse3r42e2e",
          "orderAmount": 18,
          "orderStatus": 0,
          "payStatus": 0,
          "createTime": 1490177352,
          "updateTime": 1490177352,
          "orderDetailList": [
            {
                "detailId": "161899085974995851",
                "orderId": "161899085773669363",
                "productId": "157875196362360019",
                "productName": "ice tea",
                "productPrice": 9,
                "productQuantity": 2,
                "productIcon": "http://xxx.com",
                "productImage": "http://xxx.com"
            }
        ]
    }
}
```

###Cancel Order

```
POST /sell/buyer/order/cancel
```

Parameter

```
openid: 18eu2jwk2kse3r42e2e
orderId: 161899085773669363
```

Return

```
{
    "code": 0,
    "msg": "success",
    "data": null
}
```


