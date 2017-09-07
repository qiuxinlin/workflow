package com.scxys.activiti.bean.businessBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: qiuxinlin
 * @Dercription:poc报销流程
 * @Date: 14:58 2017/9/5
 */
@Entity
@Table(name = "bus_poc")
public class BusPoc implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_bus_poc",sequenceName = "seq_bus_poc",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_bus_poc")
    @Column(name = "id_")
    private long id;
    @Column(name = "title_")
    private String title;
    @Column(name = "code_")
    private String code;
    @Column(name = "department_")
    private String department;
    //@Column(name = "detail_")

    //private Map<String,String> detail = new HashMap<String,String>();
    //@ElementCollection
    @OneToMany(cascade={CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name="bus_poc_id_")
    private List<BusPocDetail> detail=new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<BusPocDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<BusPocDetail> detail) {
        this.detail = detail;
    }
}
