ALTER TABLE visit
    ADD COLUMN plate_car_number VARCHAR(20),
    ADD COLUMN is_car_active TINYINT(1) DEFAULT 0 NOT NULL;

