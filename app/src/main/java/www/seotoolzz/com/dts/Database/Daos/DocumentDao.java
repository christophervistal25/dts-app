package www.seotoolzz.com.dts.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import www.seotoolzz.com.dts.Database.Models.Document;

@Dao
public interface DocumentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(Document document);

    @Query("SELECT * FROM documents")
    List<Document> get();
}
