package com.example.trainticketing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainticketing.HomeActivity;
import com.example.trainticketing.Model.BookingModel;
import com.example.trainticketing.R;
import com.example.trainticketing.UpdateActivity;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingVH> {

    List<BookingModel> bookingList;
    Context context;

    public BookingAdapter(Context context, List<BookingModel> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    public void setData(List<BookingModel> bookingList) {
        this.bookingList = bookingList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BookingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new BookingAdapter.BookingVH(LayoutInflater.from(context).inflate(R.layout.bookingcard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.BookingVH holder, int position) {

        BookingModel bookingModel = bookingList.get(position);

        String trainName = bookingModel.getName();
        String date = bookingModel.getDate();
        Integer passeng = bookingModel.getNumberOfSeat();

        holder.trainName.setText(trainName);
        holder.date.setText(date);
        holder.nop.setText(String.valueOf(passeng));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("_id",bookingModel.get_id());
                intent.putExtra("name", bookingModel.getName());
                intent.putExtra("arrivedTime", bookingModel.getArrivedTime());
                intent.putExtra("departureTime", bookingModel.getDepartureTime());
                intent.putExtra("priceForOneSeat", bookingModel.getPriceForOneSeat());
                intent.putExtra("from", bookingModel.getFrom());
                intent.putExtra("to", bookingModel.getTo());
                intent.putExtra("numberOfSeat", bookingModel.getNumberOfSeat());
                intent.putExtra("date", bookingModel.getDate());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class BookingVH extends RecyclerView.ViewHolder {
        TextView trainName, date, nop;
        LinearLayout view;
        public BookingVH(@NonNull View itemView) {
            super(itemView);
            trainName = itemView.findViewById(R.id.txtcardoh);
            date = itemView.findViewById(R.id.txtaddoh);
            nop = itemView.findViewById(R.id.txtdoh);
            view = itemView.findViewById(R.id.bookinglin);
        }
    }
}
