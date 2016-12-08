package net.galvin.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Administrator on 2016/12/8.
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        /**
         * EventLoopGroup 是一个多线程的处理I/O操作的时间循环。对于不同种类的传输，netty提供了多种EventLoopGroup.
         * bossGroup主要负责连接的接入，接受连接注册到workerGroup。
         * 有多少个线程会被使用，怎么映射到channel，都依赖EventLoopGroup的实现。甚至可以通过构造器来实现。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /**
             * ServerBootstrap可以帮助我们来这只一个服务器。
             * 你也可以直接使用Channel来设置服务器，这非常的多余。大多数情况下我们没有必要这么做。
             */
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    /**=
                     * 当接收到一个新的通道，才会指定一个handler
                     * ChannelInitializer是一个特殊的处理器，意图是帮助用户配置一个新的 Channel
                     * 为了实现网络应用，你很有可能要为一个新的 Channel 增加一些 handlers
                     * 当应用越来越复杂的时候，你很有可能增加更多的处理器并且抽取出匿名类。
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    /**
                     * 你可以为 Channel 的实现设置参数。
                     * 我们正在写 TCP/IP 服务，所以我们允许为 socket 设置选项，譬如 tcpNoDelay 和 keepAlive
                     * 具体的配置请参考官方 API
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new DiscardServer(port).run();
    }


}
