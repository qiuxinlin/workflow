import com.alibaba.fastjson.JSON;
import com.example.springDataJpaDemo.SpringDataJpaDemoApplication;
import com.example.springDataJpaDemo.UserEntity;
import com.example.springDataJpaDemo.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by pengyingzhi on 2017/3/27.
 */
@SpringBootTest(classes = SpringDataJpaDemoApplication.class)
@RunWith(SpringRunner.class)
public class SpringDataJpademoTests {
    @Autowired
    UserRepository userRepository;


    @Test
    @Transactional
    public void add(){
        UserEntity user = new UserEntity();
        user.setName("李四");
        userRepository.save(user);
    }

    @Test
    @Transactional
    public void delete(){
        userRepository.delete(1L);
    }

    @Test
    @Transactional
    public void delete2(){
        UserEntity user = userRepository.findOne(2L);
        userRepository.delete(user);
    }

    @Test
    @Transactional
    public void update(){
        UserEntity user = userRepository.findOne(2L);
        user.setName("李四");
        userRepository.save(user);
    }

    @Test
    public void findName(){
        List<UserEntity> li = userRepository.findByName("张三");
        int size = li.size();
        System.out.println("结果："+size+"条");
        for (int i = 0; i < size; i++) {
            System.out.println("第"+(i+1)+"条");
            System.out.println(JSON.toJSONString(li.get(i)));
        }
    }

    @Test
    public void findName2(){
        List<UserEntity> li = userRepository.findByNameWithHql("张三");
        int size = li.size();
        System.out.println("结果："+size+"条");
        for (int i = 0; i < size; i++) {
            System.out.println("第"+(i+1)+"条");
            System.out.println(JSON.toJSONString(li.get(i)));
        }
    }

    @Test
    public void findName3(){
        List<UserEntity> li = userRepository.findByNameWithHql2("张三");
        int size = li.size();
        System.out.println("结果："+size+"条");
        for (int i = 0; i < size; i++) {
            System.out.println("第"+(i+1)+"条");
            System.out.println(JSON.toJSONString(li.get(i)));
        }
    }

    @Test
    public void findNameOrderPwd(){
        List<UserEntity> li = userRepository.findByNameOrderByPwd("张三");
        int size = li.size();
        System.out.println("结果："+size+"条");
        for (int i = 0; i < size; i++) {
            System.out.println("第"+(i+1)+"条");
            System.out.println(JSON.toJSONString(li.get(i)));
        }
    }

    @Test
    public void findNameAndPwd(){
        List<UserEntity> li = userRepository.findByNameAndPwd("张三","1");
        int size = li.size();
        System.out.println("结果："+size+"条");
        for (int i = 0; i < size; i++) {
            System.out.println("第"+(i+1)+"条");
            System.out.println(JSON.toJSONString(li.get(i)));
        }
    }

    @Test
    public void findNameOrPwd(){
        List<UserEntity> li = userRepository.findByNameOrPwd("张三","3");
        int size = li.size();
        System.out.println("结果："+size+"条");
        for (int i = 0; i < size; i++) {
            System.out.println("第"+(i+1)+"条");
            System.out.println(JSON.toJSONString(li.get(i)));
        }
    }

    @Test
    public void findNameLike(){
        List<UserEntity> li = userRepository.findByNameLike("%三%");
        int size = li.size();
        System.out.println("结果："+size+"条");
        for (int i = 0; i < size; i++) {
            System.out.println("第"+(i+1)+"条");
            System.out.println(JSON.toJSONString(li.get(i)));
        }
    }

    @Test
    public void findByNameContaining(){
        List<UserEntity> li = userRepository.findByNameContaining("三");
        int size = li.size();
        System.out.println("结果："+size+"条");
        for (int i = 0; i < size; i++) {
            System.out.println("第"+(i+1)+"条");
            System.out.println(JSON.toJSONString(li.get(i)));
        }
    }
}
