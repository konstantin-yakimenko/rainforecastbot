package com.jakimenko.rainforecastbot.server

//import io.netty.channel.epoll.EpollEventLoopGroup
//import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy


@Component
class HttpServer(
    val httpChannelInitializer: HttpChannelInitializer,
    @Value("\${server.port}") val port: Int
): CommandLineRunner {
    companion object: KLogging()

    private val bossGroup = NioEventLoopGroup(1)
    private val workerGroup = NioEventLoopGroup(Runtime.getRuntime().availableProcessors() / 2 + 1)

    @Throws(Exception::class)
    override fun run(vararg args: String?) {
        try {
            val sb = ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(httpChannelInitializer)
                .option(ChannelOption.SO_BACKLOG, 512)
                // .option(ChannelOption.SO_REUSEADDR, true)
                // .childOption(ChannelOption.SO_SNDBUF, 128 * 1024)
                // .childOption(ChannelOption.SO_RCVBUF, 128 * 1024)
                // .childOption(ChannelOption.TCP_NODELAY, true)
                // .childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 9000)
            val future = sb.bind(port)
            logger.info { "Server is started on ${port} port." }
            future.sync() // locking the thread until groups are going on
            future.channel().closeFuture().sync()
        } catch (e: InterruptedException) {
            logger.error { "Something went wrong: ${e}" }
        } finally {
            shutdown()
        }
    }

    @PreDestroy
    fun shutdown() {
        logger.info { "Server is shutting down." }
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }
}
