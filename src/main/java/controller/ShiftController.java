package controller;

import Service.ShiftInterface;
import model.ShiftInfo;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Controller
@RequestMapping("/shift")
public class ShiftController {
    private static final Logger log = LoggerFactory.getLogger(ShiftController.class);

    @Autowired
    private ShiftInterface shiftInterface;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("/index")
    public String index() {//开班信息列表页
        return "index";
    }

    /**
     * //培训机构开班信息统计页
     * @return
     */
    @RequestMapping("/statistics")
    public String statistics() {
        return "statistics";
    }

    @RequestMapping(value="/showShift", method = RequestMethod.POST)
    @ResponseBody
    public Page showShift(@RequestParam Integer page,Integer rows){
        log.info("showShift {} {}",page,rows);
        //显示开班信息
        Sort sort = Sort.by(Sort.Direction.ASC,"courseId");
        Page<ShiftInfo> shiftInfos = shiftInterface.findAll(PageRequest.of(page-1,rows,sort));
        return shiftInfos;
    }

    /**
     * 培训机构开班统计信息
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/showTrainingAgency")
    @ResponseBody
    public List showTrainingAgency(@RequestParam Integer page,Integer rows){
        log.info("showTrainingAgency=== {} {}",page,rows);
        Query nativeQuery = entityManager.createNativeQuery("select trainingagencyid,trainingagencyname,count(*) as totalCourse," +
                "MAX(coursestartdate) as lastStartDate,sum(coursehours) as totalHours,\n" +
                "(select count(*) from t_student b where b.trainingagencyid = a.trainingagencyid) as totalStudent \n" +
                "from t_shiftinfo a\n" +
                "group by a.trainingagencyid").unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setFirstResult((page-1)*rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
        return resultList;
    }

}
