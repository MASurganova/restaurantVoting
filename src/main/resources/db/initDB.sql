DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS restaurant_seq;
DROP SEQUENCE IF EXISTS dish_seq;

CREATE SEQUENCE user_seq START 100000;
CREATE SEQUENCE restaurant_seq START 100000;
CREATE SEQUENCE dish_seq START 100000;

CREATE TABLE restaurants
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('restaurant_seq'),
  name             VARCHAR                 NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE dishes
(
  id                INTEGER PRIMARY KEY DEFAULT nextval('dish_seq'),
  restaurant_id     INTEGER NOT NULL,
  description       VARCHAR NOT NULL,
  price             INTEGER,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  restaurant_id     INTEGER,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
