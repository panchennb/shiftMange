package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_STUDENT")
public class Student implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "STUDENTID")
    private Long studentId;//学员ID

    @Column(name = "COURSEID")
    private Long courseId;//课程ID

    @Column(name = "USERNO",length = 50)
    private String userNo;//登录账户

    @Column(name = "USERPASS",length = 50)
    private String userPass;//登录密码

    @Column(name = "PHONE",length = 50)
    private String phone;//电话号码

    @Column(name = "NAME",length = 50)
    private String name;//学员姓名

    @Column(name = "IDCARD",length = 50)
    private String IdCard;//身份证号
}
