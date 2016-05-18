# --- !Ups

CREATE TABLE CART_ENTRY (
  id INT NOT NULL,
  userId INT NOT NULL,
  itemName varchar NOT NULL,
  paid boolean NOT NULL,
  PRIMARY KEY(id)
);

# --- !Downs

DROP TABLE CART_ENTRY;
