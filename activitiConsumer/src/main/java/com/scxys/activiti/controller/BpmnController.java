package com.scxys.activiti.controller;


import com.scxys.activiti.bean.workflowBean.AssigneeNode;
import com.scxys.activiti.bean.workflowBean.CommRes;
import com.scxys.activiti.bean.workflowBean.ProcessDefineInfo;
import com.scxys.activiti.bean.workflowBean.TaskInfo;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
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
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

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


    public static List<AssigneeNode> assigneeNodes;

    //发布流程
    @ResponseBody
    @RequestMapping(value = "/deploy")
    public CommRes DeployProgress(String fileName) {
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes" + File.separator + fileName).deploy();
        System.out.println("流程模型部署成功");
        System.out.println("流程部署id:" + "-------------" + deployment.getId());
        return new CommRes("200", "部署流程id:" + deployment.getId(), "成功");
    }

    @ResponseBody
    @RequestMapping(value = "/deploy/list")
    public CommRes getDeployList() {
        List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
        if (null == definitionList || definitionList.size()>0) {
            return new CommRes("200",null,"没有数据");
        }
        List<ProcessDefineInfo> processDefineInfoList = new ArrayList<>(definitionList.size());
        for (ProcessDefinition processDefinition : definitionList) {
            ProcessDefineInfo processDefineInfo = new ProcessDefineInfo();
            processDefineInfo.setId(processDefinition.getId());
            processDefineInfo.setName(processDefinition.getName());
            processDefineInfo.setKey(processDefinition.getKey());
            processDefineInfoList.add(processDefineInfo);
        }
        return new CommRes("200",processDefineInfoList,"ok");
    }

    //启动流程
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public CommRes startProgress(String assignees, String processKey) {
        List<String> assigneeList = this.convertToList(assignees);
        Map<String, Object> varibles = new HashMap<>();
        varibles.put("assigneeList", assigneeList);
        ProcessInstance processInstance = null;
        try {
            processInstance = runtimeService.startProcessInstanceByKey(processKey, varibles);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommRes("500", null, "失败");
        }
        return new CommRes("200", processInstance.getTenantId(), "成功");
    }

    //获取任务列表
    @ResponseBody
    @RequestMapping(value = "/task/list")
    public CommRes getTask(String assignee) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(assignee);
        List<Task> taskList = taskQuery.list();
        List<TaskInfo> taskInfoList = new ArrayList<>(taskList.size());
        for (Task task : taskList) {
            System.out.println(task.getAssignee() + "----" + task.getName() + "--" + task.getId());
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setAssignee(task.getAssignee());
            taskInfo.setTaskName(task.getName());
            taskInfo.setTaskId(task.getId());
            taskInfoList.add(taskInfo);
        }
        return new CommRes("200", taskInfoList, "成功");
    }

    //完成节点任务
    @ResponseBody
    @RequestMapping(value = "/task/complete")
    public CommRes complete(String taskId, String proInstanceId, String assignees) {
        if (null == assignees || "".equals(assignees)) {
            try {
                taskService.complete(taskId);
                return new CommRes("200", null, "完成任务节点");
            } catch (Exception e) {
                e.printStackTrace();
                return new CommRes("500", null, "异常");
            }
        }
        Map<String, Object> varibles = new HashMap<>();
        //单个办理人
        if (null != assignees && !assignees.contains(",")) {
            List<String> assigneeList = new ArrayList<>();
            assigneeList.add(assignees);
            varibles.put("assigneeList", assigneeList);
        }
        //多个办理人
        if (null != assignees && assignees.contains(",")) {
            List<String> assigneeList = this.convertToList(assignees);
            varibles.put("assigneeList", assigneeList);
        }
        try {
            taskService.complete(taskId, varibles);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommRes("500", null, "error");
        }
        return new CommRes("200", null, "ok");
    }

    //获取当前节点还有多少个办理任务（是否办理就进入下一个节点）
    @ResponseBody
    @RequestMapping(value = "/task/compresult")
    public CommRes getCompResult(String taskId) {
        Integer instanceOfNumbers = (Integer) taskService.getVariable(taskId, "nrOfInstances");
        Integer completeCounter = (Integer) taskService.getVariable(taskId, "nrOfCompletedInstances");
        return new CommRes("200", instanceOfNumbers + "--" + completeCounter, "ok");
    }

    //获取流程图的每个节点信息
    @ResponseBody
    @RequestMapping(value = "/taskNodeInfo")
    public CommRes taskNodeInfo(String fileName) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            String path = "H:"+File.separator+"idea"+File.separator+"workspace"+File.separator+"activity" +File.separator+"target"+File.separator+"classes"+File.separator+ "processes" + File.separator + fileName;
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
                            String nameNodeValue = nameNode.getNodeValue();
                            if (null == nameNodeValue || "".equals(nameNodeValue)) {
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
            return new CommRes("200", assigneeNodeList, "ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommRes("500", null, "error");
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
    public String getNodeId(Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        Node nameNode = namedNodeMap.getNamedItem("id");
        return nameNode.getNodeValue();
    }

    //获取连线目标值targetRef
    public String getTargetRef(Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        Node nameNode = namedNodeMap.getNamedItem("targetRef");
        return nameNode.getNodeValue();
    }

    public List<String> convertToList(String assignees) {
        String[] assigneeArr = assignees.split(",");
        List<String> assigneeList = new ArrayList<>();
        for (int i = 0; i < assigneeArr.length; i++) {
            assigneeList.add(assigneeArr[i]);
        }
        return assigneeList;
    }

    @ResponseBody
    @RequestMapping(value = "/task/nodes/{processId}")
    public CommRes getNodes(@PathVariable String processId) {
        BpmnModel model = repositoryService.getBpmnModel(processId);
        if (null != model) {
            Collection<FlowElement> flowElementList = model.getMainProcess().getFlowElements();
            return new CommRes("200", flowElementList, "ok");
        }
        return new CommRes("500", null, "error");
    }

}
