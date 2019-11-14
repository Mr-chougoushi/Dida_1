package com.qf.service;

import com.qf.mapper.StudentMapper;
import com.qf.pojo.Leaves;
import com.qf.pojo.MyClass;
import com.qf.pojo.Student;
import com.qf.pojo.Weekly;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private StudentMapper studentMapper;

    public StudentMapper getStudentMapper() {
        return studentMapper;
    }

    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public Student getStudentByUserName(String userName) {
        return studentMapper.getStudentByUserName(userName);
    }

    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    @Override
    public int updateUserByName(String name, String password) {
        return studentMapper.updateUserByName(name, password);
    }

    @Override
    public List<Weekly> getWeeklyByName(String name) {
        return studentMapper.getWeeklyByName(name);
    }

    @Override
    public Weekly getWeeklyById(int id) {
        return studentMapper.getWeeklyById(id);
    }

    @Override
    public int delWeeklyById(int id) {
        return studentMapper.delWeeklyById(id);
    }

    @Override
    public int addWeekly(Weekly weekly) {
        return studentMapper.addWeekly(weekly);
    }


    @Override
    public String getboosByRole() {
        return studentMapper.getboosByRole();
    }

    @Override
    public int addLeave(Leaves leaves) {
        //部署
        Map<String, Object> map = new HashMap<>();
        map.put("stuName", leaves.getStuName());
        map.put("tName", leaves.gettName());
        map.put("bName", leaves.getbName());
        map.put("boos", leaves.getBoos());
        //发起流程实例
        runtimeService.startProcessInstanceByKey("studentProcess", map);
        String id = taskService.createTaskQuery().taskAssignee(leaves.getStuName()).singleResult().getId();
        String instanceId = taskService.createTaskQuery().taskAssignee(leaves.getStuName()).singleResult().getProcessInstanceId();
        //完成请假任务
        taskService.complete(id);
        leaves.setInstanceId(instanceId);
        return studentMapper.addLeave(leaves);
    }

    @Override
    public MyClass selMyClassByCid(String cid) {
        return studentMapper.selMyClassByCid(cid);
    }
}
