package www.seotoolzz.com.dts.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import www.seotoolzz.com.dts.Database.Models.Document;
import www.seotoolzz.com.dts.Database.Models.Particular;
import www.seotoolzz.com.dts.R;

public class ParticularAdapter  extends RecyclerView.Adapter<ParticularAdapter.ViewHolder> {

    private List<Particular> mData;
    private LayoutInflater mInflater;
    private DocumentAdapter.ItemClickListener mClickListener;
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView referenceNo;

        ViewHolder(View itemView) {
            super(itemView);
            referenceNo = itemView.findViewById(R.id.documentReference);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    public void setClickListener(DocumentAdapter.ItemClickListener itemClickListener) {
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
