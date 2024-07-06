ALTER TABLE notification
    ADD COLUMN category_id INTEGER,
    ADD COLUMN level VARCHAR(20),
    DROP FOREIGN KEY notification_ibfk_1,
    DROP COLUMN neighborhood_id;

ALTER TABLE notification
    ADD FOREIGN KEY (category_id) REFERENCES notification_category (id);

ALTER TABLE notification_category
    ADD COLUMN default_message VARCHAR(255),
    ADD COLUMN default_title VARCHAR(100);
