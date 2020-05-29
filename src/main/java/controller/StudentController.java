package controller;

import Service.ShiftInterface;
import Service.StudentInterface;
import model.ShiftInfo;
import model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentInterface studentInterface;

    @Autowired
    private ShiftInterface shiftInterface;

    @RequestMapping("/showStudent")
    @ResponseBody
    public HashMap showStudent(@RequestBody Long courseId){//显示开班学员信息
        HashMap map = new HashMap();
        ShiftInfo shift = shiftInterface.findByCourseId(courseId);
        List<Student> studentList = studentInterface.findByCourseId(courseId);
        map.put("shift",shift);
        map.put("studentList",studentList);
        return map;
    }



}
