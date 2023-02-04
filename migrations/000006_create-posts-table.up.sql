CREATE TABLE post.posts (
    id SERIAL PRIMARY KEY,

    creator INTEGER NOT NULL,
    description VARCHAR(250) NOT NULL
);