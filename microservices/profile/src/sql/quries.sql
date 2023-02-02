INSERT INTO profiles
    (name, email)
    VALUES (@name, @email)
        RETURNING id;

DELETE FROM profiles
    WHERE email=@email;