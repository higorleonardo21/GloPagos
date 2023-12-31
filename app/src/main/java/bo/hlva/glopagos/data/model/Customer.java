package bo.hlva.glopagos.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "customers")
public class Customer implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "id_number")
    private String idNumber;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "percentage")
    private int percentage;

    @ColumnInfo(name = "days")
    private ArrayList<Day> days;

    public Customer(
            String name,
            String lastName,
            String idNumber,
            String phone,
            String date,
            int amount,
            int percentage) {
        this.name = name;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.phone = phone;
        this.date = date;
        this.amount = amount;
        this.percentage = percentage;
        this.days = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<Day> getDays() {
        return this.days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPercentage() {
        return this.percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
