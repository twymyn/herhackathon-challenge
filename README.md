# challenge - backend app

This is a Spring Boot application, which can be easily deployed to Heroku

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Login Data for Commerzbank Sandbox

login: 1234567890

password: 12345

## Running Locally

### Running the Application

Make sure you have Java and Maven installed. Also, install the [Heroku CLI](https://cli.heroku.com/).

```sh
$ mvn install
$ heroku local web
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

## Authorization Code Flow

1. Login at Commerzbank Sanbox link provided at [localhost:5000/banks/commerz/login](http://localhost:5000/banks/commerz/login)
2. Callback endpoint should show the retrieved authorization code - this will be used by application to retrieve access token
3. Get list of security accounts and assets at [localhost:5000/assets](http://localhost:5000/assets)


## Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```

## Documentation

For more information about using Java on Heroku, see these Dev Center articles:

- [Java on Heroku](https://devcenter.heroku.com/categories/java)
