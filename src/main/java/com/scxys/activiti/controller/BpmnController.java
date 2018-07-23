package com.scxys.activiti.controller;


import com.neoinfo.pojo.CommRes;
import com.scxys.activiti.bean.workflowBean.AssigneeNode;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "activiti")
public class BpmnController {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    //获取流程图的每个节点信息
    @ResponseBody
    @RequestMapping(value = "/taskNodeInfo")
    public CommRes taskNodeInfo(String fileName) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            String path = "/root/workflow/processes/"+ fileName+".bpmn";
            Document document = documentBuilder.parse(new File(path));
            Element rootElement = document.getDocumentElement();
            NodeList rootChildren = rootElement.getElementsByTagName("userTask");
            //获取排他网关
            NodeList exclusiveGatewayNodes = rootElement.getElementsByTagName("exclusiveGateway");
            boolean hasCondition = false;
            if (null != exclusiveGatewayNodes && exclusiveGatewayNodes.getLength() > 0) {
                hasCondition = true;
            }
            List<AssigneeNode> assigneeNodeList = new ArrayList<>(rootChildren.getLength());
            for (int i = 0; i < rootChildren.getLength(); i++) {
                AssigneeNode assigneeNode = new AssigneeNode();
                Node node = rootChildren.item(i);
                String taskId = this.getNodeId(node);
                assigneeNode.setTaskId(taskId);
                String taskName=this.getTaskName(node);
                assigneeNode.setTaskName(taskName);
                //判断任务前是否有条件
                //如果hasCondition为false则没有排它网关，即不存在条件
                if (!hasCondition) {
                    assigneeNode.setHasCondition(false);
                } else {
                    //先找到任务节点前的连线对象
                    //存在条件则查找对应条件表达式
                    NodeList sequenceFlowNodeList = rootElement.getElementsByTagName("sequenceFlow");
                    for (int l = 0; l < sequenceFlowNodeList.getLength(); l++) {
                        Node sequenceNode = sequenceFlowNodeList.item(l);
                        String target = this.getTargetRef(sequenceNode);
                        if (!target.equals(taskId)) {
                            continue;
                        }
                        NodeList sequenceNodeList = sequenceNode.getChildNodes();
                        for (int m = 0; m < sequenceNodeList.getLength(); m++) {
                            if ("conditionExpression".equals(sequenceNodeList.item(m).getNodeName())) {
                                String condition = sequenceNodeList.item(m).getTextContent();
                                assigneeNode.setHasCondition(true);
                                assigneeNode.setCondition(condition);
                            }
                        }
                    }
                }
                //获取任务标签的属性
                NamedNodeMap nodeMap = node.getAttributes();

                //判断是否为多人，标签name为multiInstanceLoopCharacteristics
                NodeList child = node.getChildNodes();
                if (null != child && child.getLength() > 0) {
                    for (int j = 0; j < child.getLength(); j++) {
                        if ("multiInstanceLoopCharacteristics".equals(child.item(j).getNodeName())) {
                            assigneeNode.setSingle(false);
                            NamedNodeMap namedNodeMap = child.item(j).getAttributes();
                            Node nameNode = namedNodeMap.getNamedItem("isSequential");
                            if(nameNode==null){
                                assigneeNode.setSerial(false);
                                break;
                            }
                            String nameNodeValue = nameNode.getNodeValue();
                            if (null == nameNodeValue || nameNodeValue.isEmpty()) {
                                assigneeNode.setSerial(true);
                                break;
                            }
                            if ("true".equals(nameNodeValue)) {
                                assigneeNode.setSerial(true);
                                break;
                            }
                            if ("false".equals(nameNodeValue)) {
                                assigneeNode.setSerial(false);
                                break;
                            }
                        } else {
                            assigneeNode.setSingle(true);
                        }
                    }
                } else {
                    assigneeNode.setSingle(true);
                }
                assigneeNodeList.add(assigneeNode);
            }
            return CommRes.success(assigneeNodeList);
        } catch (Exception e) {
            e.printStackTrace();
           return CommRes.errorRes(HttpStatus.EXPECTATION_FAILED.toString(),"文件解析异常");
        }
    }


    /**
     * 获取当前流程的下一个节点
     *
     * @param procInstanceId
     * @return
     */
    public String getNextNode(String procInstanceId) {
        // 1、首先是根据流程ID获取当前任务：
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(procInstanceId).list();
        String nextId = "";
        for (Task task : tasks) {
            RepositoryService rs = processEngine.getRepositoryService();
            // 2、然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
            ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) rs)
                    .getDeployedProcessDefinition(task.getProcessDefinitionId());
            List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
            // 3、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
            String excId = task.getExecutionId();
            ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
                    .singleResult();
            String activitiId = execution.getActivityId();
            // 4、然后循环activitiList
            // 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
            for (ActivityImpl activityImpl : activitiList) {
                String id = activityImpl.getId();
                if (activitiId.equals(id)) {
                    List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
                    for (PvmTransition tr : outTransitions) {
                        PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
                        nextId = ac.getId();
                    }
                    break;
                }
            }
        }
        return nextId;
    }

    //获取节点id
    private String getNodeId(Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        Node nameNode = namedNodeMap.getNamedItem("id");
        return nameNode.getNodeValue();
    }

    //获取连线目标值targetRef
    private String getTargetRef(Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        Node nameNode = namedNodeMap.getNamedItem("targetRef");
        return nameNode.getNodeValue();
    }

    //获取任务name
    public String getTaskName(Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        Node nameNode = namedNodeMap.getNamedItem("name");
        return nameNode.getNodeValue();
    }


}
