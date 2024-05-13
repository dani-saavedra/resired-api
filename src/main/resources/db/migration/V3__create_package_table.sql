CREATE TABLE package
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    receiver       VARCHAR(255),
    tracking_number VARCHAR(255),
    home_id         BIGINT,
    reception_date  TIMESTAMP,
    guard_id        VARCHAR(255),
    status         VARCHAR(255),
    update_date     TIMESTAMP,
    description    VARCHAR(255),
    neighborhood_id BIGINT
);
