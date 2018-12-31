DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS history;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE restaurants
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  enabled         BOOL DEFAULT FALSE ,
  voters           INTEGER                 NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE dishes
(
  id                INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  restaurant_id     INTEGER NOT NULL,
  description       VARCHAR NOT NULL,
  price             INTEGER,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
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

CREATE TABLE history
(
  event_date       TIMESTAMP DEFAULT now() PRIMARY KEY  NOT NULL,
  restaurant_name  VARCHAR                              NOT NULL

);
CREATE UNIQUE INDEX history_unique_date_idx ON history (event_date);
