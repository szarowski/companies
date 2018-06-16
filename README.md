# Companies API

The Companies API.

## Endpoints
```
Create Company:                     POST   /v1/companies
Update Company:                     PUT    /v1/companies/<id>
Get Company:                        GET    /v1/companies/<id>
List all Companies:                 GET    /v1/companies
Delete Company:                     DELETE /v1/companies/<id>
Add Beneficiary Owner to Company:   PUT    /v1/companies/<id>/add_beneficial_owner
```

### Examples

#### Create company:
```
curl -X POST http://localhost:8080/v1/companies -H "Content-Type: application/json" -d '{"company_id":"4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11","name":"Envisionworld Ltd.","address":"1 Canada square, Canary Wharf","city":"London","country":"United Kingdom","email":"rszarowski@envisionworld.co","phone":"+420123456789","beneficial_owners":["Roman Szarowski"]}'
curl -X POST https://ancient-reef-15294.herokuapp.com/v1/companies -H "Content-Type: application/json" -d '{"company_id":"4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11","name":"Envisionworld Ltd.","address":"1 Canada square, Canary Wharf","city":"London","country":"United Kingdom","email":"rszarowski@envisionworld.co","phone":"+420123456789","beneficial_owners":["Roman Szarowski"]}'
```

#### Get company:
```
curl -X GET http://localhost:8080/v1/companies/4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11
curl -X GET https://ancient-reef-15294.herokuapp.com/v1/companies/4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11
```

#### Update company with different beneficial owner:
```
curl -X PUT http://localhost:8080/v1/companies/4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11 -H "Content-Type: application/json" -d '{"company_id":"4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11","name":"Envisionworld Ltd.","address":"1 Canada square, Canary Wharf","city":"London","country":"United Kingdom","email":"rszarowski@envisionworld.co","phone":"+420123456789","beneficial_owners":["Bashir Khairy"]}'
curl -X PUT https://ancient-reef-15294.herokuapp.com/v1/companies/4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11 -H "Content-Type: application/json" -d '{"company_id":"4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11","name":"Envisionworld Ltd.","address":"1 Canada square, Canary Wharf","city":"London","country":"United Kingdom","email":"rszarowski@envisionworld.co","phone":"+420123456789","beneficial_owners":["Bashir Khairy"]}'
```

#### List all companies:
```
curl -X GET http://localhost:8080/v1/companies
curl -X GET https://ancient-reef-15294.herokuapp.com/v1/companies
```

#### Delete company:
```
curl -X DELETE http://localhost:8080/v1/companies/4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11
curl -X DELETE https://ancient-reef-15294.herokuapp.com/v1/companies/4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11
```

#### Add Beneficiary Owner to Company
```
curl -X PUT http://localhost:8080/v1/companies/4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11/add_beneficial_owner -H "Content-Type: application/json" -d '{"owner_name": "The guy who should never be here"}'
curl -X PUT https://ancient-reef-15294.herokuapp.com/v1/companies/4ee3a8d8-ca7b-4290-a52c-dd5b6165ec11/add_beneficial_owner -H "Content-Type: application/json" -d '{"owner_name": "The guy who should never be here"}'
```

## Requirements

Installed Java 8+.
Installed Gradle. 
Installed PostgreSQL database.
Compile with `-parameters` flag. Gradle build already does that.

## Create DB of Companiess in PostgreSQL

If you installed PostgreSQL from APK use the following commands to set up a database.
Note that the user for companies database requires `companies` password and the user for companies_test database requires `companies_test`.

```
sudo -u postgres createuser -P companies
sudo -u postgres createdb -O companies companies
sudo -u postgres createuser -P companies_test
sudo -u postgres createdb -O companies_test companies_test
```

### Database schema

This is the content of the 1st Flyway migration.
```
CREATE TABLE IF NOT EXISTS companies (
    company_id                              UUID            NOT NULL,
    name                                    VARCHAR(255)    NOT NULL,
    address                                 VARCHAR(255)    NOT NULL,
    city                                    VARCHAR(255)    NOT NULL,
    country                                 VARCHAR(255)    NOT NULL,
    email                                   VARCHAR(255),
    phone                                   VARCHAR(255),

    PRIMARY KEY (company_id)
);

CREATE TABLE IF NOT EXISTS beneficial_owners (
    company_id                              UUID            NOT NULL,
    owner_name                              VARCHAR(255)    NOT NULL,

    FOREIGN KEY (company_id) REFERENCES companies(company_id) ON DELETE CASCADE
);
```

## Running the service

The project is build in Gradle, use:
```
gradle clean build
gradle bootRun
```