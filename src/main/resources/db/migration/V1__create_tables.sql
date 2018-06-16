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