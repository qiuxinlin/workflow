package com.scxys.activiti.bean.workflowBean;

import java.util.List;

public class Process {

    private String processKey;

    private List<AssigneeNode> assigneeNodes;

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public List<AssigneeNode> getAssigneeNodes() {
        return assigneeNodes;
    }

    public void setAssigneeNodes(List<AssigneeNode> assigneeNodes) {
        this.assigneeNodes = assigneeNodes;
    }
}
