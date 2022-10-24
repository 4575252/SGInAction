import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iyyxx.dao.OrderDao;
import com.iyyxx.dao.UserDao;
import com.iyyxx.domain.Order;
import com.iyyxx.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @className: TestMybatis
 * @description: Mybatis test
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 16:28:53
 **/
public class TestMybatis {

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
    public void findAllUser() throws IOException {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> list = userDao.findAll();
        System.out.println(list);
        sqlSession.close();

        sqlSession = sqlSessionFactory.openSession();
        UserDao userDao2 = sqlSession.getMapper(UserDao.class);
        List<User> list2 = userDao2.findAll();
        System.out.println(list2);
    }


    @Test
    public void findAllOrder() {
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        List<Order> list = orderDao.findAll();
        System.out.println(list);
    }


    @Test
    public void find1by1Order() {
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        Order orderAndUser = orderDao.findOrderAndUser(1);
        System.out.println(orderAndUser);
    }

    @Test
    public void find1byMUser() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.findUserAndRole(1);
        System.out.println(user);
    }
    @Test
    public void findUserAndRoleStep() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.findByUserName("UZI");
        System.out.println(user.getUsername());
        System.out.println(user.getRoles().size());
    }


    @Test
    public void findAllUserPage() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        PageHelper.startPage(1,1);
        List<User> list = userDao.findAll();
        System.out.println(list);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        System.out.println(pageInfo);
    }
}
