ALTER TABLE visit
    CHANGE is_car_active checkout   TINYINT(1),
    ADD COLUMN checkout_date        TIMESTAMP;

