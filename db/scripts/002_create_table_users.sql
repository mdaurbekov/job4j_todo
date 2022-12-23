CREATE TABLE users (
                       id serial primary key,
                       name varchar,
                       login varchar not null UNIQUE ,
                       password varchar
);