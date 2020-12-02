package com.lib.showfield.http.respones.live;

public class DictDetailBean {


    /**
     * key : 100100
     * val : 政治谣言
     */

    private String key;
    private String val;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
