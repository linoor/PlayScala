# --- !Ups
create table ITEM ("NAME" VARCHAR NOT NULL PRIMARY KEY, "DESCRIPTION" TEXT NOT NULL, "PRICE" INT NOT NULL);
# --- !Downs
DROP TABLE ITEM;