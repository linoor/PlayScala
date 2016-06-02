# --- !Ups

CREATE TABLE `ORDER` (
  id INT NOT NULL,
  userId INT NOT NULL,
  itemName varchar NOT NULL,
  name varchar NOT NULL,
  address varchar NOT NULL,
  postcode varchar NOT NULL,
  comments varchar NOT NULL,
  PRIMARY KEY(id)
);

# --- !Downs

DROP TABLE `ORDER`;
