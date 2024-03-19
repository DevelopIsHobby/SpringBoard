package com.myportfolio.web;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class JdbcTest {

    @Setter(onMethod_={@Autowired})
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void test3() {
        try(SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        ) {
            log.info(conn);
            log.info(session);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Setter(onMethod_={@Autowired})
    private DataSource ds;

    @Test
    public void test2() {
        try ( Connection conn = ds.getConnection()) {
            log.info(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "book_ex",
                "1234")
        ){
            log.info(conn);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
