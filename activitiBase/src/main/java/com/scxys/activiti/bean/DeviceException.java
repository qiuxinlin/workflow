package com.scxys.activiti.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年5月24日 下午1:12:37 
* @description 说明:
*/
@Entity
@Table(name="act_deviceException")
public class DeviceException implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;//主键ID
	@Column
	private String dname;//设备名称
	@Column
	private String dcontent;//缺陷记录
	@Column
	private String leav;//等级
	@Column
	private String remark;//备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDcontent() {
		return dcontent;
	}
	public void setDcontent(String dcontent) {
		this.dcontent = dcontent;
	}
	public String getLeav() {
		return leav;
	}
	public void setLeav(String leav) {
		this.leav = leav;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
 