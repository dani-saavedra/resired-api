INSERT INTO neighborhood (id, name, address, city)
VALUES (1, 'Acqua Residencial', 'Calle 17 14a-25', 'chia');


INSERT INTO user_app (document_id, document_type, first_name, last_name, email, password, created_date, update_date, active)
VALUES ('1151943929', 'CC', 'John', 'Doe', 'john.doe@gmail.com', 'v73xi9w5t5GIrYFFyJvNWQ==', '2024-05-06 10:00:00', null, true);

INSERT INTO user_app (document_id, document_type, first_name, last_name, email, password, created_date, update_date, active)
VALUES ('12345', 'CC', 'Guard', 'Ian', 'guard.ian@gmail.com', 'v73xi9w5t5GIrYFFyJvNWQ==', '2024-05-06 10:00:00', null, true);


INSERT INTO home (name, type, created_date, neighborhood_id, owner_id)
VALUES ('Casa Ejemplo', 'Casa', '2024-05-07 12:00:00', 1, 1);

INSERT INTO user_app (document_id, document_type, first_name, last_name, email, password, created_date, update_date, active)
VALUES ('12345678', 'CC', 'Fulanito', 'De Tal', 'fulanito@gmail.com', 'v73xi9w5t5GIrYFFyJvNWQ==', '2024-05-06 10:00:00', null, false);


INSERT INTO user_rol (user_id, rol, active, neighborhood_id, home_id, created_date)
VALUES (1, 'RESIDENT', true, 1, 1, '2024-05-11 12:00:00');

INSERT INTO user_rol (user_id, rol, active, neighborhood_id, created_date)
VALUES (2, 'GUARD', true, 1, '2024-05-11 12:15:00');

INSERT INTO news (title, content, category, neighborhood_id, image, created_date)
VALUES ('Nuevo récord de goles en el campeonato local',
        'El equipo local ha marcado un nuevo récord de goles en la historia del campeonato, superando las expectativas de los aficionados.',
        'EVENTS',
        1,
        'https://cucutadeportivofc.com/wp-content/uploads/2022/08/cropped-Cucuta_Deportivo_2022.png',
        NOW());

INSERT INTO news (title, content, category, neighborhood_id, image, created_date)
VALUES ('Próximo festival de música en el parque central',
        '¡Prepárate para el festival de música más grande del año en nuestro vecindario! Bandas locales e invitadas te esperan para una jornada llena de diversión y entretenimiento.',
        'EVENTS',
        1,
        'https://concepto.de/wp-content/uploads/2020/03/musica-e1584123209397.jpg',
        NOW());

INSERT INTO package (guard_id, home_id, receiver, tracking_number, status, package_transporter, description, received_date)
VALUES (2, 1, 'John Doe', 'ABC123', 'TO_COLLECT', 'Servientrega', 'Electronics', '2024-05-14 12:00:00');

INSERT INTO package (guard_id, home_id, receiver, tracking_number, status, package_transporter, description, received_date)
VALUES (2, 1, 'Daniel', '321ABC', 'TO_COLLECT', 'Interapidisimo', 'caja', '2024-05-14 14:00:00');
