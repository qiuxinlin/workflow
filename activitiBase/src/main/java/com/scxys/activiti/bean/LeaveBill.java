package com.scxys.activiti.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 请假单
 */
@Entity
@Table(name="leavebill")
public class LeaveBill implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "seq_act_leaveBill",sequenceName = "seq_act_leaveBill",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_act_leaveBill")
	@Column(name = "id_")
	private Long id;//主键ID
	@Column(name = "days_")
	private String days;// 请假天数
	@Column(name = "content_")
	private String content;// 请假内容
	@Column(name = "leaveDate_")
	private String leaveDate;// 请假时间
	@Column(name = "remark_")
	private String remark;// 备注
	@Column(name = "endDate_")
	private String endDate;
	@Column(name = "type_")
	private String type;
	@Column(name="user_")
	private String user;// 请假人
	@Column(name="state_")
	private String state;// 请假单状态 0初始录入,1.开始审批,2为审批完成

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
