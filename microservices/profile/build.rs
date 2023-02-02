extern crate protoc_rust;
extern crate tonic_build;

fn main( ) {

    // for rabbitMQ
    protoc_rust::Codegen::new( )
        .out_dir("./src/generated/proto")
        .inputs(&["./src/proto/create-profile.proto"])
        .run( )
        .expect("failed generating code for create-profile.proto");

    // for gRPC
    tonic_build::configure( )
        .build_client(false)
        .out_dir("./src/generated/proto")
        .compile(
            &["./src/proto/main.proto"],
            &["./src/proto/"]
        ).unwrap( );
}