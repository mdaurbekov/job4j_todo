CREATE TABLE priorities (
   id SERIAL PRIMARY KEY,
   name varchar NOT NULL UNIQUE ,
   position int
);
INSERT INTO priorities (name, position) VALUES ('urgently', 1);
INSERT INTO priorities (name, position) VALUES ('normal', 2);
