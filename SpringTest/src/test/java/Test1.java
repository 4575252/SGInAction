import com.iyyxx.config.ApplicationConfig;
import com.iyyxx.service.impl.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @className: Test1
 * @description: 测试spring集成junit
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 16:07:25
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class Test1 {
    @Autowired
    private TestService testService;

    @Test
    public void testMethod1() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        TestService bean = applicationContext.getBean(TestService.class);
        bean.hello();
        bean.test();
    }

    @Test
    public void testMethod2() {
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//        TestService bean = applicationContext.getBean(TestService.class);
        testService.hello();
        testService.test();
    }
}
