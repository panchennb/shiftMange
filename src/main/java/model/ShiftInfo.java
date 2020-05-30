package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "T_SHIFTINFO")
public class ShiftInfo {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "COURSEID")
    private String courseId;//课程ID

    @Column(name = "CHARGEUSERNO")
    private String chargeUserNo;//监管机构用户名

    @Column(name = "CHARGEUSERPWD")
    private String chargeUserPwd;//监管机构密码

    @Column(name = "TRAININGAGENCYID")
    private String trainingAgencyId;//培训机构ID

    @Column(name = "TRAININGAGENCYNAME")
    private String trainingAgencyName;//培训机构名称

    @Column(name = "COURSESTARTDATE")
    private String courseStartDate;//课程开始时间

    @Column(name = "COURSEENDDATE")
    private String courseEndDate;//课程结束时间

    @Column(name = "COURSEHOURS")
    private Integer courseHours;//课程总学时

    @Column(name = "COURSENAME")
    private String courseName;//课程名称

    @Column(name = "WORKTYPE")
    private String workType;//工种名称

    @Column(name = "CREATEDATE")
    private String createDate;//创建时间

    @Column(name = "UPDATEDATE")
    private String updateDate;//更新时间

    @Column(name = "ISRELATED")
    private Integer isRelated = 0;//是否关联学习计划

    @Column(name = "ISJOIN")
    private Integer isJoin = 0;//学员是否加入学习计划

    @Column(name = "STUDYPLANNAME")
    private String studyPlanName;//关联学习计划名

    @Column(name = "STUDYPLANID")
    private String studyPlanId;//关联学习计划ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChargeUserNo() {
        return chargeUserNo;
    }

    public void setChargeUserNo(String chargeUserNo) {
        this.chargeUserNo = chargeUserNo;
    }

    public String getChargeUserPwd() {
        return chargeUserPwd;
    }

    public void setChargeUserPwd(String chargeUserPwd) {
        this.chargeUserPwd = chargeUserPwd;
    }

    public String getTrainingAgencyId() {
        return trainingAgencyId;
    }

    public void setTrainingAgencyId(String trainingAgencyId) {
        this.trainingAgencyId = trainingAgencyId;
    }

    public String getTrainingAgencyName() {
        return trainingAgencyName;
    }

    public void setTrainingAgencyName(String trainingAgencyName) {
        this.trainingAgencyName = trainingAgencyName;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public Integer getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(Integer courseHours) {
        this.courseHours = courseHours;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIsRelated() {
        return isRelated;
    }

    public void setIsRelated(Integer isRelated) {
        this.isRelated = isRelated;
    }

    public Integer getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Integer isJoin) {
        this.isJoin = isJoin;
    }

    public String getStudyPlanName() {
        return studyPlanName;
    }

    public void setStudyPlanName(String studyPlanName) {
        this.studyPlanName = studyPlanName;
    }

    public String getStudyPlanId() {
        return studyPlanId;
    }

    public void setStudyPlanId(String studyPlanId) {
        this.studyPlanId = studyPlanId;
    }
}
