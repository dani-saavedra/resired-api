CREATE TABLE pass_reset_token
(
    id          INTEGER AUTO_INCREMENT PRIMARY KEY,
    token       VARCHAR(5000) NOT NULL,
    user_id     INTEGER NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);
