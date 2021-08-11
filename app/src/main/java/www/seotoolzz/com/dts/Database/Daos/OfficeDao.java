package www.seotoolzz.com.dts.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import www.seotoolzz.com.dts.Database.Models.Office;

@Dao
public interface OfficeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(Office office);

    @Query("SELECT * FROM offices")
    List<Office> getOffices();

    @Query("SELECT * FROM offices WHERE office_code = :office_code")
    Office getOffice(String office_code);

    @Query("SELECT office_name FROM offices ORDER BY office_code")
    String[] getOfficeNames();


    @Query("SELECT office_short_name FROM offices ORDER BY office_code")
    String[] getOfficeShortNames();
}
