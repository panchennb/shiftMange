package model.zhijian;

/**
 * 课程信息
 */
public class LessonInfo {

    private Long kbsqId;//课程ID

    private String userNo;//监管机构用户名

    private String userPwd;//监管机构密码

    private Long kbsqJgid;//培训机构ID

    private String jgglName;//培训机构名称

    private String kbsqKssj;//课程开始时间

    private String kbsqJssj;//课程结束时间

    private Integer kbsqXs;//课程总学时

    private String kbsqKcmc;//课程名称

    private String kbsqZygz;//工种名称

    public Long getKbsqId() {
        return kbsqId;
    }

    public void setKbsqId(Long kbsqId) {
        this.kbsqId = kbsqId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Long getKbsqJgid() {
        return kbsqJgid;
    }

    public void setKbsqJgid(Long kbsqJgid) {
        this.kbsqJgid = kbsqJgid;
    }

    public String getJgglName() {
        return jgglName;
    }

    public void setJgglName(String jgglName) {
        this.jgglName = jgglName;
    }

    public String getKbsqKssj() {
        return kbsqKssj;
    }

    public void setKbsqKssj(String kbsqKssj) {
        this.kbsqKssj = kbsqKssj;
    }

    public String getKbsqJssj() {
        return kbsqJssj;
    }

    public void setKbsqJssj(String kbsqJssj) {
        this.kbsqJssj = kbsqJssj;
    }

    public Integer getKbsqXs() {
        return kbsqXs;
    }

    public void setKbsqXs(Integer kbsqXs) {
        this.kbsqXs = kbsqXs;
    }

    public String getKbsqKcmc() {
        return kbsqKcmc;
    }

    public void setKbsqKcmc(String kbsqKcmc) {
        this.kbsqKcmc = kbsqKcmc;
    }

    public String getKbsqZygz() {
        return kbsqZygz;
    }

    public void setKbsqZygz(String kbsqZygz) {
        this.kbsqZygz = kbsqZygz;
    }
}
