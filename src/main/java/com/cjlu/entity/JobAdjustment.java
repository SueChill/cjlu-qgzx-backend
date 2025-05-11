package com.cjlu.entity;

public class JobAdjustment {
    private Integer adjust_id;
    private Integer student_id;
    private Integer original_job_id;
    private Integer new_job_id;
    private Status status;

    public enum Status {
        已确认,
        待处理,
        已完成
    }

    //getter//setter


    public Integer getAdjust_id() {
        return adjust_id;
    }

    public void setAdjust_id(Integer adjust_id) {
        this.adjust_id = adjust_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getOriginal_job_id() {
        return original_job_id;
    }

    public void setOriginal_job_id(Integer original_job_id) {
        this.original_job_id = original_job_id;
    }

    public Integer getNew_job_id() {
        return new_job_id;
    }

    public void setNew_job_id(Integer new_job_id) {
        this.new_job_id = new_job_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
