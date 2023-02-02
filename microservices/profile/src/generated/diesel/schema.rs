// @generated automatically by Diesel CLI.

diesel::table! {
    profiles (id) {
        id -> Int8,
        name -> Varchar,
        email -> Varchar,
    }
}
