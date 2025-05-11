package com.cjlu.entity;

public class Student {
    private Integer id;
    private Integer gender;
    private String className;
    private String level;
    private String skill;
    private String situation;
    private Integer difficult_student;
    private Integer loan;

    // Getter/Setter


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Integer getDifficult_student() {
        return difficult_student;
    }

    public void setDifficult_student(Integer difficult_student) {
        this.difficult_student = difficult_student;
    }

    public Integer getLoan() {
        return loan;
    }

    public void setLoan(Integer loan) {
        this.loan = loan;
    }
}

