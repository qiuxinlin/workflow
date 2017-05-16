package com.example.springDataJpaDemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pengyingzhi on 2017/3/27.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>{
    List<UserEntity> findByName(String name);
    List<UserEntity> findByNameOrderByPwd(String name);
    List<UserEntity> findByNameAndPwd(String name,String pwd);
    List<UserEntity> findByNameOrPwd(String name,String pwd);
    List<UserEntity> findByNameLike(String name);
    List<UserEntity> findByNameContaining(String name);


    @Query(value = "select u from UserEntity u where u.name = ?1")
    List<UserEntity> findByNameWithHql(String name);

    @Query(value = "select u from UserEntity u where u.name = :name")
    List<UserEntity> findByNameWithHql2(@Param("name") String name);
}
