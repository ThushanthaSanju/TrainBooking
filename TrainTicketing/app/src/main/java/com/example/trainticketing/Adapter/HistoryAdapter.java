package com.example.trainticketing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainticketing.Model.BookingModel;
import com.example.trainticketing.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryVH>{
    List<BookingModel> bookingListh;
    Context context;

    public HistoryAdapter(Context context, List<BookingModel> bookingListh) {
        this.context = context;
        this.bookingListh = bookingListh;
    }

    public void setData(List<BookingModel> bookingListh) {
        this.bookingListh = bookingListh;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HistoryAdapter.HistoryVH(LayoutInflater.from(context).inflate(R.layout.historycard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryVH holder, int position) {
        BookingModel bookingModel = bookingListh.get(position);

        String trainNameh = bookingModel.getName();
        String dateh = bookingModel.getDate();
        Integer passengh = bookingModel.getNumberOfSeat();

        holder.trainNameh.setText(trainNameh);
        holder.dateh.setText(dateh);
        holder.noph.setText(String.valueOf(passengh));
    }

    @Override
    public int getItemCount() {
        return bookingListh.size();
    }
    public class HistoryVH extends RecyclerView.ViewHolder {
        TextView trainNameh, dateh, noph;
        LinearLayout view;
        public HistoryVH(@NonNull View itemView) {
            super(itemView);
            trainNameh = itemView.findViewById(R.id.txtcardohh);
            dateh = itemView.findViewById(R.id.txtaddohh);
            noph = itemView.findViewById(R.id.txtdohh);
            view = itemView.findViewById(R.id.historylin);
        }
    }

}
