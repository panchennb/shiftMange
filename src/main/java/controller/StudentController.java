package controller;

import Service.ShiftInterface;
import Service.StudentInterface;
import model.ShiftInfo;
import model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentInterface studentInterface;

    @Autowired
    private ShiftInterface shiftInterface;

    @RequestMapping(value="/showStudent",method = RequestMethod.POST)
    @ResponseBody
    public HashMap showStudent(@RequestParam Long courseId, Integer page, Integer rows){//显示开班学员信息
        HashMap map = new HashMap();
        Sort sort = Sort.by(Sort.Direction.ASC,"courseId");
        ShiftInfo shift = shiftInterface.findByCourseId(courseId);
        List<Student> studentList = studentInterface.findByCourseId(courseId,PageRequest.of(page-1,rows,sort));
        map.put("shift",shift);
        map.put("studentList",studentList);
        return map;
    }



}
