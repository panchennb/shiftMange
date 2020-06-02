package controller;

import Service.ShiftInterface;
import model.ConditionParam;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
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
     * 开班信息列表
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/showshift", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public Object showShift(@RequestBody ConditionParam param, int page, int rows) {
        log.info("showShift {} {},param:{}", page, rows, param);
        HashMap map = new HashMap();
        StringBuffer sql = new StringBuffer("select * from t_shiftinfo where courseName like CONCAT('%',?1,'%') ");
        if (param.getIsRelated() != null) {
            sql.append(" and isrelated = " + param.getIsRelated());
        }
        if (param.getIsJoin() != null) {
            sql.append(" and isjoin = " + param.getIsJoin());
        }
        if (param.getTrainingAgencyId() != null) {
            sql.append(" and trainingagencyid = " + param.getTrainingAgencyId());
        }
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1, StringUtils.isBlank(param.getCourseName()) ? "" : param.getCourseName());
        nativeQuery.setFirstResult((page - 1) * rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
        map.put("resultList", resultList);
        map.put("totalNum", resultList.size());
        return map;
    }

    /**
     * 开班信息列表
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/showShift", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public HashMap showShift(@RequestParam Integer isRelated, Integer isJoin, Long trainingAgencyId, String courseName, Integer page, Integer rows) {
        log.info("showShift {} {}", page, rows);
        HashMap map = new HashMap();
        StringBuffer sql = new StringBuffer("select * from t_shiftinfo where courseName like CONCAT('%',?1,'%') ");
        if (isRelated != null) {
            sql.append(" and isrelated = " + isRelated);
        }
        if (isJoin != null) {
            sql.append(" and isjoin = " + isJoin);
        }
        if (trainingAgencyId != null) {
            sql.append(" and trainingagencyid = " + trainingAgencyId);
        }
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1, courseName);
        nativeQuery.setFirstResult((page - 1) * rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
        map.put("resultList", resultList);
        map.put("totalNum", resultList.size());
//        Sort sort = Sort.by(Sort.Direction.ASC,"courseId");
//        Page<ShiftInfo> shiftInfos = shiftInterface.findAll(PageRequest.of(page-1,rows,sort));
        return map;
    }

    /**
     * 培训机构开班统计
     *
     * @param param
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/showtrainingagency")
    @ResponseBody
    @CrossOrigin
    public Object showTrainingAgency(@RequestBody ConditionParam param, int page, int rows) {
        log.info("showTrainingAgency {} {},param:{}", page, rows, param);
        HashMap map = new HashMap();
        StringBuffer sql = new StringBuffer("select trainingagencyid,trainingagencyname,count(*) as totalCourse," +
                "MAX(coursestartdate) as lastStartDate,sum(coursehours) as totalHours," +
                "(select count(*) from t_student b where b.trainingagencyid = a.trainingagencyid) as totalStudent " +
                "from t_shiftinfo a where a.trainingAgencyName like CONCAT('%',?1,'%')");
        sql.append(" group by a.trainingagencyid");
        if (StringUtils.isNotBlank(param.getSortName()) && StringUtils.isNotBlank(param.getSortOrder())) {
            sql.append(" order by " + param.getSortName() + " " + param.getSortOrder());
        } else {
            sql.append(" order by totalCourse DESC");
        }
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1, StringUtils.isBlank(param.getTrainingAgencyName()) ? "" : param.getTrainingAgencyName());
        nativeQuery.setFirstResult((page - 1) * rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
        map.put("resultList", resultList);
        map.put("totalNum", resultList.size());
        return map;
    }

    /**
     * 培训机构开班统计信息
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/showTrainingAgency")
    @ResponseBody
    @CrossOrigin
    public HashMap showTrainingAgency(@RequestParam Long trainingAgencyId, String trainingAgencyName, Integer page, Integer rows, String sortName, String sortOrder) {
        log.info("showTrainingAgency=== {} {} {} {}", trainingAgencyId, trainingAgencyName, sortName, sortOrder);
        HashMap map = new HashMap();
        StringBuffer sql = new StringBuffer("select trainingagencyid,trainingagencyname,count(*) as totalCourse," +
                "MAX(coursestartdate) as lastStartDate,sum(coursehours) as totalHours," +
                "(select count(*) from t_student b where b.trainingagencyid = a.trainingagencyid) as totalStudent " +
                "from t_shiftinfo a where a.trainingAgencyName like CONCAT('%',?1,'%')");
        if (trainingAgencyId != null) {
            sql.append(" and a.trainingAgencyId = " + trainingAgencyId);
        }
        sql.append(" group by a.trainingagencyid");
        if (StringUtils.isNotBlank(sortName)) {
            sql.append(" order by " + sortName + " " + sortOrder);
        } else {
            sql.append(" order by totalCourse DESC");
        }
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1, trainingAgencyName);
        nativeQuery.setFirstResult((page - 1) * rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
        map.put("resultList", resultList);
        map.put("totalNum", resultList.size());
        return map;
    }

    /**
     * 查询机构开班明细
     *
     * @param param
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/showtrainingdata")
    @ResponseBody
    @CrossOrigin
    public Object showTrainingData(@RequestBody ConditionParam param, int page, int rows) {
        log.info("showTrainingData {} {},param:{}", page, rows, param);
        HashMap map = new HashMap();
        StringBuffer sql = new StringBuffer("select coursename,coursestartdate,courseenddate,coursehours,worktype," +
                "(select COUNT(*) from t_student b where a.courseid = b.courseid) as studentNum,studyPlanName " +
                "from t_shiftinfo a where a.trainingagencyid = ?1 and courseName like CONCAT('%',?2,'%')");
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1, param.getTrainingAgencyId());
        nativeQuery.setParameter(2, StringUtils.isBlank(param.getCourseName()) ? "" : param.getCourseName());
        nativeQuery.setFirstResult((page - 1) * rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
        map.put("resultList", resultList);
        map.put("totalNum", resultList.size());
        return map;
    }

    /**
     * 查询机构开班明细
     *
     * @param trainingagencyId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/showTrainingData")
    @ResponseBody
    @CrossOrigin
    public HashMap showTrainingData(@RequestParam Long trainingagencyId, String courseName, Integer page, Integer rows) {
        log.info("showTrainingAgency=== {} {}", trainingagencyId, courseName);
        HashMap map = new HashMap();
        StringBuffer sql = new StringBuffer("select coursename,coursestartdate,courseenddate,coursehours,worktype," +
                "(select COUNT(*) from t_student b where a.courseid = b.courseid) as studentNum,studyPlanName " +
                "from t_shiftinfo a where a.trainingagencyid = ?1 and courseName like CONCAT('%',?2,'%')");
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1, trainingagencyId);
        nativeQuery.setParameter(2, courseName);
        nativeQuery.setFirstResult((page - 1) * rows);
        nativeQuery.setMaxResults(rows);
        List resultList = nativeQuery.getResultList();
        map.put("resultList", resultList);
        map.put("totalNum", resultList.size());
        return map;
    }

    /**
     * 培训机构筛选下拉框
     *
     * @return
     */
    @RequestMapping("/gettrainingagency")
    @ResponseBody
    @CrossOrigin
    public Object getTrainingAgency() {
        log.info("showTrainingData");
        StringBuffer sql = new StringBuffer("select distinct trainingagencyname,trainingagencyid from t_shiftinfo");
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List resultList = nativeQuery.getResultList();
        return resultList;
    }
}
