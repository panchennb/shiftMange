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

    /**
     * 开班信息列表页
     * @return
     */
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

    /**
     * 开班信息列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value="/showShift", method = RequestMethod.POST)
    @ResponseBody
    public List showShift(@RequestParam Integer isRelated,Integer isJoin,Long trainingAgencyId,String courseName, Integer page,Integer rows){
        log.info("showShift {} {}",page,rows);
        StringBuffer sql = new StringBuffer("select * from t_shiftinfo where courseName like CONCAT('%',?1,'%') ");
        if(isRelated != null){
            sql.append(" and isrelated = "+isRelated);
        }
        if(isJoin != null){
            sql.append(" and isjoin = "+isJoin);
        }
        if(trainingAgencyId != null){
            sql.append(" and trainingagencyid = "+trainingAgencyId);
        }
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1,courseName);
        nativeQuery.setFirstResult((page-1)*rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
//        Sort sort = Sort.by(Sort.Direction.ASC,"courseId");
//        Page<ShiftInfo> shiftInfos = shiftInterface.findAll(PageRequest.of(page-1,rows,sort));
        return resultList;
    }

    /**
     * 培训机构开班统计信息
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/showTrainingAgency")
    @ResponseBody
    public List showTrainingAgency(@RequestParam Long trainingAgencyId,String trainingAgencyName, Integer page,Integer rows){
        log.info("showTrainingAgency=== {} {}",trainingAgencyId,trainingAgencyName);
        StringBuffer sql = new StringBuffer("select trainingagencyid,trainingagencyname,count(*) as totalCourse," +
                "MAX(coursestartdate) as lastStartDate,sum(coursehours) as totalHours," +
                "(select count(*) from t_student b where b.trainingagencyid = a.trainingagencyid) as totalStudent " +
                "from t_shiftinfo a where a.trainingAgencyName like CONCAT('%',?1,'%')");
        if(trainingAgencyId != null){
            sql.append(" and a.trainingAgencyId = "+trainingAgencyId);
        }
        sql.append(" group by a.trainingagencyid");
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1,trainingAgencyName);
        nativeQuery.setFirstResult((page-1)*rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
        return resultList;
    }

    /**
     * 查询机构开班明细
     * @param trainingagencyId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/showTrainingData")
    @ResponseBody
    public List showTrainingData(@RequestParam Long trainingagencyId,String courseName,Integer page,Integer rows){
        log.info("showTrainingAgency=== {} {}",trainingagencyId,courseName);
        StringBuffer sql = new StringBuffer("select coursename,coursestartdate,courseenddate,coursehours,worktype," +
                "(select COUNT(*) from t_student b where a.courseid = b.courseid) as studentNum,studyPlanName " +
                "from t_shiftinfo a where a.trainingagencyid = ?1 and courseName like CONCAT('%',?2,'%')");
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1,trainingagencyId);
        nativeQuery.setParameter(2,courseName);
        nativeQuery.setFirstResult((page-1)*rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
        return resultList;
    }

}
