package com.cjlu.entity;
import java.util.Date;


public class JobApplication {
    private Integer applicationId;
    private Integer studentId;
    private Integer jobId;
    private Date applyTime;
    private String availTimes;
    private String reason;
    private Integer status;

    //getter//setter


    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getAvailTimes() {
        return availTimes;
    }

    public void setAvailTimes(String availTimes) {
        this.availTimes = availTimes;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}