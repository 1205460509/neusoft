package com.neusoft.hit.backgroundmanagement.entities;

public class BusinessInfo {
    private String id;
    private String catagoryCode;
    private String catagory;
    private String type;
    private String tableName;
    private String tableDesc;
    private String medPh;
    private String timeColumn;
    private String staticAmount;
    private String staticWay;
    private String feeColumn;
    private String groupColumn;
    private String validFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatagoryCode() {
        return catagoryCode;
    }

    public void setCatagoryCode(String catagoryCode) {
        this.catagoryCode = catagoryCode;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getMedPh() {
        return medPh;
    }

    public void setMedPh(String medPh) {
        this.medPh = medPh;
    }

    public String getTimeColumn() {
        return timeColumn;
    }

    public void setTimeColumn(String timeColumn) {
        this.timeColumn = timeColumn;
    }

    public String getStaticAmount() {
        return staticAmount;
    }

    public void setStaticAmount(String staticAmount) {
        this.staticAmount = staticAmount;
    }

    public String getStaticWay() {
        return staticWay;
    }

    public void setStaticWay(String staticWay) {
        this.staticWay = staticWay;
    }

    public String getFeeColumn() {
        return feeColumn;
    }

    public void setFeeColumn(String feeColumn) {
        this.feeColumn = feeColumn;
    }

    public String getGroupColumn() {
        return groupColumn;
    }

    public void setGroupColumn(String groupColumn) {
        this.groupColumn = groupColumn;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    @Override
    public String toString() {
        return "BusinessInfo{" +
                "id='" + id + '\'' +
                ", catagoryCode='" + catagoryCode + '\'' +
                ", catagory='" + catagory + '\'' +
                ", type='" + type + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableDesc='" + tableDesc + '\'' +
                ", medPh='" + medPh + '\'' +
                ", timeColumn='" + timeColumn + '\'' +
                ", staticAmount='" + staticAmount + '\'' +
                ", staticWay='" + staticWay + '\'' +
                ", feeColumn='" + feeColumn + '\'' +
                ", groupColumn='" + groupColumn + '\'' +
                ", validFlag='" + validFlag + '\'' +
                '}';
    }
}
