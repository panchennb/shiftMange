package controller;

import Service.ShiftInterface;
import Service.StudentInterface;
import model.ShiftInfo;
import model.ShiftRelateInfo;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.APIUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("yxt")
public class YunXueTangController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YunXueTangController.class);

    private static final SimpleDateFormat SDF_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ShiftInterface shiftInterface;
    @Autowired
    private StudentInterface studentInterface;

    /**
     * 关联学习计划  TODO 回调yxt学习计划信息
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/relateStudyPlan", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object relateStudyPlan(HttpServletRequest request, @RequestBody ShiftRelateInfo param) {
        int code = 1;
        String msg = "";

        String id = param.getId();
        ShiftInfo shift = shiftInterface.findById(id);
        ShiftInfo shiftInfo = null;
        List<Student> students = null;
        try {
            shift.setUpdateDate(SDF_YMDHMS.parse(APIUtil.now()));
            shift.setStudyPlanId(param.getStudyPlanId());
            shift.setStudyPlanName(param.getStudyPlanName());
            shift.setIsJoin(param.getIsJoin());
            shift.setIsRelated(param.getIsRelated());
            shiftInfo = shiftInterface.save(shift);
            students = joinStudent(param);
        } catch (ParseException e) {
            LOGGER.error(e.toString());
            code = 0;
            msg = e.getMessage();
        }
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("shiftInfo", shiftInfo);
        data.put("students", students);
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    /**
     * 学员关联学习计划 TODO 学员加入yxt学习计划
     *
     * @param param
     * @return
     * @throws ParseException
     */
    private List<Student> joinStudent(ShiftRelateInfo param) throws ParseException {
        List<Student> students = studentInterface.findByShiftInfoId(param.getId());
        if (param.getIsJoin() == 1) {
            for (Student student : students) {
                student.setUpdateDate(SDF_YMDHMS.parse(APIUtil.now()));
                student.setIsJoin(param.getIsJoin());
                studentInterface.save(student);
            }
        }
        return students;
    }
}
