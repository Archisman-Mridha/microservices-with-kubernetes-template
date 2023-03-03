CREATE TABLE authentication.users (
    id SERIAL PRIMARY KEY,

    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);