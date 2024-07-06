CREATE TABLE `role`
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    name       VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE token
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    value      VARCHAR(255) NULL,
    user_id    BIGINT NULL,
    expiry_at  datetime NULL,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL
);

ALTER TABLE user
    ADD hashed_password VARCHAR(255) NULL;

ALTER TABLE user
    ADD is_email_verified BIT(1) NULL;

ALTER TABLE user
    ADD name VARCHAR(255) NULL;

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user
DROP
COLUMN city;

ALTER TABLE user
DROP
COLUMN firstname;

ALTER TABLE user
DROP
COLUMN lastname;

ALTER TABLE user
DROP
COLUMN lat;

ALTER TABLE user
DROP
COLUMN lon;

ALTER TABLE user
DROP
COLUMN number;

ALTER TABLE user
DROP
COLUMN password;

ALTER TABLE user
DROP
COLUMN phone;

ALTER TABLE user
DROP
COLUMN street;

ALTER TABLE user
DROP
COLUMN zipcode;