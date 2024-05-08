INSERT INTO Neighborhood (id, name, address, city)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Acqua Residencial', 'Calle 17 14a-25', 'chia');

INSERT INTO Home (name, home_type, created_date, neighborhood_id)
VALUES ('Casa Ejemplo', 'Casa', '2024-05-07 12:00:00', '550e8400-e29b-41d4-a716-446655440000');

INSERT INTO user_app (id, first_name, last_name, email, password, created_date, update_date, active)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'John', 'Doe', 'john.doe@example.com', 'v73xi9w5t5GIrYFFyJvNWQ==', '2024-05-06 10:00:00', '2024-05-06 10:00:00', true);
