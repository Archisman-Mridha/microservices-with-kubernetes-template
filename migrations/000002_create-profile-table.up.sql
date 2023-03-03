CREATE TABLE profile.profiles (
    id SERIAL PRIMARY KEY,

    username VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);