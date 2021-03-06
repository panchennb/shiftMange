package controller;

import Service.ShiftInterface;
import Service.StudentInterface;
import model.ShiftInfo;
import model.Student;
import model.zhijian.LessonInfo;
import model.zhijian.StudentInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.APIUtil;
import utils.OKHttp2Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("zhijian")
public class ZhiJianController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZhiJianController.class);

    private static final String URL_PREIX = "http://36.49.92.140:8081/tomp";
    private static final String TOKEN_URL = URL_PREIX + "/check/getToken";
    private static final String KBQR_URL = URL_PREIX + "/visitors/saveZjsz";
    private static final String KZJG_URL = URL_PREIX + "/Dsfkcjg/kcjgadd";
    private static final String JYKH_URL = URL_PREIX + "/Examination/assess";

    private static final String USERNO = "yxt";
    private static final String USERPWD = "yxt";

    private static final SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat SDF_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private StudentInterface studentInterface;

    @Autowired
    private ShiftInterface shiftInterface;

    /**
     * 登录接口
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object login(HttpServletRequest request, @RequestBody Map<String, String> param) {
        String userNo = param.get("userNo");
        String userPwd = param.get("userPwd");
        Object result = null;
        try {
            result = getToken(userNo, userPwd);
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
        return result;
    }

    /**
     * 1.获取token
     *
     * @param userNo
     * @param userPwd
     * @return
     * @throws IOException
     */
    public static Object getToken(String userNo, String userPwd) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("userPwd", userPwd);
        return OKHttp2Utils.postJson(TOKEN_URL, JSONObject.fromObject(params).toString());
    }

    /**
     * 2.开班信息同步接口
     *
     * @param request
     * @param planMap
     * @return
     */
    @RequestMapping(value = "/savelessoninfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private Object saveLessonInfo(HttpServletRequest request, @RequestBody Map<String, Object> planMap) {
        LOGGER.info("savelessoninfo===== {}", planMap);
        String flg = "true";
        String errMsg = null;

        JSONObject mapJson = JSONObject.fromObject(planMap);
        LessonInfo lessonInfo = json2Object(mapJson.getJSONObject("kcxx"), LessonInfo.class);
        List<StudentInfo> studentInfos = json2List(mapJson.getJSONArray("xyxx"), StudentInfo.class);
        // 同步课程
        // 检查课程是否已同步
        ShiftInfo shiftInfoFromDB = shiftInterface.findByCourseId(lessonInfo.getKbsqId());
        ShiftInfo newShiftInfo = new ShiftInfo();
        try {
            if (shiftInfoFromDB != null) {
                newShiftInfo = getNewShiftInfo(shiftInfoFromDB, lessonInfo);
            } else {
                ShiftInfo shiftInfo = new ShiftInfo();
                shiftInfo.setCreateDate(SDF_YMDHMS.parse(APIUtil.now()));
                newShiftInfo = getNewShiftInfo(shiftInfo, lessonInfo);
            }
        } catch (ParseException e) {
            LOGGER.error(e.toString());
            flg = "false";
            errMsg = e.getMessage();
        }
        ShiftInfo shift = shiftInterface.save(newShiftInfo);
        // 同步学员
        // 检查学员是否已同步
        List<Student> studentsFromDB = studentInterface.findByShiftInfoId(shift.getId());
        for (StudentInfo studentInfo : studentInfos) {
            try {
                Student newStudent = null;
                for (Student studentFromDB : studentsFromDB) {
                    if (studentFromDB.getStudentId().equals(studentInfo.getKbxyXyid())) {
                        newStudent = getNewStudent(studentFromDB, studentInfo, shift);
                        break;
                    }
                }
                if (newStudent == null) {
                    newStudent = new Student();
                    newStudent.setCourseId(shift.getCourseId());
                    newStudent.setShiftInfoId(shift.getId());
                    newStudent.setCreateDate(SDF_YMDHMS.parse(APIUtil.now()));
                    newStudent = getNewStudent(newStudent, studentInfo, shift);
                }
                studentInterface.save(newStudent);
            } catch (ParseException e) {
                LOGGER.error(e.toString());
                flg = "false";
                errMsg = e.getMessage();
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("flg", flg);
        result.put("errMsg", errMsg);
        return result;
    }

    private Student getNewStudent(Student student, StudentInfo studentInfo, ShiftInfo shiftInfo) throws ParseException {
        student.setUpdateDate(SDF_YMDHMS.parse(APIUtil.now()));
        student.setIdCard(studentInfo.getXyxxSfzh());
        student.setName(studentInfo.getXyxxName());
        student.setUserNo(studentInfo.getUserNo());
        student.setPhone(studentInfo.getXyxxLxdh());
        student.setStudentId(studentInfo.getKbxyXyid());
        student.setUserPass(studentInfo.getUserPass());
        student.setTrainingAgencyId(shiftInfo.getTrainingAgencyId());
        return student;
    }

    private ShiftInfo getNewShiftInfo(ShiftInfo shiftInfo, LessonInfo lessonInfo) throws ParseException {
        shiftInfo.setCourseId(lessonInfo.getKbsqId());
        shiftInfo.setChargeUserNo(lessonInfo.getUserNo());
        shiftInfo.setChargeUserPwd(lessonInfo.getUserPwd());
        shiftInfo.setTrainingAgencyId(lessonInfo.getKbsqJgid());
        shiftInfo.setTrainingAgencyName(lessonInfo.getJgglName());
        shiftInfo.setCourseStartDate(SDF_YMD.parse(lessonInfo.getKbsqKssj()));
        shiftInfo.setCourseEndDate(SDF_YMD.parse(lessonInfo.getKbsqJssj()));
        shiftInfo.setUpdateDate(SDF_YMDHMS.parse(APIUtil.now()));
        shiftInfo.setCourseHours(lessonInfo.getKbsqXs());
        shiftInfo.setCourseName(lessonInfo.getKbsqKcmc());
        shiftInfo.setWorkType(lessonInfo.getKbsqZygz());
        return shiftInfo;
    }

    /**
     * 将RequestBody中Map对象的值转为java对象
     *
     * @param json
     * @param clazz
     * @return
     */
    private <T> T json2Object(JSONObject json, Class<T> clazz) {
        Object obj = JSONObject.toBean(json, clazz);
        return (T) obj;
    }

    /**
     * 将RequestBody中Map对象的值转为java集合对象
     *
     * @param json
     * @param clazz
     * @return
     */
    private <T> List<T> json2List(JSONArray json, Class<T> clazz) {
        Object objArr = JSONArray.toArray(json, clazz);
        return Arrays.asList((T[]) objArr);
    }
}
