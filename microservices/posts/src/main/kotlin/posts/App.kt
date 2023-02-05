package posts

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService

fun main( ) {

    val server: Server= ServerBuilder
        .forPort(4000)
        .addService(ProtoReflectionService.newInstance( ))
        .build( )

    println("🔥 server starting at port 4000")
    server.start( )
    server.awaitTermination( )
}