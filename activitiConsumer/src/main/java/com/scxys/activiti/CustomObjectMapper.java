package com.scxys.activiti;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年5月9日 下午2:53:10 
* @description 说明:
*/
public class CustomObjectMapper extends ObjectMapper{
    public CustomObjectMapper(){
        ObjectMapper sp=new ObjectMapper();
        sp.getSerializerProvider().setNullValueSerializer(new NullSerializer());
    }

}