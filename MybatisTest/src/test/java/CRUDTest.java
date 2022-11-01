import com.iyyxx.dao.UserDao;
import com.iyyxx.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * @className: CRUDTest
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/31/0031 14:31:26
 **/
public class CRUDTest {


    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @After
    public void tearDown() throws Exception {
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testInsert() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUsername("张三");
        user.setAddress("福建厦门");
        user.setAge(40);
        int count = userDao.insert(user);
        System.out.println("影响记录数为: "+count);
        System.out.println("新增的用户id为: "+user.getId());
    }

    @Test
    public void testUpdate() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setId(10);
        user.setUsername("张三");
        user.setAddress("漳州");
        user.setAge(20);
        int count = userDao.update(user);
        System.out.println("影响记录数为: "+count);
    }

    @Test
    public void testResearchAndDel() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.findByUsername("张三");
        if(user != null){
            int count = userDao.deleteById(user.getId());
            System.out.println("影响记录数为: "+count);
        }
    }
}
