# challenge - backend app

This is a Spring Boot application, which can be easily deployed to Heroku

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Running Locally

### Postgres Database

If you're going to use a local database, ensure you have a local `.env` file that reads something like this:

```
JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/java_database_name
```

Detailed steps are described
here: [Heroku Postgres - Local Setup](https://devcenter.heroku.com/articles/heroku-postgresql#local-setup)

### Running the Application

Make sure you have Java and Maven installed. Also, install the [Heroku CLI](https://cli.heroku.com/).

```sh
$ mvn install
$ heroku local web
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```

## Documentation

For more information about using Java on Heroku, see these Dev Center articles:

- [Java on Heroku](https://devcenter.heroku.com/categories/java)
