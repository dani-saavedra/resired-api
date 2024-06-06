CREATE TABLE notification_category
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    neighborhood_id INTEGER      NOT NULL,
    active          tinyint DEFAULT 1,
    FOREIGN KEY (neighborhood_id) REFERENCES neighborhood (id)
);
