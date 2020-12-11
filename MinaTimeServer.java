package com.github.luyu.xiaolu778;

import com.github.luyu.Entity.currency_attr;
import com.github.luyu.dao.AttrMapper;
import com.github.luyu.dao.delete;
import com.github.luyu.dao.update;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IdleStatus;



public class MinaTimeServer {


    private static final int PORT = 6000;

    public static void main(String[] args) throws IOException {
        TimeServerHandler.data();

        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        System.out.println("begin server....");
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());

        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(StandardCharsets.UTF_8))
        );

        acceptor.setReuseAddress(true);


        acceptor.setHandler(new TimeServerHandler());
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        acceptor.bind(new InetSocketAddress(PORT));

    }
//    查
    public String select() {
        String json;
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AttrMapper mapper = session.getMapper(AttrMapper.class);

            //            查询车牌为123的
            currency_attr num_one = mapper.num1();
//            System.out.println(num_one.toString());

            Map<String, Object> foo = new HashMap<>();
            foo.put("attr_id", num_one.getAttr_id());
            foo.put("wheel", num_one.getWheel());
            foo.put("dyn_sys", num_one.getDyn_sys());
            foo.put("exh_sys", num_one.getExh_sys());
            foo.put("member", num_one.getMember());
            foo.put("size", num_one.getSize());
            foo.put("car_door", num_one.getCar_door());
            foo.put("car_light", num_one.getCar_light());
            foo.put("Clutch", num_one.getClutch());
            foo.put("car_window", num_one.getCar_window());
            foo.put("fuel", num_one.getFuel());
            foo.put("car_color", num_one.getCar_color());
            foo.put("car_attr", num_one.getCar_attr());
            json = JsonUtils.toJson(foo);
        }
        return json;
    }
//    删
    public void delete() {
        String json;
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            delete del = session.getMapper(delete.class);
            del.two();
//            session.commit();
        }
    }
//    改
    public void update() {
        String json;
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            update upd = session.getMapper(update.class);
            upd.three();
//            session.commit();

        }
    }
}
