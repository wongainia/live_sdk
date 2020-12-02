package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020/6/22 21:15
 * <p>
 * Description
 **/
public class ReportParam {

    private String roomNo;
    private String reportType;
    private String reason;
    private int reportSource;
    private int reportedUserId;

    public ReportParam(String roomNo, String reportType,
                       String reason, int reportSource, int reportedUserId) {
        this.roomNo = roomNo;
        this.reportType = reportType;
        this.reason = reason;
        this.reportSource = reportSource;
        this.reportedUserId = reportedUserId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getReportSource() {
        return reportSource;
    }

    public void setReportSource(int reportSource) {
        this.reportSource = reportSource;
    }

    public int getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(int reportedUserId) {
        this.reportedUserId = reportedUserId;
    }
}
