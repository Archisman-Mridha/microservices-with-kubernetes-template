package posts

import io.grpc.Server
import io.grpc.ServerBuilder

fun main( ) {

    val server: Server= ServerBuilder
        .forPort(4000)
        .build( )

    println("🔥 server starting at port 4000")
    server.start( )
    server.awaitTermination( )
}