package com.scxys.activiti.serviceImpl;

import com.scxys.activiti.service.WorkflowService;
import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
 * @author 作者:
 * @version 创建时间:2017年3月22日 下午2:12:07
 * @description 说明:
 */
public class WorkflowServiceImpl implements WorkflowService {

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	FormService formService;
	@Autowired
	HistoryService historyService;
	@Autowired
	IdentityService identityService;
	
	@Override
	public void delegateTasks(String afterDate,String beforeDate,String assignee,String delegateUser) {
		DateFormat dateFormat=DateFormat.getDateInstance();
		Date after= null;
		Date before= null;
		try {
			after = dateFormat.parse(afterDate);
			before = dateFormat.parse(beforeDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Task> taskList;
		if(assignee != null && assignee.isEmpty()){
			taskList=taskService.createTaskQuery().taskCreatedAfter(after).taskCreatedBefore(before)
					.list();
		}else {
			taskList=taskService.createTaskQuery().taskCreatedAfter(after).taskCreatedBefore(before)
					.taskAssignee(assignee).list();
		}
		for(Task task:taskList) {
			taskService.delegateTask(task.getId(), delegateUser);
		}
	}

	@Override
	public void deployment(String name,String diagramData,String svgData,String category) {
		//String classpath=WorkflowServiceImpl.class.getResource("/").toString().substring(6);
		/*本地文件地址*/
		//String diagramFilepath=classpath+"processes/"+name+".bpmn";
		//String svgFilepath=classpath+"processes/"+name+".svg";
		/*服务器上文件地址*/
		String diagramFilepath="/root/workflow/processes/"+name+".bpmn";
		String svgFilepath="/root/workflow/processes/"+name+".svg";
		PrintStream psDiagram = null;
		PrintStream psSvg = null;
		File diagramXml =null;
		File svg = null;
		try {
			diagramXml = new File(diagramFilepath);
			svg = new File(svgFilepath);
			psDiagram = new PrintStream(new FileOutputStream(diagramXml),true);
			psSvg = new PrintStream(new FileOutputStream(svg),true);
			psDiagram.println(diagramData);// 往文件里写入字符串
			psSvg.println(svgData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(psDiagram!=null&&psSvg!=null){
				psDiagram.close();
				psSvg.close();
			}
		}
		if(category!=null&&!category.isEmpty()){
			//修改bpmn xml文件中的targetNamespace 即修改流程定义的类别
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				Document document = documentBuilder.parse(diagramXml);
				Element rootElement = document.getDocumentElement();
				rootElement.setAttribute("targetNamespace",category);
				doc2XmlFile(document,diagramFilepath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//获取部署文件
		InputStream inputStreamDiagram= null;
		InputStream inputStreamSvg= null;
		try {
			inputStreamDiagram = new FileInputStream(diagramXml);
			inputStreamSvg=new FileInputStream(svg);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		repositoryService.createDeployment()//创建部署对象
						.name(name)//添加部署名称
						.category(category)
						.addInputStream(name+".bpmn",inputStreamDiagram)
						.addInputStream(name+".svg",inputStreamSvg)
						.deploy();
	}
	public void doc2XmlFile(Document document, String filename) throws TransformerException
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);
		}
}
