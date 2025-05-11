package com.cjlu.dto;

/*
* 这里是岗位申请的传输
*
* */
public class JobApplicationDTO {
    private Integer studentId;
    private Integer jobId;
    private String availTimes;
    private String reason;

    // getters/setters


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
}