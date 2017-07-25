package com.scxys.activiti.bean;

import java.io.Serializable;
import javax.persistence.*;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月4日 下午2:11:58 
* @description 说明:业务流程基本信息
*/
@Entity
@Table(name="act_process")
public class ActProcess implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "seq_act_process",sequenceName = "seq_act_process",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_act_process")
	private Long id;//主键ID
	@Column
	private String processCode;
	@Column
	private String processName;
	@Column
	private String deploymentId;
	@Column
	private String flowClassify;
	@Column
	private String describtion;
	@Column
	private String parentCode;//上级编码
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getFlowClassify() {
		return flowClassify;
	}
	public void setFlowClassify(String flowClassify) {
		this.flowClassify = flowClassify;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
}
 