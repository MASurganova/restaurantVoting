DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO restaurants (name, enabled, voters) VALUES
  ('My', true, 1),
  ('Other', false, 0);

INSERT INTO dishes (restaurant_id, description, price) VALUES
  (100000, 'Чечевичный суп', 150),
  (100000, 'Салат с семгой', 250),
  (100001, 'Борщ', 250),
  (100001, 'Оливье', 150),
  (100001, 'Овощное рагу', 200);

INSERT INTO users (name, email, password, restaurant_id) VALUES
  ('User', 'user@yandex.ru', 'password', 100000),
  ('Admin', 'admin@gmail.com', 'admin', null);

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100007),
  ('ROLE_ADMIN', 100008);