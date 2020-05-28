package controller;

import Service.ShiftInterface;
import com.fasterxml.jackson.databind.util.JSONPObject;
import model.ShiftInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class ShiftController {
    private static final Logger log = LoggerFactory.getLogger(ShiftController.class);

    @Autowired
    private ShiftInterface shiftInterface;

    @RequestMapping("/index")
    public String index() {

        return "index";
    }

    @RequestMapping("/showShift")
    @ResponseBody
    public List showShift(){
        //显示开班信息
        List<ShiftInfo> shiftInfoList = shiftInterface.findAll();
        return shiftInfoList;
    }

}
