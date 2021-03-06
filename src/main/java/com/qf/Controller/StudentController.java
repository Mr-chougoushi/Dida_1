package com.qf.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.Leaves;
import com.qf.pojo.MyClass;
import com.qf.pojo.Student;
import com.qf.pojo.Weekly;
import com.qf.service.StudentService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("Student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }




    //个人资料管理
    @RequestMapping("studentInfor")
    public String myInfor(HttpServletRequest request) {
        if (request.getSession().getAttribute("student") == null) {
            String userName = (String) request.getSession().getAttribute("userName");
            Student student = studentService.getStudentByUserName(userName);
            request.getSession().setAttribute("student", student);
        }
        return "studentInfor";
    }

    //根据用户名查到Student信息传到修改页面
    @RequestMapping("getStudent")
    public String getStudent() {
        return "updateStudent";
    }

    //保存修改信息内容，跳转
    @RequestMapping("saveStudent")
    public String saveStudent(Student student, HttpServletRequest request, HttpServletResponse response) {
        studentService.updateStudent(student);
        String userName = (String) request.getSession().getAttribute("userName");
        Student student2 = studentService.getStudentByUserName(userName);
        request.getSession().setAttribute("student", student2);
        try {
            PrintWriter writer = response.getWriter();
            writer.write("修改成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "studentInfor";
    }



    //获取学生周报列表
    @RequestMapping("studentWeekly")
    public String studentWeekly(@RequestParam(defaultValue = "1") Integer startPage, Model model, HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        Student student = studentService.getStudentByUserName(userName);
        PageHelper.startPage(startPage, 6);
        List<Weekly> weeklyList = studentService.getWeeklyByName(student.getStuName());
        PageInfo<Weekly> wkPageInfo = new PageInfo<>(weeklyList);
        model.addAttribute("wkPageInfo", wkPageInfo);
        request.getSession().setAttribute("weeklyList", weeklyList);
        return "studentWeekly";
    }

    //查看周报
    @RequestMapping("selWeekly")
    public String selWeekly(int wkId, HttpServletRequest request) {
        Weekly weekly = studentService.getWeeklyById(wkId);
        request.setAttribute("weekly", weekly);
        return "selWeekly";

    }

    //删除周报
    @RequestMapping("deleteWeekly")
    @ResponseBody
    public String studentWeekly(int wkId) {
        int i = studentService.delWeeklyById(wkId);
        if (i > 0) {
            return "ok";
        }
        return "TryAgain";
    }

    //新建周报功能跳转
    @RequestMapping("addWeekly")
    public String addWeekly(HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        Student student = studentService.getStudentByUserName(userName);
        request.setAttribute("myName", student);
        return "addWeekly";
    }

    //保存新建周报
    @RequestMapping("saveWeekly")
    public String saveWeekly(Weekly weekly, HttpServletResponse response) {
        int i = studentService.addWeekly(weekly);
        PrintWriter writer = null;
        response.setContentType("text/html;charset=UTF-8");
        if (i > 0) {
            try {
                writer = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer.print("<script language=\"javascript\">alert('新建成功！');window.location.href='studentWeekly'</script>");
            writer.close();
            return "studentWeekly";
        }
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.print("<script language=\"javascript\">alert('新建失败！');window.location.href='studentWeekly'</script>");
        writer.close();
        return "studentWeekly";
    }

    //请假跳转
    @RequestMapping("studentLeave")
    public String studentLeave(HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        Student student = studentService.getStudentByUserName(userName);
//        int i = studentService.selectProcess(student.getStuName());
//        if (i==0) {
            MyClass myClass = studentService.selMyClassByCid(student.getCid());
            String boosName = studentService.getboosByRole();
            request.setAttribute("stuName", student.getStuName());
            request.setAttribute("myClass", myClass);
            request.setAttribute("boosName", boosName);
            return "studentLeave";
//        }else {
//            return "leaveFailure";
//        }
    }

    @RequestMapping("saveLeave")
    public String saveLeave(Leaves leaves) {
        studentService.addLeave(leaves);
        return "homePage";
    }

}
