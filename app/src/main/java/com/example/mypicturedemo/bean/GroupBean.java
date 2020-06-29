package com.example.mypicturedemo.bean;

import java.util.ArrayList;

/**
 * 组数据的实体类
 */
public class GroupBean {

    private String header;
    private String footer;
    private ArrayList<ChildBean> children;
    private boolean isExpand;

    public GroupBean(String header, String footer, ArrayList<ChildBean> children) {
        this.header = header;
        this.footer = footer;
        this.children = children;
    }

    public GroupBean(String header, String footer, ArrayList<ChildBean> children, boolean isExpand) {
        this.header = header;
        this.footer = footer;
        this.children = children;
        this.isExpand = isExpand;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public ArrayList<ChildBean> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ChildBean> children) {
        this.children = children;
    }


    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

}
