package com.lib.showfield.bean;

/**
 - @Description: 
 - @Author:
 - @Time:  2020-05-22 09:56
 */
public class MQTTBean {

    /**
     * cmd : CHAT_ROOM_CLOSE
     * text : {"message":"聊天室已关播～"}
     */

    private String cmd;
    private String text;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MQTTBean{" +
                "cmd='" + cmd + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
