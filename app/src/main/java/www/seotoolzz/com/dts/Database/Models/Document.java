package www.seotoolzz.com.dts.Database.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName ="documents")
public class Document {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String url;
    String reference_no;
    String pr_date;
    String office;
    String laimant;
    String particulars;
    String amount;
    String purpose;
    String charge_to;
    String current_department;
    String current_station;
    String item_no;
    String quantity;
    String unit_of_issue;
    String description;
    String estimated_unit_cost;
    String estimated_total_cost;
    String unique_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReference_no() {
        return reference_no;
    }

    public void setReference_no(String reference_no) {
        this.reference_no = reference_no;
    }

    public String getPr_date() {
        return pr_date;
    }

    public void setPr_date(String pr_date) {
        this.pr_date = pr_date;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getLaimant() {
        return laimant;
    }

    public void setLaimant(String laimant) {
        this.laimant = laimant;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCharge_to() {
        return charge_to;
    }

    public void setCharge_to(String charge_to) {
        this.charge_to = charge_to;
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

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit_of_issue() {
        return unit_of_issue;
    }

    public void setUnit_of_issue(String unit_of_issue) {
        this.unit_of_issue = unit_of_issue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimated_unit_cost() {
        return estimated_unit_cost;
    }

    public void setEstimated_unit_cost(String estimated_unit_cost) {
        this.estimated_unit_cost = estimated_unit_cost;
    }

    public String getEstimated_total_cost() {
        return estimated_total_cost;
    }

    public void setEstimated_total_cost(String estimated_total_cost) {
        this.estimated_total_cost = estimated_total_cost;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }
}
