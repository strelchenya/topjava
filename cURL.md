## Meal API


| Type request | Method | Link | Content-Type | JSON request |
| ------ | ------ | ------ | ------ |  ------ |
| **POST**   | [Create meal](http://localhost:8080/topjava/rest/meals "") | http://localhost:8080/topjava/rest/meals | application/json | ```{ "dateTime": "2021-02-02T02:02:02", "description": "Полдник", "calories": 123 }```|
| **DELETE** | [Delete meal](http://localhost:8080/topjava/rest/meals/100006) | http://localhost:8080/topjava/rest/meals/100006 | application/json | |
| **PUT** | [Update meal](http://localhost:8080/topjava/rest/meals/100005) | http://localhost:8080/topjava/rest/meals/100005 | application/json |```{ "id":100005, "dateTime":"2022-12-22T22:22:22", "description":"Полдник", "calories":123 }```|
| **GET** | [Get meals](http://localhost:8080/topjava/rest/meals) | http://localhost:8080/topjava/rest/meals | application/json | |
| **GET** | [Get meal](http://localhost:8080/topjava/rest/meals/100005) | http://localhost:8080/topjava/rest/meals/100005 | application/json | |
| **GET** | [Get meals between](http://localhost:8080/topjava/rest/meals/filter?start-date=&end-date=2020-01-30&start-time=13:00&end-time=15:00) | http://localhost:8080/topjava/rest/meals/filter?start-date=&end-date=2020-01-30&start-time=13:00&end-time=15:00 | application/json | |

###### [CREATE MEAL]()
```yaml
{
"id": 100011,
"dateTime": "2021-02-02T02:02:02",
"description": "Полдник",
"calories": 123
}
```
###### [DELETE DATA]()
```text
HTTP/1.1 204
Date: Tue, 30 Nov 2021 16:07:29 GMT
Keep-Alive: timeout=20
Connection: keep-alive

<Response body is empty>

Response code: 204; Time: 80ms; Content length: 0 bytes
```
###### [UPDATE DATA]()
```yaml
{
"id":100005,
"dateTime":"2022-12-22T22:22:22",
"description":"Полдник",
"calories":123
}
```
###### [GET MEALS DATA]()
```yaml
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 30 Nov 2021 16:13:16 GMT
Keep-Alive: timeout=20
Connection: keep-alive
```
```yaml
[
{
"id": 100008,
"dateTime": "2020-01-31T20:00:00",
"description": "Ужин",
"calories": 510,
"excess": false
},
{
"id": 100007,
"dateTime": "2020-01-31T13:00:00",
"description": "Обед",
"calories": 1000,
"excess": false
},
{
"id": 100005,
"dateTime": "2020-01-31T00:00:00",
"description": "Еда на граничное значение",
"calories": 100,
"excess": false
},
{
"id": 100004,
"dateTime": "2020-01-30T20:00:00",
"description": "Ужин",
"calories": 500,
"excess": false
},
{
"id": 100003,
"dateTime": "2020-01-30T13:00:00",
"description": "Обед",
"calories": 1000,
"excess": false
},
{
"id": 100002,
"dateTime": "2020-01-30T10:00:00",
"description": "Завтрак",
"calories": 500,
"excess": false
}
]
```
###### [GET MEAL DATA]()
```yaml
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 30 Nov 2021 16:16:10 GMT
Keep-Alive: timeout=20
Connection: keep-alive
```
```yaml
{
"id": 100005,
"dateTime": "2020-01-31T00:00:00",
"description": "Еда на граничное значение",
"calories": 100,
"user": null
}
```
###### [GET MEAL BETWEEN DATA]()
```yaml
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 30 Nov 2021 16:17:32 GMT
Keep-Alive: timeout=20
Connection: keep-alive
```
```yaml
[
{
"id": 100003,
"dateTime": "2020-01-30T13:00:00",
"description": "Обед",
"calories": 1000,
"excess": false
}
]
```
```text
Response code: 200; Time: 81ms; Content length: 100 bytes
```
