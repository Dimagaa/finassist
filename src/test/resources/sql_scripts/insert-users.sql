INSERT INTO permissions (id, name)
VALUES (1, 'TEST');
ALTER SEQUENCE permissions_id_seq RESTART WITH 2;

INSERT INTO users (id, email, password, first_name, last_name)
VALUES ('e3b3d183-62a1-4ffe-9bfb-1c62cbab3dd3', 'bob@test.test','$2a$10$GvHjWQqRzyrhpNvq5fX/ZuEa6UVyCLz1sGV/QITNvvJ1u9Cu1RYKm', 'Bob', 'Alison'),
       ('3095e25c-479a-4e2a-b75e-0be2ff7c98a0', 'alice@test.test','$2a$10$GvHjWQqRzyrhpNvq5fX/ZuEa6UVyCLz1sGV/QITNvvJ1u9Cu1RYKm', 'Alice', 'Bobson');

INSERT INTO users_permissions(user_id, permission_id)
VALUES ('e3b3d183-62a1-4ffe-9bfb-1c62cbab3dd3', 1),
       ('3095e25c-479a-4e2a-b75e-0be2ff7c98a0', 1);
