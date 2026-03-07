CREATE TABLE IF NOT EXISTS role_has_page(
    id INT PRIMARY KEY,
    FK_role INT NOT NULL,
    FK_page INT NOT NULL,
    can_create TINYINT(1) NOT NULL DEFAULT 1,
    can_edit TINYINT(1) NOT NULL DEFAULT 1,
    can_delete TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT fk_role_page FOREIGN KEY (FK_role) REFERENCES role(id),
    CONSTRAINT fk_page_role FOREIGN KEY (FK_page) REFERENCES page(id)
);