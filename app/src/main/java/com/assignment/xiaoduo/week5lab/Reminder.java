package com.assignment.xiaoduo.week5lab;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiaoduo on 4/19/15.
 */
public class Reminder implements Serializable {

    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private boolean completed;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}
