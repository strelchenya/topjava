POST http://localhost:8080/topjava/rest/users/meals
Content-Type: application/json

{
"dateTime": "2021-02-02T02:02:02",
"description": "Полдник",
"calories": 123
}

<> 2021-11-29T212947.201.json

###
GET http://localhost:8080/topjava/rest/users/meals

<> 2021-11-29T212258.200.json

###
GET http://localhost:8080/topjava/rest/users/meals/100005

<> 2021-11-29T212343.200.json

###
DELETE http://localhost:8080/topjava/rest/users/meals/100006

###

PUT http://localhost:8080/topjava/rest/users/meals/100005
Content-Type: application/json

{
"id":100005,
"dateTime":"2022-12-22T22:22:22",
"description":"Полдник",
"calories":123
}

###
GET http://localhost:8080/topjava/rest/users/meals/filter?start-date=&end-date=2020-01-30&start-time=13:00&end-time=15:00

<> 2021-11-29T213519.200.json