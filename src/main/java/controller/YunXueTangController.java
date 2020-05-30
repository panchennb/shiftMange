package controller;

import Service.ShiftInterface;
import model.ShiftInfo;
import model.ShiftRelateInfo;
import net.sf.json.JSONObject;
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
import java.util.Map;

@Controller
@RequestMapping("yxt")
public class YunXueTangController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YunXueTangController.class);

    private static final SimpleDateFormat SDF_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ShiftInterface shiftInterface;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object setPersonalPlan(HttpServletRequest request, @RequestBody ShiftRelateInfo param) {
        int code = 1;
        String msg = "";

        String id = param.getId();
        ShiftInfo shift = shiftInterface.findById(id);
        ShiftInfo data = null;
        try {
            shift.setUpdateDate(SDF_YMDHMS.parse(APIUtil.now()));
            shift.setStudyPlanId(param.getStudyPlanId());
            shift.setStudyPlanName(param.getStudyPlanName());
            shift.setIsJoin(param.getIsJoin());
            shift.setIsRelated(param.getIsRelated());
            data = shiftInterface.save(shift);
        } catch (ParseException e) {
            LOGGER.error(e.toString());
            code = 0;
            msg = e.getMessage();
        }
        Map<String,Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
