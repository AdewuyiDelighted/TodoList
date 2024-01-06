**Introduction**
This todolist program can be used to minimal task management for users,Users of this app can create task,update task,tick-out task,delete task.
This application is built using maven springboot and mongobd database
Features:
User registration
User login
Create a task in todolist
Update a task in todolist
Tick out task in todolist
View a task in todolist
View all tasks in todolist
Delete a task in todolist
Delete all tasks todolist
**GET
RegisterRequest**
This end-point enable a new user registration,it accept a username,password that must be 8 - 20 characters(must consist uppercase,lowercase,digit,and characters)
A code sample in Json using postman is shown below
Request
Url:https://localhost:8081/api/register
Method : POST
Header:Content-Type:application/json

**Body:**


JSON








{
"username":"Deligthed",
"password":"Semicolon22"
}


Fields:

> username(required,String):The username of the user
> password(required,String):The password of the user
Response 1
successful request
Status code : 201 created
Body



JSON








{
"data"
"message":"Account created"
"isSuccessful":true
}


Response 2
unsuccessful request due to creating with same username
Status code:400 bad request
Body



JSON










"data"{
"message":"User exit already"
}
"isSuccessful":false






Response 3
unsuccessful request due wrong password format
Status code:400 bad request
Body



JSON








"data"{
"message":"Password too weak"
}
"isSuccessful":false

**GET
LoginRequest**

This endpoint is for the verification of user's details,To ensure that user enter the right detail before accessing other todolist features
It accept user's username and password
A code sample in Json using postman is used below
Request
Url: http://localhost:8081/api/login
Method: POST
Header

> Content-Type :application/json
Body:



JSON








{


"username":"Deligthed",
"password":"Semicolon22"
}




Fields:

> usernamel(required,String):The username of the user
> password(required,String):The passwordof the user
Response 1
successful request.
Status code: 200 ok
Body:



JSON








"data"{
"message":"Account Unlocked"
}
"isSuccessful":true






Response 2
unsuccessful request due to incorrect username of password
Status code: 400 bad request
Body:



JSON








{
"Invalid details"
}
**GET
addTaskRequest**
This end point enable user to be able to create a new task,This endpoint takes in username,title,description and it return return message
A code sample of Json using postman is shown below
Request
Url:https//localhost:8081/api/addTask
Method post
Header

> Content -Type: application/json
Body:



JSON








{
"username":"Delighted"
"title":"House errand"
"description":"Cook beans"
}


Fields:
>username(required,String):Todolist username
> title(required,String):New or old title of the task that want to be added
>description(required,String):The task content
Response 1
successful request.
Status code: 200 ok
Body:


JSON








{
"data"{
"message":"Task added"
}
"isSuccessful":true
}




Response 2
unsuccessful request due to incorrect username
Status code: 400 bad request
Body:


JSON








{
"Invalid details"
}
**GET
updateTask**

This endpoint allow user to update task that have been added by them before,It's accept username,the title,the previous task,and new task
A code sample in Json in postman
Request
Url:https//localhost:8081:api:updateTask
Method : POST
Header:

> Content-Type : application/json
Body



JSON








{


"username":"Delighted",
"title":"Home errand",
"oldDescription":"",
"newDescription":""
}




Fields:

username(required,String):username todolist name
title(required,String):title of the task that would be updated,
oldDescription(required,String):The description of former task
newDescription(required,String):The new description of new task
Response 1
successful request.
Status code: 200 ok
Body:


JSON








{
"data"{
"message":"Task updated"
}
"isSuccessful":true
}
**GET
tickOutTask**
This endpoint isenable to tick out the task that user's have completed,it accept the username,title,description,and reponse
Request
Url:https://localhost:8081/api/tickOutTask
Method : DELETE
Response
successful request.
Status code: 200 ok
Body:


JSON








{
"data"{
"message":"Task ticked out"
}
"isSuccessful":true
}

**GET
findAllTasks**

This endpoint allow user to be able to view all tasks they have added and haven't ticked out
It accept user's username
A code sample in Json using postman is used below
Request
Url: http://localhost:8081/api/findAllTask
Method: GET

Header

> Content-Type :application/json
Body:



JSON








{
"username":"Deligthed",
}


Field

> username(required,String):The username of the user
Response 1
successful request
Status code : 201 created
Body



JSON








{
"username":"Delighted",
"title": "School work",
"description": "Do assignment",
"isCompleted": false
},
}
"title": "Church work",
"description": "choirpratice",
"isCompleted": false
}
"isSuccessful":true
}

**GET
deleteTaskRequest**

This end point enable user be able to delete a task,It accept the user's username,title and the body
Request
Url:https//localhost:8081/api/deleteAtask
Method:DELETE
Header

> Content-Type :application/json
Body:



JSON








{
"username":"Deligthed",
"title":"house errand",
"description":"wash plate"
}


Field

> username(required,String):The username of the user
> title(required,String):The title of the required task
> description(required,String):The description of the task
Response 1
successful request.
Status code: 200 ok
Body:


JSON








{
"data"{
"message":"Task deleted"
}
"isSuccessful":true
}
**GET
findATask**
This end point enable user be able to view
a task,It accept the user's username,title and the body
Request
Url:https//localhost:8081/api/deleteATask
Method:GET
Header

> Content-Type :application/json
Body:



JSON








{
"username":"Delighted",
"title":"house errand",
"description":"wash plate"
}


Field

> username(required,String):The username of the user
> title(required,String):The title of the required task
> description(required,String):The description of the task
Response 1
successful request.
Status code: 200 ok
Body:


JSON








{
"data"{
"message":""
}
"isSuccessful":true
}
**GET
deleteAllTaskRequest**

This endpoint allow user to be able to delete all tasks they have added and haven't ticked out
It accept user's username
A code sample in Json using postman is used below
Request
Url: http://localhost:8081/api/deleteAllTask
Method: DELETE

Header

> Content-Type :application/json
Body:



JSON








{
"username":"delighted"
}


Field

> username(required,String):The username of the user
Response
successful request.
Status code: 200 ok
Body:


JSON








{
"data"{
"message":"All tasks deleted"
}
"isSuccessful":true
}

**GET
deleteTodoListRequest**

This endpoint allow user to be able to delete their todolist account
It accept user's username
A code sample in Json using postman is used below
Request
Url: http://localhost:8081/api/deleteToDoList
Method: DELETE

Header

> Content-Type :application/json
Body:



JSON








{
"username":"delighted"
}


Field

> username(required,String):The username of the user
Response
successful request.
Status code: 200 ok
Body:


JSON








{
"data"{
"message":"Account deleted"
}
"isSuccessful":true
}
































































