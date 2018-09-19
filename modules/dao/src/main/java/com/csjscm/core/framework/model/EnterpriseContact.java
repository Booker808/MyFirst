package com.csjscm.core.framework.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class EnterpriseContact {
    private Integer id;
    @NotBlank(message ="entNumber不能为空" )
    private String entNumber;
    @NotBlank(message ="name不能为空" )
    private String name;

    private String job;
    @NotNull(message ="contactType不能为空" )
    private Integer contactType;

    private Integer sex;

    private String phone;

    private String email;

    private String identityNo;

    private String identityImg0;

    private String identityImg1;

    private Date createTime;

    private Date editTime;

    private String editUser;

    private Integer isdelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntNumber() {
        return entNumber;
    }

    public void setEntNumber(String entNumber) {
        this.entNumber = entNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getContactType() {
        return contactType;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIdentityImg0() {
        return identityImg0;
    }

    public void setIdentityImg0(String identityImg0) {
        this.identityImg0 = identityImg0;
    }

    public String getIdentityImg1() {
        return identityImg1;
    }

    public void setIdentityImg1(String identityImg1) {
        this.identityImg1 = identityImg1;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}