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

    # installing goLang plugin
    go install google.golang.org/protobuf/cmd/protoc-gen-go@v1.28
    go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@v1.2

    set -U fish_user_paths $HOME/go/bin $fish_user_paths
    set -U fish_user_paths /usr/local/go/bin $fish_user_paths
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

- *`sqlc`* -
    ```bash
    # gcc is required to use sqlc
    sudo apt install gcc -y

    go install github.com/kyleconroy/sqlc/cmd/sqlc@latest
    ```

## Conventions

- In RabbitMQ, if name of a queue is **profile**, that means, the messages in the queue will be consumed by the profile microservice.

- Docker build commands are executed from the root of the project.