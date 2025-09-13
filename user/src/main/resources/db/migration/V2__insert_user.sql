-- Insert 5 usuarios tipo ADMIN
INSERT INTO users (id, username, email, password, module, first_time, manager, enabled, location_id)
VALUES
('44f7dc96-003a-4bb3-8c4d-dfbf04c42729', 'admin',  'admin@correo.com',  '$2b$10$9h10fhHolSfbnp4j0zv2V.mIWV/1N9PI5q8q5LwqItqkaYx2lxNFO', 'ADMIN', true, true, true, '123e4567-e89b-12d3-a456-426614174001'),
('44f7dc96-003a-4bb3-8c4d-dfbf04c42730', 'admin1', 'admin1@correo.com', '$2b$10$9h10fhHolSfbnp4j0zv2V.mIWV/1N9PI5q8q5LwqItqkaYx2lxNFO', 'ADMIN', true, true, true, '123e4567-e89b-12d3-a456-426614174002'),
('44f7dc96-003a-4bb3-8c4d-dfbf04c42731', 'admin2', 'admin2@correo.com', '$2b$10$9h10fhHolSfbnp4j0zv2V.mIWV/1N9PI5q8q5LwqItqkaYx2lxNFO', 'ADMIN', true, true, true, '123e4567-e89b-12d3-a456-426614174003'),
('44f7dc96-003a-4bb3-8c4d-dfbf04c42732', 'admin3', 'admin3@correo.com', '$2b$10$9h10fhHolSfbnp4j0zv2V.mIWV/1N9PI5q8q5LwqItqkaYx2lxNFO', 'ADMIN', true, true, true, '123e4567-e89b-12d3-a456-426614174004'),
('44f7dc96-003a-4bb3-8c4d-dfbf04c42733', 'admin4', 'admin4@correo.com', '$2b$10$9h10fhHolSfbnp4j0zv2V.mIWV/1N9PI5q8q5LwqItqkaYx2lxNFO', 'ADMIN', true, true, true, '123e4567-e89b-12d3-a456-426614174005');

-- Insert corresponding user_information con nombres distintos
INSERT INTO user_information (id, name, last_name, salary_per_week, created_at, birthdate, FK_User)
VALUES
('55a8b7de-12cd-4f68-9012-b02f11a12d40', 'Daniel',   'Morales', 750.00, '2025-08-29 12:00:00', '1995-05-15', '44f7dc96-003a-4bb3-8c4d-dfbf04c42729'),
('55a8b7de-12cd-4f68-9012-b02f11a12d41', 'María',    'Gómez',   750.00, '2025-08-29 12:00:00', '1992-11-02', '44f7dc96-003a-4bb3-8c4d-dfbf04c42730'),
('55a8b7de-12cd-4f68-9012-b02f11a12d42', 'Carlos',   'Pérez',   750.00, '2025-08-29 12:00:00', '1988-07-19', '44f7dc96-003a-4bb3-8c4d-dfbf04c42731'),
('55a8b7de-12cd-4f68-9012-b02f11a12d43', 'Laura',    'Ramírez', 750.00, '2025-08-29 12:00:00', '1990-03-12', '44f7dc96-003a-4bb3-8c4d-dfbf04c42732'),
('55a8b7de-12cd-4f68-9012-b02f11a12d44', 'Andrés',   'Sánchez', 750.00, '2025-08-29 12:00:00', '1993-09-25', '44f7dc96-003a-4bb3-8c4d-dfbf04c42733');
