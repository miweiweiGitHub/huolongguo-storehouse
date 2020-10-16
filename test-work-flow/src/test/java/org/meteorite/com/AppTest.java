package org.meteorite.com;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppTest {

    @Autowired
    private ProcessEngine processEngine;
    /**
     * 部署流程定义
     */
    @Test
    public void createDeployment() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/test-huolongguo.bpmn")
                .name("火龙果测试流程")
                .deploy();

        log.info("部署流程 id：{}",deployment.getId());
        log.info("部署流程 名称：{}",deployment.getName());
    }

    /**
     * 启动流程实例分配任务给个人
     */
    @Test
    public void startProcessesInstance() {
        String userKey = "PTM";

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("userKey",userKey);
        variables.put("day",2);
        variables.put("users","huolongguo");

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance myProcess = runtimeService.startProcessInstanceByKey("myProcess_1",variables);

        log.info("流程定义 id：{}",myProcess.getProcessDefinitionId());
        log.info("流程实例 id：{}",myProcess.getId());

    }

    /**
     * 查询个人的任务
     */
    @Test
    public void findPersonTaskList(){

        TaskService taskService = processEngine.getTaskService();

        List<Task> list = taskService.createTaskQuery().list();
        TaskQuery taskQuery = taskService.createTaskQuery();

        for (Task task : list) {
            log.info("流程实例 id：{}",task.getProcessInstanceId());
            log.info("任务 id：{}",task.getId());
            log.info("任务负责人 ：{}",task.getAssignee());
            log.info("任务名称 ：{}",task.getName());

        }

    }

}
