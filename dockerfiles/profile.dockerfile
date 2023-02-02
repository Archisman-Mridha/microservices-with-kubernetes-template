ARG CARGO_CHEF_IMAGE=lukemathwalker/cargo-chef:latest-rust-buster

#! dependency planning stage
FROM $CARGO_CHEF_IMAGE AS planner
WORKDIR /app

RUN mkdir src && touch ./src/main.rs && \
    echo "fn main( ) { }" > ./src/main.rs

COPY ./microservices/profile/Cargo.toml .
RUN cargo chef prepare --recipe-path recipe.json

#! dependency caching stage
FROM $CARGO_CHEF_IMAGE AS cacher
WORKDIR /app

COPY --from=planner /app/recipe.json recipe.json
RUN cargo chef cook --release --recipe-path recipe.json

#! building stage
FROM rust:1-slim-buster as builder
WORKDIR /app

COPY ./microservices/profile .
COPY --from=cacher /app/target target
COPY --from=cacher $CARGO_HOME $CARGO_HOME

RUN apt update && apt install -y protobuf-compiler

RUN cargo build --release --bin profile

#! packaging stage
FROM gcr.io/distroless/cc-debian11:latest as packager
WORKDIR /

COPY --from=builder /app/target/release/profile .

EXPOSE 4000
CMD ["/profile"]