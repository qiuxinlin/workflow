package com.scxys.activiti; 
/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年6月20日 上午10:14:00 
* @description 说明:
*/

import org.activiti.ldap.LDAPConfigurator;
import org.activiti.ldap.LDAPGroupManager;
import org.activiti.ldap.LDAPUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

public class LdapConfig {

	@Autowired
	LDAPConfigurator configurator;
	@Autowired
	LDAPUserManager ldapUserManager;
	@Autowired
	LDAPGroupManager groupManager; 
	public LdapConfig() {
		configurator.setServer("ldap://localhost");
		configurator.setPort(389);
		configurator.setBaseDn("dc=qiuxinlin,dc=com");
		configurator.setUser("cn=Manager"); 
		configurator.setPassword("secret");
	}
}
 