-- neighborhood
INSERT INTO neighborhood (id, name, address, city)
VALUES (2, 'Santafe Pijao', 'Calle 182 #45-11', 'Bogota');

-- homes first neighborhood
INSERT INTO home (name, block, home_number, type, created_date, neighborhood_id, owner_id)
VALUES ('Casa Ejemplo 2', 2, 308, 'Casa', '2024-05-07 12:00:00', 1, 1);
INSERT INTO home (name, home_number, type, created_date, neighborhood_id, owner_id)
VALUES ('Casa Ejemplo 3', 308, 'Casa', '2024-05-07 12:00:00', 1, 1);

-- homes new neighborhood
INSERT INTO home (name, block, home_number, type, created_date, neighborhood_id, owner_id)
VALUES ('Casa Ejemplo 4', 2, 308, 'Casa', '2024-05-07 12:00:00', 2, 1);
INSERT INTO home (name, home_number, type, created_date, neighborhood_id, owner_id)
VALUES ('Casa Ejemplo 5', 308, 'Casa', '2024-05-07 12:00:00', 2, 1);

-- guard
INSERT INTO user_app (id, document_id, document_type, first_name, last_name, email, password, created_date, update_date, active)
VALUES (5, '1234567812', 'CC', 'Guardia', 'Dos', 'guard@gmail.com', 'v73xi9w5t5GIrYFFyJvNWQ==', '2024-05-31 10:00:00', null, true);

INSERT INTO user_rol (user_id, rol, active, neighborhood_id, home_id, created_date)
VALUES (5, 'GUARD', true, 2, NULL, '2024-05-31 12:00:00');
