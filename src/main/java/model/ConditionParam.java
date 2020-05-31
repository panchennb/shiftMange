package model;

public class ConditionParam {
    private String courseName;
    private Integer isRelated;
    private Integer isJoin;
    private String trainingAgencyName;
    private Long trainingAgencyId;

    public String getCourseName() {
        return courseName;
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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public String getTrainingAgencyName() {
        return trainingAgencyName;
    }

    public void setTrainingAgencyName(String trainingAgencyName) {
        this.trainingAgencyName = trainingAgencyName;
    }

    public Long getTrainingAgencyId() {
        return trainingAgencyId;
    }

    public void setTrainingAgencyId(Long trainingAgencyId) {
        this.trainingAgencyId = trainingAgencyId;
    }
}
