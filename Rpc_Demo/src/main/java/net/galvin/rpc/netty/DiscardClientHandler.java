package net.galvin.rpc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * ChannelHandler 有两个生命周期监听方法：handlerAdded() 和 handlerRemoved()。
 * 你可以执行任意的 初始化 任务，只要这个任务不会阻塞很久。
 * 首先所有接受到的数据 必须都累计保存在buf中。
 * 然后，handler 必须检查 buf 是否有足够的数据。在本例子中是4个字节，然后执行 实际的业务逻辑。
 * 另外，当接收到新的数据，netty将会再次调用 channelRead() 方法，实际上 所有的四个字节会被积累。
 */
public class DiscardClientHandler extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        buf = ctx.alloc().buffer(4); // (1)
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        buf.release(); // (1)
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg;
        buf.writeBytes(m); // (2)
        m.release();
        if (buf.readableBytes() >= 4) { // (3)
            long currentTimeMillis = (buf.readUnsignedInt()-2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
