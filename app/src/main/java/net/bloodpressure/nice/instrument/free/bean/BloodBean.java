package net.bloodpressure.nice.instrument.free.bean;

import java.io.Serializable;

public class BloodBean implements Serializable {
    private int id;
    private int pulse;
    private int diastolic;
    private int systolic;
    private String timestamp;
    private String title;

    public BloodBean() {
    }

    public BloodBean(int id, int pulse, int diastolic, int systolic, String timestamp, String title) {
        this.id = id;
        this.pulse = pulse;
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.timestamp = timestamp;
        this.title = title;
    }

    public BloodBean(int pulse, int diastolic, int systolic, String timestamp, String title) {
        this.pulse = pulse;
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.timestamp = timestamp;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
