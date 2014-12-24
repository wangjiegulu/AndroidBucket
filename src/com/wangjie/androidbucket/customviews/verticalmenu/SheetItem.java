package com.wangjie.androidbucket.customviews.verticalmenu;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/10/14.
 */
public class SheetItem implements Serializable {
    private String title;
    private int textColor = Color.parseColor("#2198C8");
    private int textSizeSp = 18;
    private int action;

    @Override
    public String toString() {
        return "SheetItem{" +
                "title='" + title + '\'' +
                ", textColor=" + textColor +
                ", textSizeSp=" + textSizeSp +
                ", action=" + action +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public SheetItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getTextColor() {
        return textColor;
    }

    public SheetItem setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public int getTextSizeSp() {
        return textSizeSp;
    }

    public SheetItem setTextSizeSp(int textSizeSp) {
        this.textSizeSp = textSizeSp;
        return this;
    }

    public int getAction() {
        return action;
    }

    public SheetItem setAction(int action) {
        this.action = action;
        return this;
    }
}
