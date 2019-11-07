1. JSON Parser 
    https://github.com/square/moshi

2. Sample API JSON
    https://api.edamam.com/search?q=steak&app_id=3ef87764&app_key=f6329aeb0ce6a806b529977877a9b5a4%20&from=0&to=2&calories=700-800&diet=low-fat

3. Objects for JSON mapping
    
    
[API] Rating:
    1. User u7890 create rating for recipe id id123
        POST    http://localhost:8080/users
                      {
                        	"username":"user1",
                        	"password":"psw2",
                        	"email":"user3@sem.com"
                      }

         -> 200
                {
                    "data": {
                        "id": "dXNlcjNAc2VtLmNvbQ==",
                        "username": "user1",
                        "email": "user3@sem.com"
                    }
                }

    2. Get user info
        PUT    http://localhost:8080/ratings/user/u7890
                    {
                        "id":"id123",
                        "rating":4
                    }
        -> 200
         {
            "data": {
                    "id": "id123",
                    "userid": "u7890",
                    "rating": 4
                }
          }
            
    3. Read average rating of recipe id123
        GET http://localhost:8080/ratings/id123
         -> 200
             {
                 "data": {
                     "id": "id123",
                     "count": 9,
                     "average": 3.6666666666666665
                 }
             }

[API] User:
    1. Create user
        POST    http://localhost:8080/ratings/user/u7890
                    {
                        "id":"id123",
                        "rating":2
                    }
         -> 200
         {
            "data": {
                    "id": "id123",
                    "userid": "u7890",
                    "rating": 2
                }
          }
    2. Get user info
        GET    http://localhost:8080/users/userid
        -> 200
                {
                    "data": {
                        "id": "userid",
                        "username": "user1",
                        "email": "user3@sem.com"
                    }
                }
        -> 404

    3. Update user password
        PUT http://localhost:8080/users/dXNlcjNAc2VtLmNvbQ==
         -> 200
             {
                 "data": "Update Successful"
             }

[API] Shopping List:
    1. Create shopping list
        POST    http://localhost:8080/shoppinglist/user/userid
            {
                "name": "Dan3's shopping list",
                "items": [
                    {
                        "id": "I123",
                        "name": "ITEM TEST 3",
                        "qty": 10,
                        "unit": "g"
                    },
                    {
                        "id": "I123",
                        "name": "ITEM TEST 3",
                        "qty": 10,
                        "unit": "g"
                    }
                ]
            }
         -> 200
            {
                "data": {
                    "id": "shoppinglistid",
                    "name": "Dan3's shopping list",
                    "userid": "dXNlcjFAc2VtLmNvbQ==",
                    "items": [
                        {
                            "unit": "g",
                            "qty": 10,
                            "name": "ITEM TEST 3",
                            "id": "I123"
                        },
                        {
                            "unit": "g",
                            "qty": 10,
                            "name": "ITEM TEST 3",
                            "id": "I123"
                        }
                    ]
                }
            }
    2. Get all shopping lists of a user
        GET    http://localhost:8080/shoppinglist/users/userid/all
        -> 200
        -> 200 []

    3. Get a shopping list
        GET http://localhost:8080/shoppinglist/shoppinglistid
         -> 200

    4. Update a shooping list
        PUT http://localhost:8080/shoppinglist/shoppinglistid
            * update entire item list
        -> 200