# spring-framework-5-webapp-heroku

    Login in master branch:
    Rol admin -> admin admin
    Rol user -> drohne drohne

   [web](https://spring-framework-5-app.herokuapp.com/)
     
      If after do login with user or admin data you have http 403 error code:
      The jwt branch is deployed instead of master branch.
      
      You can try to take a Jwt token doing post from:
      https://spring-framework-5-app.herokuapp.com/api/login 
      In the body in json format:
      
      {
        "username": "admin",
        "password": "admin"
      }
      or
      {
        "username": "drohne",
        "password": "drohne"
      }
   
      Available endpoints: Authentication required
      [GET] https://spring-framework-5-app.herokuapp.com/api/partner/list-partner
      

