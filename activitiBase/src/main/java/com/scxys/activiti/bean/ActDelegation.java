package com.scxys.activiti.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月17日 下午2:03:47 
* @description 说明:全任务委托表
*/
@Entity
@Table(name="act_delegation")
public class ActDelegation implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	@Column
	private String owner;
	@Column
	private String assignee;
	@Column
	private String delegationDate;
	@Column
	private String startDate;
	@Column
	private String endDate;
	@Column
	private String comment;
	@Column
	private String author;
	@Column
	private int status=0;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getDelegationDate() {
		return delegationDate;
	}
	public void setDelegationDate(String delegationDate) {
		this.delegationDate = delegationDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
 