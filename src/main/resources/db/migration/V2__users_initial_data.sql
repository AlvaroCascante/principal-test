
INSERT INTO persons (name, lastname, id_number, birthday, email, gender) VALUES ('System', 'System', '6-10', '2024-01-01', 'System@system.com', 'NEUTRO');

INSERT INTO users (created_at, id_person, nickname, password, user_status, last_login, username, roles) VALUES (now(), 1, 'SYSADMIN', '$2a$10$U5FIgKWP9AMycrt0h4pAGuY05mxwqjiV3Bf5ikmWGB0RAa6XHpNBi', 'ACTIVE', now(), 'SYSADMIN', 'ADMIN');
INSERT INTO users (created_at, id_person, nickname, password, user_status, last_login, username, roles) VALUES (now(), 1, 'SYSTEACHER', '$2a$10$U5FIgKWP9AMycrt0h4pAGuY05mxwqjiV3Bf5ikmWGB0RAa6XHpNBi', 'ACTIVE', now(), 'SYSTEACHER', 'TEACHER');
INSERT INTO users (created_at, id_person, nickname, password, user_status, last_login, username, roles) VALUES (now(), 1, 'SYSADMIN', '$2a$10$U5FIgKWP9AMycrt0h4pAGuY05mxwqjiV3Bf5ikmWGB0RAa6XHpNBi', 'ACTIVE', now(), 'SYSPARENT', 'CONTACT');
INSERT INTO users (created_at, id_person, nickname, password, user_status, last_login, username, roles) VALUES (now(), 1, 'SYSDATAADMIN', '$2a$10$U5FIgKWP9AMycrt0h4pAGuY05mxwqjiV3Bf5ikmWGB0RAa6XHpNBi', 'ACTIVE', now(), 'SYSDATAADMIN', 'DATA');