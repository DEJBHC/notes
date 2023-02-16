package com.tools.notes.model;

import cn.hutool.core.date.DateUtil;

import java.sql.Timestamp;
import java.util.Date;


public class Things {
    private int id;

    public Things() {
    }

    @Override
    public String toString() {
        return "Things{" +
                "id=" + id +
                ", dateUtil=" + date +
                ", things='" + things + '\'' +
                '}';
    }

    public Things(int id, Timestamp date, String things) {
        this.id = id;
        this.date = date;
        this.things = things;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Things(Timestamp date) {
        this.date = date;
    }

    public Things(String things) {
        this.things = things;
    }

    public String getThings() {
        return things;
    }

    public void setThings(String things) {
        this.things = things;
    }

    private Timestamp date;
    private String things;
}
