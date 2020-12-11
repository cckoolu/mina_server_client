package com.github.luyu.xiaolu778;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;


public class MinaServerHandler extends IoHandlerAdapter {

    public static String HEX = "0123456789ABCDEF";
    public static int i = 0;
    public static final CharsetDecoder decoder = (StandardCharsets.UTF_8).newDecoder();

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        //IoBuffer buffer = (IoBuffer) message;
        //byte[] bb = buffer.array();
        //String msg = bytesToHex(bb);
        System.out.println("i get msg:"+message.toString() + " ======> " + i++);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        String Hex;
        for (byte b : bytes) {
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            sb.append(HEX.charAt(b & 0x0f));
        }
        Hex=sb.toString();
        return Hex;
    }
}