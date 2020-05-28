package controller;

import Service.ShiftInterface;
import model.ShiftInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShiftController {
    private static final Logger log = LoggerFactory.getLogger(ShiftController.class);

    @Autowired
    private ShiftInterface shiftInterface;

    @RequestMapping("/showShift")
    public String showShift() {
        //展示开班信息
        List<ShiftInfo> shiftInfoList = shiftInterface.findAll();
        log.info("====="+shiftInfoList.size());

        return "改一下111";
    }
}
