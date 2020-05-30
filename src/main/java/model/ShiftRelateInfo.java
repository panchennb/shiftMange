package model;

public class ShiftRelateInfo {
    private String id;// 课程主键id

    private Integer isRelated;//是否关联学习计划

    private Integer isJoin;//学员是否加入学习计划

    private String studyPlanName;//关联学习计划名

    private String studyPlanId;//关联学习计划ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
