package com.scxys.ldap; 
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.ldap.LDAPConfigurator;
import org.activiti.ldap.LDAPUserManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestLdap {

   /* public static void add(Ldap ldap){
        User user = new User();
        user.setId("111");
        user.setName("chensi");
        ldap.add("cn="+user.getName()+",ou=developer", user);
    }
 
    public static void delete(Ldap ldap){
    	ldap.delete("cn=zhangsan");
    }
 
    public static void modfiyRDN(Ldap ldap){
    	ldap.modifyRDN("ou=12345678", "ou=123456");
    }
 
    public static void modfiyAttrs(Ldap ldap){
        User user = new User();
        user.setId("12345678");
        user.setName("张三");
        ldap.modfiyAttrs("uid=cuibo, ou=users", user);
    }
 
    public static void find(Ldap ldap){
        User user = ldap.find("cn=zhaoliu");
        System.out.println(user.getId() + " | " + user.getName());
    }
 */
    /**
      * @param args
    */
	@Test
	public void test() {
		
		LDAPConfigurator ldapConfigurator=new LDAPConfigurator();
		ldapConfigurator.setServer("ldap://localhost");
		ldapConfigurator.setPort(389);
		//ldapConfigurator.setUser("cn=Manager,dc=qiuxinlin,dc=com");
		ldapConfigurator.setUserBaseDn("cn=Manager,dc=qiuxinlin,dc=com");
		ldapConfigurator.setPassword("secret");
    	LDAPUserManager ldapUserManager=new LDAPUserManager(ldapConfigurator);
    	//ldapUserManager.findUserById("zhangsan");
    	
    	ldapUserManager.createNewUser("qiuxinlin");
    }
}
