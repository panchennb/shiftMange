package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_STUDENT")
public class Student {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "STUDENTID")
    private String studentId;//学员ID

    @Column(name = "SHIFTINFOID")
    private String shiftInfoId;//课程UUID

    @Column(name = "COURSEID")
    private String courseId;//课程ID

    @Column(name = "USERNO")
    private String userNo;//登录账户

    @Column(name = "USERPASS")
    private String userPass;//登录密码

    @Column(name = "PHONE")
    private String phone;//电话号码

    @Column(name = "NAME")
    private String name;//学员姓名

    @Column(name = "IDCARD")
    private String idCard;//身份证号

    @Column(name = "CREATEDATE")
    private Date createDate;//创建时间

    @Column(name = "UPDATEDATE")
    private Date updateDate;//更新时间

    @Column(name = "ISJOIN")
    private Integer isJoin=0;//是否加入学习计划

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getShiftInfoId() {
        return shiftInfoId;
    }

    public void setShiftInfoId(String shiftInfoId) {
        this.shiftInfoId = shiftInfoId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Integer isJoin) {
        this.isJoin = isJoin;
    }
}
