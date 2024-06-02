CREATE TABLE notification
(
    id      INTEGER AUTO_INCREMENT PRIMARY KEY,
    title   VARCHAR(100)         NOT NULL,
    message VARCHAR(255)         NOT NULL,
    viewed  TINYINT(1) DEFAULT 0 NOT NULL
);
