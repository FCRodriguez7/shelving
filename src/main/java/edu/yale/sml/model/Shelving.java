package edu.yale.sml.model;


import java.util.Date;

public class Shelving implements java.io.Serializable {

    private static final long serialVersionUID = 2215173185991677617L;

    private Integer accuracyErrors;

    private String barcodeEnd;

    private String barcodeStart;

    private Date creationDate;

    private String displayEnd = "";

    private String displayStart = "";

    private String endItemStatus = "";

    private Date endItemStatusDate;

    private String floor = "";

    private Date formDate = new Date();

    private int id;

    private String NETID = "";

    private String normalizedEnd = "";

    private String normalizedStart = "";

    private String notes;

    private String numRows;

    private String oversize;

    private String SCANLOCATION = "";

    private String startItemStatus = "";

    private Date startItemStatusDate;

    private String team;

    private float teamSize = 1;

    private String time;

    public Shelving() {
    }

    public Shelving(int id) {
        this.id = id;
    }

    public Integer getAccuracyErrors() {
        return this.accuracyErrors;
    }

    public String getBarcodeEnd() {
        return this.barcodeEnd;
    }

    public String getBarcodeStart() {
        return this.barcodeStart;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public String getDisplayEnd() {
        return displayEnd;
    }

    public String getDisplayStart() {
        return displayStart;
    }

    public String getEndItemStatus() {
        return endItemStatus;
    }

    public Date getEndItemStatusDate() {
        return endItemStatusDate;
    }

    public String getFloor() {
        return this.floor;
    }

    public Date getFormDate() {
        return this.formDate;
    }

    public int getId() {
        return this.id;
    }

    public String getNETID() {
        return NETID;
    }

    public String getNormalizedEnd() {
        return normalizedEnd;
    }

    public String getNormalizedStart() {
        return normalizedStart;
    }

    public String getNotes() {
        return this.notes;
    }

    public String getNumRows() {
        return this.numRows;
    }

    public String getOversize() {
        return oversize;
    }

    public String getSCANLOCATION() {
        return SCANLOCATION;
    }

    public String getStartItemStatus() {
        return startItemStatus;
    }

    public Date getStartItemStatusDate() {
        return startItemStatusDate;
    }

    public String getTeam() {
        return this.team;
    }

    public float getTeamSize() {
        return teamSize;
    }

    public String getTime() {
        return this.time;
    }

    public void setAccuracyErrors(Integer accuracyErrors) {
        this.accuracyErrors = accuracyErrors;
    }

    public void setBarcodeEnd(String barcodeEnd) {
        this.barcodeEnd = barcodeEnd;
    }

    public void setBarcodeStart(String barcodeStart) {
        this.barcodeStart = barcodeStart;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public void setDisplayEnd(String displayEnd) {
        this.displayEnd = displayEnd;
    }

    public void setDisplayStart(String displayStart) {
        this.displayStart = displayStart;
    }

    public void setEndItemStatus(String endItemStatus) {
        this.endItemStatus = endItemStatus;
    }

    public void setEndItemStatusDate(Date endItemStatusDate) {
        this.endItemStatusDate = endItemStatusDate;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setFormDate(Date formDate) {
        this.formDate = formDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNETID(String nETID) {
        NETID = nETID;
    }

    public void setNormalizedEnd(String normalizedEnd) {
        this.normalizedEnd = normalizedEnd;
    }

    public void setNormalizedStart(String normalizedStart) {
        this.normalizedStart = normalizedStart;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setNumRows(String numRows) {
        this.numRows = numRows;
    }

    public void setOversize(String oversize) {
        this.oversize = oversize;
    }

    public void setSCANLOCATION(String sCANLOCATION) {
        SCANLOCATION = sCANLOCATION;
    }

    public void setStartItemStatus(String startItemStatus) {
        this.startItemStatus = startItemStatus;
    }

    public void setStartItemStatusDate(Date startItemStatusDate) {
        this.startItemStatusDate = startItemStatusDate;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setTeamSize(float teamSize) {
        this.teamSize = teamSize;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Shelving [id=" + id + ", creationDate=" + creationDate
                + ", formDate=" + formDate + ", oversize=" +
                oversize + ", time=" + time + ", numRows=" + numRows
                + ", floor=" + floor + ", notes=" + notes + "," +
                " team=" + team + ", barcodeStart=" + barcodeStart + ", barcodeEnd="
                + barcodeEnd + ", accuracyErrors=" + accuracyErrors + "]";
    }

}
