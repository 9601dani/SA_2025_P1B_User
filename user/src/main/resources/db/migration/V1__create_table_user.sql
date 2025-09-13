-- Tabla users
CREATE TABLE users (
    id CHAR(36) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    module VARCHAR(255),
    first_time BOOLEAN NOT NULL,
    manager BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL,
    location_id CHAR(36)
);

-- Tabla user_information
CREATE TABLE user_information (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    last_name VARCHAR(255),
    salary_per_week DECIMAL(15,2),
    created_at DATETIME,
    birthdate DATE,
    fk_user CHAR(36) UNIQUE,
    CONSTRAINT fk_user_information_user FOREIGN KEY (fk_user)
        REFERENCES users (id)
        ON DELETE CASCADE
);
