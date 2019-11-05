1. JSON Parser 
    https://github.com/square/moshi

2. Sample API JSON
    https://api.edamam.com/search?q=steak&app_id=3ef87764&app_key=f6329aeb0ce6a806b529977877a9b5a4%20&from=0&to=2&calories=700-800&diet=low-fat

3. Objects for JSON mapping
    
    
[API] Rating:
    1. User u7890 create rating for recipe id id123
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
    2. User u7890 update rating for recipe id id123
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
                 "data": 3.25
             }
