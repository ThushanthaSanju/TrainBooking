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

import com.example.trainticketing.BookNowActivity;
import com.example.trainticketing.Model.TrainModel;
import com.example.trainticketing.R;

import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainVH> {

    List<TrainModel> trainList;
    Context context;

    public TrainAdapter(Context context, List<TrainModel> trainList) {
        this.context = context;
        this.trainList = trainList;
    }

    public void setData(List<TrainModel> trainList) {
        this.trainList = trainList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TrainVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TrainAdapter.TrainVH(LayoutInflater.from(context).inflate(R.layout.traincard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrainVH holder, int position) {
        TrainModel trainModel = trainList.get(position);

        String trainName = trainModel.getName();
        Integer price = trainModel.getPriceForOneSeat();

        holder.trainName.setText(trainName);
        holder.price.setText(String.valueOf(price));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookNowActivity.class);
                intent.putExtra("id",trainModel.get_id());
                intent.putExtra("name", trainModel.getName());
                intent.putExtra("arrivedTime", trainModel.getArrivedTime());
                intent.putExtra("departureTime", trainModel.getDepartureTime());
                intent.putExtra("priceForOneSeat", trainModel.getPriceForOneSeat());
                intent.putExtra("from", trainModel.getFrom());
                intent.putExtra("to", trainModel.getTo());
                intent.putExtra("numberOfSeat", trainModel.getNumberOfSeat());
                intent.putExtra("status", trainModel.isStatus());
                intent.putExtra("date", trainModel.getDate());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

    public class TrainVH extends RecyclerView.ViewHolder {
        TextView trainName, price;
        LinearLayout view;
        public TrainVH(@NonNull View itemView) {
            super(itemView);
            trainName = itemView.findViewById(R.id.txtcardo);
            price = itemView.findViewById(R.id.txtaddo);
            view = itemView.findViewById(R.id.lilayo);
        }
    }

}
