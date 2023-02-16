package com.tools.notes.services;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.tools.notes.model.Things;

import java.sql.SQLException;
import java.util.List;

public class ThingsService {
    //增
    public int insert(Things things) throws SQLException {
        return Db.use().execute("INSERT INTO Things(date,things) VALUES(?,?)",things.getDate(),things.getThings());
    }
    //删
    public int delete(Things things) throws SQLException {
        return Db.use().execute("DELETE FROM Things WHERE things=?",things.getThings());
    }
    //改
    public int update(Things things) throws SQLException {
        return Db.use().execute("UPDATE Things SET date=? AND things=?",things.getDate(),things.getThings());
    }
    //查
    public List<Entity> queryFromDate(Things things, Things things1) throws SQLException {
        return Db.use().query("SELECT * FROM Things WHERE date BETWEEN ? AND ?",things.getDate(),things1.getDate());
    }
    public List<Entity> queryFromThings(Things things) throws SQLException {
        return Db.use().query("SELECT * FROM Things WHERE things=?",things.getThings());
    }
}
