package controller;

import Service.StudentInterface;
import model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentInterface studentInterface;

    @RequestMapping("/showStudent")
    @ResponseBody
    public List showStudent(){
        //显示学员信息
        List<Student> studentList = studentInterface.findAll();
        return studentList;
    }
}
