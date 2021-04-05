package www.seotoolzz.com.dts.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import www.seotoolzz.com.dts.Database.Daos.DocumentDao;
import www.seotoolzz.com.dts.Database.Daos.UserDao;
import www.seotoolzz.com.dts.Database.Models.Document;
import www.seotoolzz.com.dts.Database.Models.User;


@Database(entities = {
        User.class, Document.class},version = 1)
public abstract class DB extends RoomDatabase {

    private static DB appDatabase;
    private Context context;
    public abstract UserDao userDao();
    public abstract DocumentDao documentDao();


    public synchronized  static DB getInstance(Context context)
    {
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), DB.class, "dts_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public void destroyInstance() {
        appDatabase = null;
    }
}