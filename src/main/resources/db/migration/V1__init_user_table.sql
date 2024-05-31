CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    email      VARCHAR(255) NULL,
    username   VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    phone      VARCHAR(255) NULL,
    firstname  VARCHAR(255) NULL,
    lastname   VARCHAR(255) NULL,
    city       VARCHAR(255) NULL,
    street     VARCHAR(255) NULL,
    number     INT NOT NULL,
    zipcode    VARCHAR(255) NULL,
    lat        VARCHAR(255) NULL,
    lon        VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);