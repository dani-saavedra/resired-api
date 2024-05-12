INSERT INTO neighborhood (id, name, address, city)
VALUES (1, 'Acqua Residencial', 'Calle 17 14a-25', 'chia');


INSERT INTO user_app (document_id, document_type, first_name, last_name, email, password, created_date, update_date, active)
VALUES ('1151943929', 'CC', 'John', 'Doe', 'john.doe@gmail.com', 'v73xi9w5t5GIrYFFyJvNWQ==', '2024-05-06 10:00:00', null, true);

INSERT INTO home (name, home_type, created_date, neighborhood_id, owner_id)
VALUES ('Casa Ejemplo', 'Casa', '2024-05-07 12:00:00', 1, '1151943929');

INSERT INTO user_app (document_id, document_type, first_name, last_name, email, password, created_date, update_date, active)
VALUES ('12345678', 'CC', 'Fulanito', 'De Tal', 'fulanito@gmail.com', 'v73xi9w5t5GIrYFFyJvNWQ==', '2024-05-06 10:00:00', null, false);


INSERT INTO user_rol (user_document, rol, active, neighborhood_id, home_id, created_date)
VALUES ('1151943929', 'RESIDENT', true, 1, 1, '2024-05-11 12:00:00');

INSERT INTO user_rol (user_document, rol, active, neighborhood_id, home_id, created_date)
VALUES ('1151943929', 'RESIDENT', true, 1, 1, '2024-05-11 12:15:00');
