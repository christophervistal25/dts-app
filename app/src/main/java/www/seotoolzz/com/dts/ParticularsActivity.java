package www.seotoolzz.com.dts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.seotoolzz.com.dts.Adapters.DocumentAdapter;
import www.seotoolzz.com.dts.Adapters.ParticularAdapter;
import www.seotoolzz.com.dts.Database.Models.Particular;

public class ParticularsActivity extends AppCompatActivity  implements ParticularAdapter.ItemClickListener {

    ParticularAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);

        RecyclerView rvParticulars = findViewById(R.id.rvParticulars);
        rvParticulars.setLayoutManager(new LinearLayoutManager(this));

        String particulars = getIntent().getStringExtra("PARTICULARS");

        List<Particular> particularList = new ArrayList<>();

        for(String data : particulars.split("\\|")) {
            String[] splintedData = data.split(",");
            if(splintedData.length != 0) {
                Particular particular = new Particular();
                particular.setId(splintedData[0]);
                particular.setItem_id(splintedData[1]);
                particular.setName(splintedData[2]);
                particular.setUnit(splintedData[3]);
                particular.setDescription(splintedData[4]);
                particular.setEstimated_cost(splintedData[5]);
                particular.setCost(splintedData[6]);

                particularList.add(particular);
            }

            adapter = new ParticularAdapter(this, particularList);
            adapter.setClickListener(this);
            rvParticulars.setAdapter(adapter);
        }
//

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}