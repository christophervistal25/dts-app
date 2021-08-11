package www.seotoolzz.com.dts.Database.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName ="documents")
public class Document {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String reference_no;
    String office;
    String claimant;
    String transaction;
    String current_department;
    String current_station;
    String logs_user_id;
    String history_logs_datetime;
    String charge_to;
    String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference_no() {
        return reference_no;
    }

    public void setReference_no(String reference_no) {
        this.reference_no = reference_no;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getClaimant() {
        return claimant;
    }

    public void setClaimant(String claimant) {
        this.claimant = claimant;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getCurrent_department() {
        return current_department;
    }

    public void setCurrent_department(String current_department) {
        this.current_department = current_department;
    }

    public String getCurrent_station() {
        return current_station;
    }

    public void setCurrent_station(String current_station) {
        this.current_station = current_station;
    }

    public String getLogs_user_id() {
        return logs_user_id;
    }

    public void setLogs_user_id(String logs_user_id) {
        this.logs_user_id = logs_user_id;
    }

    public String getHistory_logs_datetime() {
        return history_logs_datetime;
    }

    public void setHistory_logs_datetime(String history_logs_datetime) {
        this.history_logs_datetime = history_logs_datetime;
    }

    public String getCharge_to() {
        return charge_to;
    }

    public void setCharge_to(String charge_to) {
        this.charge_to = charge_to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
