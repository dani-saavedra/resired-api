CREATE TABLE device
(
    id VARCHAR(255) PRIMARY KEY,
    user_id INTEGER NOT NULL,
    allow_notifications TINYINT(1),
    FOREIGN KEY (user_id) REFERENCES user_app (id)
);
