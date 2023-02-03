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

- *`java`* -
    ```bash
    wget https://download.oracle.com/java/19/latest/jdk-19_linux-x64_bin.deb
    sudo dpkg -i jdk-19_linux-x64_bin.deb
    rm jdk-19_linux-x64_bin.deb

    sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-19/bin/java 1
    java -version

    sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk-19/bin/javac 1
    javac -version

    sudo update-alternatives --config java

    set -Ua JAVA_HOME /usr/lib/jvm/jdk-19/bin/java

    # for dependency management
    sudo apt-get install -y gradle
    ```

- *`javafmt`* -
    ```bash
    wget https://github.com/google/google-java-format/releases/download/v1.15.0/google-java-format-1.15.0-all-deps.jar
    mv google-java-format-1.15.0-all-deps.jar javafmt.jar
    ```

## Conventions

- In RabbitMQ, if name of a queue is **profile**, that means, the messages in the queue will be consumed by the profile microservice.

- Docker build commands are executed from the root of the project.