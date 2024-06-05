ALTER TABLE package
    RENAME COLUMN guard_id TO guard_received_id;

ALTER TABLE package
    ADD COLUMN guard_delivered_id INTEGER,
    ADD COLUMN receiver_last_four_digits VARCHAR(4);

ALTER TABLE package
    ADD FOREIGN KEY (guard_received_id) REFERENCES user_app (id);
