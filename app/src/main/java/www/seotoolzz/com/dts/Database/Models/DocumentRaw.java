package www.seotoolzz.com.dts.Database.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName ="document_raw")
public class DocumentRaw {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String reference_no;
    public String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
