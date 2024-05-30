ALTER TABLE pass_reset_token
    ADD COLUMN invalid tinyint default 0;
