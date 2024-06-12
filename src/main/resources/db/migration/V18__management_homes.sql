CREATE TABLE block
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255),
    type            VARCHAR(50),
    neighborhood_id INTEGER NOT NULL,
    FOREIGN KEY (neighborhood_id) REFERENCES neighborhood (id)
);
ALTER TABLE home DROP FOREIGN KEY home_ibfk_1;
ALTER TABLE home DROP COLUMN neighborhood_id,  drop column name;
ALTER TABLE home DROP COLUMN type;
ALTER TABLE home DROP COLUMN block;
ALTER TABLE home ADD COLUMN block INTEGER;
ALTER TABLE home
    ADD CONSTRAINT home_block FOREIGN KEY (block)
        REFERENCES block (id);

ALTER TABLE home MODIFY owner_id INTEGER NULL;




