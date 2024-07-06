ALTER TABLE `role`
    ADD is_deleted BIT(1) NULL;

ALTER TABLE `role`
    MODIFY is_deleted BIT (1) NOT NULL;

ALTER TABLE token
    ADD is_deleted BIT(1) NULL;

ALTER TABLE token
    MODIFY is_deleted BIT (1) NOT NULL;

ALTER TABLE user
    ADD is_deleted BIT(1) NULL;

ALTER TABLE user
    MODIFY is_deleted BIT (1) NOT NULL;