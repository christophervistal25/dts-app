package www.seotoolzz.com.dts.API.ContractModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentSendResponse {
    @SerializedName("success")
    @Expose
    private boolean success;


    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
