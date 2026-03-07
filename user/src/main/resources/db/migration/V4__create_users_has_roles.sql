CREATE TABLE IF NOT EXISTS user_has_role(
    id INT PRIMARY KEY ,
    user_id CHAR(36) NOT NULL,
    role_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_uhr_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_uhr_role FOREIGN KEY (role_id) REFERENCES role(id)
);