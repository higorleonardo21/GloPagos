package bo.hlva.glopagos.data.model;

import java.io.Serializable;

public class Day implements Serializable {

    private boolean isComplete;
    private long value;
    private String date;
    private int number;

    public Day(boolean isComplete, long value, String date, int number) {
        this.isComplete = isComplete;
        this.value = value;
        this.date = date;
        this.number = number;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
