package com.example.ldapDemo;

import com.example.ldapDemo.domain.Person;
import com.example.ldapDemo.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by pengyingzhi on 2017/4/19.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LdapDemoApplicationTest {
    @Autowired
    PersonRepository repository;
    @Test
    public void test(){
        System.out.println("People found with findAll():");
        System.out.println("-------------------------------");
        for (Person person : this.repository.findAll()) {
            System.out.println(person);
        }
        System.out.println();

        // fetch an individual person
        System.out.println("Person found with findByPhone('+46 555-123456'):");
        System.out.println("--------------------------------");
        System.out.println(this.repository.findByPhone("+46 555-123456"));

        // fetch an individual person
        System.out.println("Person found with findByUserName('zhaoliu'):");
        System.out.println("--------------------------------");
        System.out.println(this.repository.findByUserName("zhaoliu"));
    }
    @Test
    public void test2() {
    	System.out.println();
    }
}