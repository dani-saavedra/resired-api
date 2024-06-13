ALTER TABLE visit
    ADD COLUMN scanned_by INTEGER NOT NULL,
    ADD FOREIGN KEY (scanned_by) REFERENCES user_app (id);
