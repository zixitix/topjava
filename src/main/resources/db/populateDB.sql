DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
INSERT INTO meals (description,date_time,calories, user_id) VALUES
('User meal first', '2016-12-22 20:10:25-07',2000, 100000),
('User meal second','2016-06-12 17:10:25-07',2000, 100000),
('User meal third','2016-06-27 15:10:25-07',1000, 100000),
('Admin meal first','2016-05-30 11:10:25-07',1500, 100001),
('Admin meal second','2016-04-02 12:10:25-07',800, 100001),
('Admin meal third','2016-01-22 22:10:25-07',1400, 100001);
