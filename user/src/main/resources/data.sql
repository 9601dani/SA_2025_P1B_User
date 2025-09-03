INSERT INTO users (id, username, email, password, module, first_time, manager, enabled)
VALUES ('44f7dc96-003a-4bb3-8c4d-dfbf04c42729',
        'admin',
        'admin@correo.com',
        '$2b$10$9h10fhHolSfbnp4j0zv2V.mIWV/1N9PI5q8q5LwqItqkaYx2lxNFO',
        'ADMIN',
        true,
        true,
        true);

INSERT INTO users (id, username, email, password, module, first_time, manager, enabled)
VALUES ('44f7dc96-003a-4bb3-8c4d-dfbf04c42720',
        'admin1',
        'admin1@correo.com',
        '$2b$10$9h10fhHolSfbnp4j0zv2V.mIWV/1N9PI5q8q5LwqItqkaYx2lxNFO',
        'RESTAURANT',
        true,
        true,
        true);

INSERT INTO users (id, username, email, password, module, first_time, manager, enabled)
VALUES ('44f7dc96-003a-4bb3-8c4d-dfbf04c42721',
        'admin2',
        'admin2@correo.com',
        '$2b$10$9h10fhHolSfbnp4j0zv2V.mIWV/1N9PI5q8q5LwqItqkaYx2lxNFO',
        'HOTEL',
        true,
        true,
        true);

INSERT INTO user_information (id, name, last_name, salary_per_week, created_at, birthdate, FK_User)
VALUES ('55a8b7de-12cd-4f68-9012-b02f11a12d33',
        'Daniel',
        'Morales',
        750.00,
        '2025-08-29 12:00:00',
        '1995-05-15',
        '44f7dc96-003a-4bb3-8c4d-dfbf04c42729');

INSERT INTO user_information (id, name, last_name, salary_per_week, created_at, birthdate, FK_User)
VALUES ('55a8b7de-12cd-4f68-9012-b02f11a12d34',
        'Daniel',
        'Morales',
        750.00,
        '2025-08-29 12:00:00',
        '1995-05-15',
        '44f7dc96-003a-4bb3-8c4d-dfbf04c42720');

INSERT INTO user_information (id, name, last_name, salary_per_week, created_at, birthdate, FK_User)
VALUES ('55a8b7de-12cd-4f68-9012-b02f11a12d35',
        'Daniel',
        'Morales',
        750.00,
        '2025-08-29 12:00:00',
        '1995-05-15',
        '44f7dc96-003a-4bb3-8c4d-dfbf04c42721');