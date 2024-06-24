DELETE FROM users_permissions;
DELETE FROM users;
DELETE FROM permissions;
ALTER SEQUENCE permissions_id_seq RESTART WITH 1;
