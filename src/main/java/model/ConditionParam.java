package model;

public class ConditionParam {
    private String courseName;
    private Integer isRelated;
    private Integer isJoin;
    private String trainingAgencyName;
    private Long trainingAgencyId;

    private String name;
    private String id;

    private String sortName;
    private String sortOrder;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "ConditionParam{" +
                "courseName='" + courseName + '\'' +
                ", isRelated=" + isRelated +
                ", isJoin=" + isJoin +
                ", trainingAgencyName='" + trainingAgencyName + '\'' +
                ", trainingAgencyId=" + trainingAgencyId +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", sortName='" + sortName + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
