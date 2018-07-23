package com.scxys.activiti.bean.businessBean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: qiuxinlin
 * @Dercription:
 * @Date: 10:11 2017/9/6
 */
@Entity
@Table(name = "bus_poc_detail")
public class BusPocDetail implements Serializable {
    private static final long serialVersionUID = -8736799419914894180L;
    @Id
    @SequenceGenerator(name = "seq_bus_poc_detail",sequenceName = "seq_bus_poc_detail",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_bus_poc_detail")
    @Column(name = "id_")
    private long id;
    @Column(name = "subject_")
    private String subject;
    @Column(name = "money_")
    private int money;
    @Column(name = "date_")
    private String date;
    @Column(name = "remark_")
    private String remark;
    @Column(name = "bus_poc_id_")
    private Long busPocId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getBusPocId() {
        return busPocId;
    }

    public void setBusPocId(Long busPocId) {
        this.busPocId = busPocId;
    }
}
