package www.seotoolzz.com.dts.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import www.seotoolzz.com.dts.Database.Models.Document;

@Dao
public interface DocumentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(Document document);

    @Update
    void update(Document document);

    @Query("SELECT * FROM documents ")
    List<Document> get();


    @Query("SELECT * FROM documents WHERE reference_no = :reference_no")
    Document find(String reference_no);

    @Query("UPDATE documents SET  status = 'SENT' WHERE  reference_no = :REFERENCE_NO")
    void updateSent(String REFERENCE_NO);




}
