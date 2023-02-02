use std::{path::PathBuf, env};

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
        .file_descriptor_set_path(
            PathBuf::from(env::var("OUT_DIR").unwrap( )).join("profile_descriptor.bin")
        )
        .out_dir("./src/generated/proto")
        .compile(
            &["./src/proto/main.proto"],
            &["./src/proto/"]
        ).unwrap( );
}