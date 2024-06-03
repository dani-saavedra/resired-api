CREATE TABLE notification
(
    id           INTEGER AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(100) NOT NULL,
    message      VARCHAR(255) NOT NULL,
    created_date TIMESTAMP    NOT NULL
);

CREATE TABLE notification_user
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id         INTEGER              NOT NULL,
    notification_id INTEGER              NOT NULL,
    viewed          TINYINT(1) DEFAULT 0 NOT NULL,
    viewed_at       TIMESTAMP            NULL,
    FOREIGN KEY (user_id) REFERENCES user_app (id),
    FOREIGN KEY (notification_id) REFERENCES notification (id)
);
