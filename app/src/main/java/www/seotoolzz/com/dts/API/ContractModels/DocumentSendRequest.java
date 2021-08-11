package www.seotoolzz.com.dts.API.ContractModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentSendRequest {


    @SerializedName("data_reference_no")
    @Expose
    private String data_reference_no;

    @SerializedName("data_office")
    @Expose
    private String data_office;


    @SerializedName("data_claimant")
    @Expose
    private String data_claimant;

    @SerializedName("data_transaction")
    @Expose
    private String data_transaction;

    @SerializedName("data_charge_to")
    @Expose
    private String data_charge_to;


    @SerializedName("data_current_department")
    @Expose
    private String data_current_department;


    @SerializedName("data_current_station")
    @Expose
    private String data_current_station;


    public String getData_reference_no() {
        return data_reference_no;
    }

    public void setData_reference_no(String data_reference_no) {
        this.data_reference_no = data_reference_no;
    }

    public String getData_office() {
        return data_office;
    }

    public void setData_office(String data_office) {
        this.data_office = data_office;
    }

    public String getData_claimant() {
        return data_claimant;
    }

    public void setData_claimant(String data_claimant) {
        this.data_claimant = data_claimant;
    }

    public String getData_transaction() {
        return data_transaction;
    }

    public void setData_transaction(String data_transaction) {
        this.data_transaction = data_transaction;
    }

    public String getData_charge_to() {
        return data_charge_to;
    }

    public void setData_charge_to(String data_charge_to) {
        this.data_charge_to = data_charge_to;
    }

    public String getData_current_department() {
        return data_current_department;
    }

    public void setData_current_department(String data_current_department) {
        this.data_current_department = data_current_department;
    }

    public String getData_current_station() {
        return data_current_station;
    }

    public void setData_current_station(String data_current_station) {
        this.data_current_station = data_current_station;
    }
}
