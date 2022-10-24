import com.iyyxx.dao.UserDao;
import com.iyyxx.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @className: TestMybatis
 * @description: 测试mybatis单独使用
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 16:28:53
 **/
public class TestMybatis {
    @Test
    public void testMybatis() {
        String resource = "mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            User user = userDao.findById(1);
            System.out.println(user);
            sqlSession.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
