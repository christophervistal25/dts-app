package www.seotoolzz.com.dts.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import www.seotoolzz.com.dts.Database.Models.Document;
import www.seotoolzz.com.dts.R;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    private final List<Document> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public DocumentAdapter(Context context, List<Document> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_row, parent, false);
        return new ViewHolder(view);
    }



    // binds the data to the TextView in each row
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Document document = mData.get(position);
        String SENT_STATUS = "SENT";

        if(document.getStatus() !=  null && document.getStatus().toUpperCase().equals(SENT_STATUS)) {
            holder.border.setBackgroundColor(Color.parseColor("#1dbc60"));
        } else {
            holder.border.setBackgroundColor(Color.parseColor("#2962FF"));
        }
        holder.referenceNo.setText(document.getReference_no());
        holder.office.setText(document.getOffice());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView referenceNo;
        TextView office;
        View border;

        ViewHolder(View itemView) {
            super(itemView);
            referenceNo = itemView.findViewById(R.id.referenceNo);
            office = itemView.findViewById(R.id.office);
            border = itemView.findViewById(R.id.border);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    // convenience method for getting data at click position
    public Document getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
