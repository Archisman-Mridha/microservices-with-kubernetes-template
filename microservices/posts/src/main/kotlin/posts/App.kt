package posts

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import posts.generated.proto.CreatePostRequest
import posts.generated.proto.CreatePostResponse
import posts.generated.proto.PostsGrpcKt

class PostsService: PostsGrpcKt.PostsCoroutineImplBase( ) {

    override suspend fun createPost(request: CreatePostRequest): CreatePostResponse {
        return super.createPost(request)
    }
}

fun main( ) {

    //* connect to cockroach database

    //* create gRPC server

    val server: Server= ServerBuilder
        .forPort(4000)
        .addService(ProtoReflectionService.newInstance( ))
        .addService(PostsService( ))
        .build( )

    println("🔥 server starting at port 4000")
    server.start( )
    server.awaitTermination( )
}