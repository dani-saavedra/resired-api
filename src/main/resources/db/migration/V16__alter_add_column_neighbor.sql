ALTER TABLE neighborhood
    ADD COLUMN document VARCHAR(75),
    ADD COLUMN update_date DATE,
    ADD COLUMN residence_type VARCHAR(50),
    ADD COLUMN grouping_type VARCHAR(50),
    ADD COLUMN preferred_name VARCHAR(50),
    ADD COLUMN category VARCHAR(50),
    ADD COLUMN community_type VARCHAR(100),
    ADD COLUMN towers INTEGER default 0,
    ADD COLUMN homes INTEGER default 0;
