package www.seotoolzz.com.dts.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import www.seotoolzz.com.dts.Database.Models.Particular;
import www.seotoolzz.com.dts.R;

public class ParticularAdapter  extends RecyclerView.Adapter<ParticularAdapter.ViewHolder> {

    private List<Particular> mData;
    private LayoutInflater mInflater;
    private ParticularAdapter.ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public ParticularAdapter(Context context, List<Particular> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.particular_row, parent, false);
        return new ParticularAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Particular particular = mData.get(position);
        holder.particularWidget.setText(String.format("%s\n%s\n%s", particular.getItem_id().replace("'", ""), particular.getName().replace("'", ""), particular.getCost().replace("'", "")));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView particularWidget;

        ViewHolder(View itemView) {
            super(itemView);
            particularWidget = itemView.findViewById(R.id.particularWidget);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ParticularAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // convenience method for getting data at click position
    public Particular getItem(int id) {
        return mData.get(id);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
