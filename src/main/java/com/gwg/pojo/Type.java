package com.gwg.pojo;

import java.io.Serializable;
import java.util.List;

public class Type implements Serializable {

    private Integer id;
    private String name;

    private List<Blog> blog_list;

    public Type() {
    }

    public Type(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Blog> getBlog_list() {
        return blog_list;
    }

    public void setBlog_list(List<Blog> blog_list) {
        this.blog_list = blog_list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
