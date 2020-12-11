package com.github.luyu.xiaolu778;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.ExpiringSessionRecycler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

public class MinaServer {
    static NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
    public static void main(String[] args) throws Exception {
        acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
        // 设置handler，把这个IoHandler 注册到IoService
        acceptor.setHandler(new MinaServerHandler());
        acceptor.setSessionRecycler(new ExpiringSessionRecycler(15 * 1000));
        DatagramSessionConfig dcfg = acceptor.getSessionConfig();
        dcfg.setReuseAddress(true);
        dcfg.setReceiveBufferSize(1024);
        dcfg.setSendBufferSize(1024);
        //绑定端口
        acceptor.bind(new InetSocketAddress(2222));


        System.out.println("Server Listening...");
    }

    public static Boolean SendMsg(String message, String sessionip, int port) {
        try {
            SocketAddress ra = new InetSocketAddress(sessionip, port);
            SocketAddress loc = new InetSocketAddress(2222);
            IoSession MySession = acceptor.newSession(ra, loc);
            byte[] res = hex2Byte(message);
            IoBuffer buf = IoBuffer.wrap(res);
            WriteFuture future = MySession.write(buf, ra);
            future.awaitUninterruptibly(100);
            if (future.isWritten()) {
                MySession.closeOnFlush();
                return true;
            } else {
                MySession.closeOnFlush();
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public static byte[] hex2Byte(String hex) {
        String digital = "0123456789ABCDEF";
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        int temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte) (temp & 0xff);
        }
        return bytes;
    }
}

