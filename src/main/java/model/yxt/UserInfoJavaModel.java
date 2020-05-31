package model.yxt;

public class UserInfoJavaModel {

    /**
     * 用户ID(同步必传)
     */
    private String id;

    /**
     * 用户名(同步必传)
     */
    private String userName;

    /**
     * 中文姓名(同步必传)
     */
    private String cnName;

    /**
     * 工号
     */
    private String userNo;

    /**
     * 密码备注：如果用MD5或者CMD5加密则必须使用标准MD5 32位小写加密的字符串（如果不传使用平台配置的默认密码）
     */
    private String password;

    /**
     * 密码加密方式： YXT(云学堂加密默认)、MD5 (密码MD5加密)、CMD5(用户名+密码MD5加密)
     */
    private String encryptionType;

    /**
     * 性别
     */
    private String sex;

    /**
     * 移动电话
     */
    private String mobile;

    /**
     * 电子邮件
     */
    private String mail;

    /**
     * 部门编号
     */
    private String orgOuCode;

    /**
     * 部门名称
     */
    private String orgOuName;

    /**
     * 岗位编号
     */
    private String postionNo;

    /**
     * 岗位名
     */
    private String postionName;

    /**
     * 入职日期
     */
    private String entrytime;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 过期日期
     */
    private String expiredDate;

    /**
     * 用户状态
     */
    private String status;

    /**
     * 用户删除状态
     */
    private String deleteStatus;

    /**
     * 是否是部门主管
     */
    private int isManager = 0;

    /**
     * 直属经理
     */
    private String managerNo;

    /**
     * 职级
     */
    private String gradeName;

    /**
     * 是否置空邮箱、手机号
     */
    private int isUpdateNull = 0;

    /**
     * 是否需要绑定手机（0：不需要，1：需要） 默认设置为自动绑定手机号 如果不需要绑定可单独将该字段设置为0
     */
    private int isMobileValidated = 1;

    /**
     * 是否需要绑定邮箱（0：不需要，1：需要） 默认设置为自动绑定邮箱 如果不需要绑定可单独将该字段设置为0
     */
    private int isEmailValidated = 1;

    /**
     * 扩展字段 1~10
     */
    private String spare1;

    private String spare2;

    private String spare3;

    private String spare4;

    private String spare5;

    private String spare6;

    private String spare7;

    private String spare8;

    private String spare9;

    private String spare10;

    private String isBindWXQY;

    private String wXQYOpenID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getOrgOuCode() {
        return orgOuCode;
    }

    public void setOrgOuCode(String orgOuCode) {
        this.orgOuCode = orgOuCode;
    }

    public String getOrgOuName() {
        return orgOuName;
    }

    public void setOrgOuName(String orgOuName) {
        this.orgOuName = orgOuName;
    }

    public String getPostionNo() {
        return postionNo;
    }

    public void setPostionNo(String postionNo) {
        this.postionNo = postionNo;
    }

    public String getPostionName() {
        return postionName;
    }

    public void setPostionName(String postionName) {
        this.postionName = postionName;
    }

    public String getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(String entrytime) {
        this.entrytime = entrytime;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public String getManagerNo() {
        return managerNo;
    }

    public void setManagerNo(String managerNo) {
        this.managerNo = managerNo;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getIsUpdateNull() {
        return isUpdateNull;
    }

    public void setIsUpdateNull(int isUpdateNull) {
        this.isUpdateNull = isUpdateNull;
    }

    public int getIsMobileValidated() {
        return isMobileValidated;
    }

    public void setIsMobileValidated(int isMobileValidated) {
        this.isMobileValidated = isMobileValidated;
    }

    public int getIsEmailValidated() {
        return isEmailValidated;
    }

    public void setIsEmailValidated(int isEmailValidated) {
        this.isEmailValidated = isEmailValidated;
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1;
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2;
    }

    public String getSpare3() {
        return spare3;
    }

    public void setSpare3(String spare3) {
        this.spare3 = spare3;
    }

    public String getSpare4() {
        return spare4;
    }

    public void setSpare4(String spare4) {
        this.spare4 = spare4;
    }

    public String getSpare5() {
        return spare5;
    }

    public void setSpare5(String spare5) {
        this.spare5 = spare5;
    }

    public String getSpare6() {
        return spare6;
    }

    public void setSpare6(String spare6) {
        this.spare6 = spare6;
    }

    public String getSpare7() {
        return spare7;
    }

    public void setSpare7(String spare7) {
        this.spare7 = spare7;
    }

    public String getSpare8() {
        return spare8;
    }

    public void setSpare8(String spare8) {
        this.spare8 = spare8;
    }

    public String getSpare9() {
        return spare9;
    }

    public void setSpare9(String spare9) {
        this.spare9 = spare9;
    }

    public String getSpare10() {
        return spare10;
    }

    public void setSpare10(String spare10) {
        this.spare10 = spare10;
    }

    public String getIsBindWXQY() {
        return isBindWXQY;
    }

    public void setIsBindWXQY(String isBindWXQY) {
        this.isBindWXQY = isBindWXQY;
    }

    public String getwXQYOpenID() {
        return wXQYOpenID;
    }

    public void setwXQYOpenID(String wXQYOpenID) {
        this.wXQYOpenID = wXQYOpenID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserInfoJavaModel other = (UserInfoJavaModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}

