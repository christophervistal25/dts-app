package www.seotoolzz.com.dts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import www.seotoolzz.com.dts.Database.Models.Particular;

public class ParticularsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);
        String particulars = getIntent().getStringExtra("PARTICULARS");
        Toast.makeText(this, particulars, Toast.LENGTH_SHORT).show();
        String[] particularList = particulars.split(",");
        for(String list : particularList)
        {
            Log.d("SAMPLE_DATA", list);
        }

    }
}