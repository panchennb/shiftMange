package controller;

import Service.ShiftInterface;
import Service.StudentInterface;
import model.ShiftInfo;
import model.ShiftRelateInfo;
import model.Student;
import model.yxt.ResultJavaEntity;
import model.yxt.UserInfoJavaModel;
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
import utils.QidaSHA256;
import utils.SyncDataJavaUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("yxt")
public class YunXueTangController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YunXueTangController.class);

    private static final SimpleDateFormat SDF_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String CHECK_USER_URL = "https://api-qida.yunxuetang.cn/v1/udp/sy/sv/cku";
    private static final String USER_ADD_STUDYPLAN_URL = "http://api.yunxuetang.cn/el/sty/addpersontoplan";

    private static final String URL_PREIX = "http://IP:端口/tomp";
    private static final String KBQR_URL = URL_PREIX + "/visitors/saveZjsz";

    private static final String APIKEY = "1eec01df-0e9c-4ae5-b6dc-8614fe75458c";
    private static final String SALT = "123";
    private static final String SECRETKEY = "6116b0ab-a136-474d-a7b3-d80c13c3294c";
    private static final String BASE_URL = "https://api-qida.yunxuetang.cn/v1/";

    private static final String USERNO = "";
    private static final String USERPWD = "";

    @Autowired
    private ShiftInterface shiftInterface;
    @Autowired
    private StudentInterface studentInterface;


    /**
     * 关联学习计划
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
            returnStudyPlan(shift, param);
        } catch (Exception e) {
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
     * 学员关联学习计划
     *
     * @param param
     * @return
     * @throws ParseException
     */
    private List<Student> joinStudent(ShiftRelateInfo param) throws ParseException, IOException {
        List<Student> students = studentInterface.findByShiftInfoId(param.getId());
        if (param.getIsJoin() == 1) {
            for (Student student : students) {
                student.setUpdateDate(SDF_YMDHMS.parse(APIUtil.now()));
                student.setIsJoin(param.getIsJoin());
                studentInterface.save(student);
            }
            userJoinYXTStudyPlan(students, param);
        }
        return students;
    }

    private void userJoinYXTStudyPlan(List<Student> students, ShiftRelateInfo param) throws IOException {
        // 检查账号存不存在
        List<String> userNameList = new ArrayList<>();
        String uNames = "";
        for (Student student : students) {
            userNameList.add(student.getUserNo());
            uNames += student.getUserNo() + ";";
        }
        String[] userNames = userNameList.toArray(new String[]{});
        JSONObject json = new JSONObject();
        json.put("apikey", APIKEY);
        json.put("salt", SALT);
        json.put("signature", QidaSHA256.SHA256Encrypt(SECRETKEY + SALT));
        json.put("datas", userNames);
        String userNameResult = OKHttp2Utils.postJson(CHECK_USER_URL, json.toString());
        JSONArray userNameIsNotExist = JSONObject.fromObject(JSONObject.fromObject(userNameResult).getString("data")).getJSONArray("noexisted");
        List<String> userNameList2Add = new ArrayList<>();
        for (int i = 0; i < userNameIsNotExist.size(); i++) {
            userNameList2Add.add(userNameIsNotExist.get(i).toString());
        }
        // 新增账号
        addYxtUser(students, userNameList2Add);
        // 人员添加到学习计划中
        JSONObject json2 = new JSONObject();
        json2.put("apikey", APIKEY);
        json2.put("salt", SALT);
        json2.put("signature", QidaSHA256.SHA256Encrypt(SECRETKEY + SALT));
        json2.put("ID", param.getStudyPlanId());
        json2.put("UserNames", uNames);
        OKHttp2Utils.postJson(USER_ADD_STUDYPLAN_URL, json2.toString());
    }

    private void addYxtUser(List<Student> students, List<String> userNameList2Add) {
        List<UserInfoJavaModel> users2Add = new ArrayList<UserInfoJavaModel>();
        for (Student student : students) {
            if (userNameList2Add.contains(student.getUserNo())) {
                UserInfoJavaModel user = new UserInfoJavaModel();
                user.setCnName(student.getName());
                user.setId(student.getStudentId().toString());
                user.setUserName(student.getUserNo());
                user.setMobile(student.getPhone());
                user.setSpare1(student.getIdCard());
                users2Add.add(user);
            }
        }
        try {
            if (users2Add.size() > 0) {
                ResultJavaEntity resultJavaEntity = SyncDataJavaUtil.users(1, users2Add, APIKEY, SECRETKEY, BASE_URL);
            }
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     * 3.开班确认回调接口
     *
     * @param shift
     * @param param
     */
    private void returnStudyPlan(ShiftInfo shift, ShiftRelateInfo param) {
        try {
            Object tokenReturn = ZhiJianController.getToken(USERNO, USERPWD);
            JSONObject tokenJson = JSONObject.fromObject(tokenReturn);
            String token = tokenJson.getString("data");
            String dsptKcszId = shift.getCourseId().toString();// 课程ID
            String dsptJgjg = shift.getTrainingAgencyName();// 监管机构
            List<Map<String, Object>> kbxx = new ArrayList<>();// 课表信息
            // TODO 获取计划阶段及内容
            Map<String, Object> params = new HashMap<>();
            params.put("token", token);
            params.put("dsptKcszId", dsptKcszId);
            params.put("dsptJgjg", dsptJgjg);
            params.put("kbxx", kbxx);
            String result = OKHttp2Utils.postJson(KBQR_URL, params.toString());
            LOGGER.info(result);
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }
}
