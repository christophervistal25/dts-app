package www.seotoolzz.com.dts.Database.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "offices")
public class Office {
    @NonNull
    @PrimaryKey()
    public String office_code;
    public String office_name;
    public String office_short_name;

    public String getOffice_code() {
        return office_code;
    }

    public void setOffice_code(String office_code) {
        this.office_code = office_code;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public String getOffice_short_name() {
        return office_short_name;
    }

    public void setOffice_short_name(String office_short_name) {
        this.office_short_name = office_short_name;
    }
}
