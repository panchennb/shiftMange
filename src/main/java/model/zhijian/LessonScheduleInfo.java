package model.zhijian;

/**
 * 课表信息
 */
public class LessonScheduleInfo {

    private String dsptZjszId;//章标号（节对应的章编号，章时传本章编号）

    private Integer dsptBh;//标号

    private String dsptZjszName;//章节名称

    private String dsptZjszLevel;//章/节类型标识（1 章节 2 小节）

    private Integer ext1;//学时（章学时必须等于其下小节的学时和；所有小节的学时和必须大于等于开班信息接口中的kbsqXs）

    public String getDsptZjszId() {
        return dsptZjszId;
    }

    public void setDsptZjszId(String dsptZjszId) {
        this.dsptZjszId = dsptZjszId;
    }

    public Integer getDsptBh() {
        return dsptBh;
    }

    public void setDsptBh(Integer dsptBh) {
        this.dsptBh = dsptBh;
    }

    public String getDsptZjszName() {
        return dsptZjszName;
    }

    public void setDsptZjszName(String dsptZjszName) {
        this.dsptZjszName = dsptZjszName;
    }

    public String getDsptZjszLevel() {
        return dsptZjszLevel;
    }

    public void setDsptZjszLevel(String dsptZjszLevel) {
        this.dsptZjszLevel = dsptZjszLevel;
    }

    public Integer getExt1() {
        return ext1;
    }

    public void setExt1(Integer ext1) {
        this.ext1 = ext1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonScheduleInfo that = (LessonScheduleInfo) o;

        if (dsptZjszId != null ? !dsptZjszId.equals(that.dsptZjszId) : that.dsptZjszId != null) return false;
        if (dsptBh != null ? !dsptBh.equals(that.dsptBh) : that.dsptBh != null) return false;
        if (dsptZjszName != null ? !dsptZjszName.equals(that.dsptZjszName) : that.dsptZjszName != null) return false;
        if (dsptZjszLevel != null ? !dsptZjszLevel.equals(that.dsptZjszLevel) : that.dsptZjszLevel != null)
            return false;
        return ext1 != null ? ext1.equals(that.ext1) : that.ext1 == null;
    }

    @Override
    public int hashCode() {
        int result = dsptZjszId != null ? dsptZjszId.hashCode() : 0;
        result = 31 * result + (dsptBh != null ? dsptBh.hashCode() : 0);
        result = 31 * result + (dsptZjszName != null ? dsptZjszName.hashCode() : 0);
        result = 31 * result + (dsptZjszLevel != null ? dsptZjszLevel.hashCode() : 0);
        result = 31 * result + (ext1 != null ? ext1.hashCode() : 0);
        return result;
    }
}
