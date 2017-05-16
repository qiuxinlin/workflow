package com.example.entity; 
/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年5月5日 上午11:01:48 
* @description 说明:
*/
public class Greeting {

	private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
 