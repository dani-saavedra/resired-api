ALTER TABLE notification
    ADD COLUMN neighborhood_id INTEGER;

ALTER TABLE notification
    ADD FOREIGN KEY (neighborhood_id) REFERENCES neighborhood (id);
