package com.scxys.activiti.bean.workflowBean;

import java.io.Serializable;

public class AssigneeNode implements Serializable{

    //任务标签id
    private String taskId;

    //单个办理人
    private String assignee;

    //多个办理人"user1,user2,user3"
    private String assignees;

    //条件表达式
    private String condition;

    private Boolean single;

    //多人串行还是并行(true是串行，false是并行)
    private Boolean serial;

    //该节点是否需要条件
    private Boolean hasCondition;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssignees() {
        return assignees;
    }

    public void setAssignees(String assignees) {
        this.assignees = assignees;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }

    public Boolean getSerial() {
        return serial;
    }

    public void setSerial(Boolean serial) {
        this.serial = serial;
    }

    public Boolean getHasCondition() {
        return hasCondition;
    }

    public void setHasCondition(Boolean hasCondition) {
        this.hasCondition = hasCondition;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
