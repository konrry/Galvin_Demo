package net.galvin.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.Executors;

/**
 * ChannelInboundHandlerAdapter实现了ChannelInboundHandler，提供了多种事件处理方法。
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当一个链接建立准备通信时，方法 channelActive() 会被调用。
     * 我们写了一个 32位的整数代表方法中的当前时间。
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        /**
         * 为了发送一个消息，我们需要分配一个新的 buffer 来容纳消息。
         * 我们将会写一个32位的证书，所以我们需要的容量是4个字节。
         * Get the current ByteBufAllocator via ChannelHandlerContext.alloc() and allocate a new buffer.
         * 通过 ChannelHandlerContext.alloc() 可以获取 ByteBufAllocator，分配一个buffer。
         */
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        /**
         * 和平常一样，我们写了一个结构化的消息。

         But wait, where's the flip? Didn't we used to call java.nio.ByteBuffer.flip() before sending a message in NIO?
         ByteBuf does not have such a method because it has two pointers; one for read operations and the other for write operations.
         The writer index increases when you write something to a ByteBuf while the reader index does not change.
         The reader index and the writer index represents where the message starts and ends respectively.

         In contrast, NIO buffer does not provide a clean way to figure out where the message content starts and ends without calling the flip method.
         You will be in trouble when you forget to flip the buffer because nothing or incorrect data will be sent.
         Such an error does not happen in Netty because we have different pointer for different operation types.
         You will find it makes your life much easier as you get used to it -- a life without flipping out!
         Another point to note is that the ChannelHandlerContext.write() (and writeAndFlush()) method returns a ChannelFuture. A ChannelFuture represents an I/O operation which has not yet occurred. It means, any requested operation might not have been performed yet because all operations are asynchronous in Netty. For example, the following code might close the connection even before a message is sent:

         Channel ch = ...;
         ch.writeAndFlush(message);
         ch.close();
         Therefore, you need to call the close() method after the ChannelFuture is complete, which was returned by the write() method, and it notifies its listeners when the write operation has been done. Please note that, close() also might not close the connection immediately, and it returns a ChannelFuture.
         */
        final ChannelFuture f = ctx.writeAndFlush(time);

        /**
         * How do we get notified when a write request is finished then? This is as simple as adding a ChannelFutureListener to the returned ChannelFuture. Here, we created a new anonymous ChannelFutureListener which closes the Channel when the operation is done.
         */
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });
    }

    /**
     * 这里我们覆盖了时间处理的方法：channelRead()
     * 当接收到客户端的消息的时候，这个方法会被调用。
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            /**
             * ByteBuf是有一个引用计数的对象，必须要显示的调用 release() 方法来释放。
             * 记住handler的在职责就是释放 任何传给handler的应用对象
             */
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 由于I/O的错误或者处理器实现中报错，该方法都会被调用。
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
