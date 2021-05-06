package www.seotoolzz.com.dts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import www.seotoolzz.com.dts.Adapters.ParticularAdapter;
import www.seotoolzz.com.dts.Database.Models.Particular;

public class ParticularsActivity extends AppCompatActivity  implements ParticularAdapter.ItemClickListener{

    ParticularAdapter adapter;
    List<Particular> particularList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);

        RecyclerView rvParticulars = findViewById(R.id.rvParticulars);
        rvParticulars.setLayoutManager(new LinearLayoutManager(this));

        String particulars = getIntent().getStringExtra("PARTICULARS");

        particularList = new ArrayList<>();

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
        Particular particular = particularList.get(position);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ParticularsActivity.this);
        dialogBuilder.setTitle(particular.getDescription().replace("'", "").toUpperCase());
        dialogBuilder.setMessage("Unit : " + particular.getUnit().replace("'", "").toUpperCase() + "\n" + "Estimated cost : ₱" + particular.getEstimated_cost().replace("'", ""));
        AlertDialog confirmationDialog = dialogBuilder.create();
        confirmationDialog.show();

    }

}