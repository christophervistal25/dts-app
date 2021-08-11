package www.seotoolzz.com.dts.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import www.seotoolzz.com.dts.Database.Models.DocumentRaw;

@Dao
public interface DocumentRawDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(DocumentRaw document);

    @Query("SELECT * FROM document_raw WHERE unique_id = :unique_id")
    DocumentRaw find(String unique_id);

    @Query("SELECT * FROM document_raw WHERE reference_no = :reference_no")
    DocumentRaw findByReferenceNo(String reference_no);

    @Query("SELECT * FROM document_raw")
    List<DocumentRaw> all();
}
