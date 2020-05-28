package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_SHIFTINFO")
public class ShiftInfo implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "COURSEID")
    private Long courseId;//课程ID

    @Column(name = "USERNO",length = 50)
    private String userNo;//监管机构用户名

    @Column(name = "USERPWD",length = 50)
    private String userPwd;//监管机构密码

    @Column(name = "TRAININGAGENCYID")
    private Long trainingAgencyId;//培训机构ID

    @Column(name = "TRAININGAGENCYNAME",length = 50)
    private String trainingAgencyName;//培训机构名称

    @Column(name = "COURSESTARTDATE")
    private Date courseStartDate;//课程开始时间

    @Column(name = "COURSEENDDATE")
    private Date courseEndDate;//课程结束时间

    @Column(name = "COURSEHOURS")
    private Integer courseHours;//课程总学时

    @Column(name = "COURSENAME",length = 50)
    private String courseName;//课程名称

    @Column(name = "WORKTYPE",length = 50)
    private String workType;//工种名称

}
