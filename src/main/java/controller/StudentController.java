package controller;

import Service.ShiftInterface;
import Service.StudentInterface;
import model.ShiftInfo;
import model.Student;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(ShiftController.class);

    @Autowired
    private StudentInterface studentInterface;

    @Autowired
    private ShiftInterface shiftInterface;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 开班信息详情
     * @param courseId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value="/showStudent",method = RequestMethod.POST)
    @ResponseBody
    public HashMap showStudent(@RequestParam String name,Long studentId, Long courseId, Integer page, Integer rows){
        log.info("showStudent ====={} {}",name,studentId);
        HashMap map = new HashMap();
        Sort sort = Sort.by(Sort.Direction.ASC,"studentId");
        ShiftInfo shift = shiftInterface.findByCourseId(courseId);
        StringBuffer sql = new StringBuffer("select * from t_student where name like CONCAT('%',?1,'%') ");
        if(studentId != null){
            sql.append(" and studentid = "+studentId);
        }
        Query nativeQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        nativeQuery.setParameter(1,name);
        nativeQuery.setFirstResult((page-1)*rows);
        nativeQuery.setMaxResults(rows);
        List studentList = nativeQuery.getResultList();
//        List<Student> studentList = studentInterface.findByCourseId(courseId,PageRequest.of(page-1,rows,sort));
        map.put("shift",shift);
        map.put("studentList",studentList);
        return map;
    }



}
