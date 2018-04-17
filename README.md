# Lunch Finder Server

Provides API endpoints with Lunch Finder data for client apps
- [Web](https://github.com/iumehara/lunchFinderWeb)
- [iOS](https://github.com/iumehara/lunchFinderIOS)
- [Android](https://github.com/iumehara/lunchFinderAndroid)

### Dependencies
- Java 8
- Postgres

### Getting Started
- Setup local Postgres DB
    - `createdb lunch_finder_dev`
    - `psql -d lunch_finder_dev -f ./src/main/resources/db/migrations.sql`
    - `psql -d lunch_finder_dev -f ./src/main/resources/db/seed.sql`
    - `createdb lunch_finder_test`
    - `psql -d lunch_finder_test -f ./src/main/resources/db/migrations.sql`
    
- Setup .env file
    - `cp .envSample .env` then set the env vars for local environment
        - `export SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5432/lunch_finder_dev'`
        - `export CLIENT_URL='http://localhost:8000'`

- Start the app
    - `source .env`
    - `./gradlew build`
    - `./gradlew bootrun`
    - api data available at `http://localhost:8080/{resource}`
