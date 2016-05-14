# --- !Ups

CREATE TABLE USER (
    id INT NOT NULL,
    email varchar NOT NULL,
    password varchar NOT NULL,
    fullname varchar NOT NULL,
    isAdmin boolean NOT NULL,
    PRIMARY KEY(id)
);

# --- !Downs

DROP TABLE USER;
