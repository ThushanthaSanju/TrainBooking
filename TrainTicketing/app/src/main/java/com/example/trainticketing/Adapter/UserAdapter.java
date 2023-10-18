package com.example.trainticketing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainticketing.BookNowActivity;
import com.example.trainticketing.Model.TrainModel;
import com.example.trainticketing.Model.UserModel;
import com.example.trainticketing.R;
import com.example.trainticketing.UpdateProfileActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH> {

    List<UserModel> userList;
    Context context;

    public UserAdapter(Context context, List<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    public void setData(List<UserModel> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserAdapter.UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new UserAdapter.UserVH(LayoutInflater.from(context).inflate(R.layout.profilecard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserVH holder, int position) {
        UserModel userModel = userList.get(position);

        String fulname = userModel.getFullname();
        String ni = userModel.getNic();
        String phon = userModel.getPhone();
        String passw = userModel.getPassword();

        holder.fname.setText(fulname);
        holder.nic.setText(ni);
        holder.phn.setText(phon);
        holder.pass.setText(passw);

        holder.updt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateProfileActivity.class);
                intent.putExtra("id",userModel.get_id());
                intent.putExtra("name", userModel.getFullname());
                intent.putExtra("nic", userModel.getNic());
                intent.putExtra("phone", userModel.getPhone());
                intent.putExtra("email", userModel.getEmail());
                intent.putExtra("password", userModel.getPassword());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserVH extends RecyclerView.ViewHolder{
        TextView fname, nic, phn, pass;
        Button updt;
        LinearLayout view;
        public UserVH(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.Nameittxt);
            nic = itemView.findViewById(R.id.Nicittxt);
            phn = itemView.findViewById(R.id.Phoneedittxt);
            pass = itemView.findViewById(R.id.Passdittxt);
            view = itemView.findViewById(R.id.prolio);
            updt = itemView.findViewById(R.id.updtbtnNn);
        }
    }
}
