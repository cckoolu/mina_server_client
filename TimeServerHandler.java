package com.github.luyu.xiaolu778;


import com.dream.socket.logger.Logger;
import com.dream.socket.logger.LoggerFactory;
import com.github.luyu.Entity.currency_attr;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import java.sql.Date;
import java.util.Arrays;

public class TimeServerHandler extends IoHandlerAdapter {
    public static MinaTimeServer minaTimeServer =new MinaTimeServer();

    public static void data() {
//        System.out.println(minaTimeServer.select());
        minaTimeServer.select();
        minaTimeServer.delete();
        minaTimeServer.update();

    }


    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }


    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        if( str.trim().equalsIgnoreCase("quit") ) {
//            System.out.println(str.trim().toString());
            session.write("quit\n");
            sessionClosed(session);
            return;
        }

        else if ( str.trim().equalsIgnoreCase("select") ) {
//            System.out.println(str.trim().toString());
            session.write(minaTimeServer.select());
            return;
        }
        else if ( str.trim().equalsIgnoreCase("delete") ) {
            String delete_data = "已删除";
            System.out.println(str.trim().toString());
            session.write(delete_data);
            return;
        }
        else if ( str.trim().equalsIgnoreCase("update") ) {
            String update_data = "已更改";
            System.out.println(str.trim().toString());
            session.write(update_data);
            return;
        }
        else {
            String constant = "已收到";
            System.out.println(str.trim().toString());
            //        session.write(minaTimeServer.select());
            session.write(constant);
        }


        System.out.println();
        System.out.println("Message written...");
    }
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("IDLE " + session.getIdleCount(status));
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
    }



}

