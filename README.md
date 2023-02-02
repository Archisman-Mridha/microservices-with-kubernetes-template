# Microservices With Kubernetes Template

A template distributed microservices system backed by Kubernetes and AWS.

## Installations

- *`rust`* -
    ```bash
    curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh

    set -Ua fish_user_paths $HOME/.cargo/bin
    ```

- *`protoc`* -
    ```bash
    sudo apt install -y protobuf-compiler libprotobuf-dev
    ```

- *`migrate`* -
    ```bash
    curl -s https://packagecloud.io/install/repositories/golang-migrate/migrate/script.deb.sh | sudo bash
    sudo apt-get update

    sudo apt-get install migrate
    ```

- *`diesel cli`* -
    ```bash
    sudo apt install libpq-dev -y

    cargo install diesel_cli --no-default-features --features postgres && \
        cargo install diesel_cli_ext
    ```

## Conventions

- In RabbitMQ, if name of a queue is **profile**, that means, the messages in the queue will be consumed by the profile microservice.

- Docker build commands are executed from the root of the project.