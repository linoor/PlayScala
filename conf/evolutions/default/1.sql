# --- !Ups
create table PRODUCT ("NAME" VARCHAR NOT NULL PRIMARY KEY, "DESCRIPTION" TEXT NOT NULL, "PRICE" INT NOT NULL);
# --- !Downs
DROP TABLE PRODUCT;