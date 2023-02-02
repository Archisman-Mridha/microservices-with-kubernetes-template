#![allow(non_snake_case)]

use std::thread;
use protobuf::Message;
use tonic::{transport::Server, Request, Response, Status};

mod generated;
use generated::proto::{create_profile::CreateProfileRequest, profile::{profile_server::{ProfileServer, Profile}, DeleteProfileRequest, DeleteProfileResponse}};

fn consumeFromRabbitMQ( ) -> amiquip::Result<( )> {
    use amiquip::{Connection, QueueDeclareOptions, ConsumerOptions, ConsumerMessage};

    let mut connection= Connection::insecure_open(
        "amqp://user:password@localhost:5672")?;
    println!("🔥 connected to rabbitMQ");

    let channel= connection.open_channel(None)?; // channel id is automatically generated by the library

    thread::spawn(
        move | | -> amiquip::Result<( )> {
            let queue= channel.queue_declare("profile", QueueDeclareOptions::default( ))?;

            let consumer= queue.consume(ConsumerOptions::default( ))?;

            for message in consumer.receiver( ).iter( ) {
                match message {

                    ConsumerMessage::Delivery(message) => {
                        let messageBody= message.body;

                        let mut createProfileRequest= CreateProfileRequest::new( );

                        if createProfileRequest.merge_from_bytes(&messageBody).is_ok( ) {

                            println!("received message from rabbitMQ !");
                            todo!( )

                        } else {
                            println!("unknown type of message received from rabbitMQ");
                        }
                    }

                    _ => println!("unknown type of message received from rabbitMQ")
                }
            }

            return Ok(( ));
        }
    );

    return Ok(( ));
}

#[derive(Default)]
struct ImplementedProfileService { }

#[tonic::async_trait]
impl Profile for ImplementedProfileService {
    async fn delete_profile(&self, request: Request<DeleteProfileRequest>) -> Result<Response<DeleteProfileResponse>, Status> {

        todo!( )
    }
}

#[tokio::main]
async fn main( ) -> Result<( ), Box<dyn std::error::Error>> {

    //* connect to rabbitMQ and start consuming messages in a separate thread
    consumeFromRabbitMQ( ).unwrap_or_else(
        |error| {
            println!("{}", error);

            panic!("💀 error connecting to rabbitMQ");
        }
    );

    //* starting the gRPC server

    let implementedProfileService= ImplementedProfileService::default( );
    let socketAddress= "0.0.0.0:4000".parse( ).unwrap( );

    println!("🔥 starting gRPC server");

    Server::builder( )
        .add_service(ProfileServer::new(implementedProfileService))
        .serve(socketAddress)
        .await?;

    return Ok(( ));
}