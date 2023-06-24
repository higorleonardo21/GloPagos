package bo.hlva.glopagos.data.model;

import java.io.Serializable;

public class Day implements Serializable {

    private int amount;
    private String date;
    private int number;

    public Day(int amount, String date, int number) {
        this.amount = amount;
        this.date = date;
        this.number = number;
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

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
