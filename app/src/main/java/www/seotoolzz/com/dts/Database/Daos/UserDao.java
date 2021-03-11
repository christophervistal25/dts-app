package www.seotoolzz.com.dts.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;



import www.seotoolzz.com.dts.Database.Models.User;


@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE user_account_username = :username")
    User findByUsername(String username);
}
