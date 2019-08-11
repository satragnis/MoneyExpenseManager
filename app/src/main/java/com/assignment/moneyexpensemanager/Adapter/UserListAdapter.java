package com.assignment.moneyexpensemanager.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assignment.moneyexpensemanager.Activity.UserDetailsActivity;
import com.assignment.moneyexpensemanager.Models.ExpenseModel;
import com.assignment.moneyexpensemanager.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.DataObjectHolder> {

    View view;
    ArrayList<ExpenseModel> expenseModels;
    FragmentManager fragmentManager;
    RecyclerView recyclerView;
    Context context;

    public UserListAdapter(ArrayList<ExpenseModel> expenseModels, FragmentManager fragmentManager, RecyclerView recyclerView, Context context) {
        this.fragmentManager = fragmentManager;
        this.recyclerView = recyclerView;
        this.context = context;
        this.expenseModels = expenseModels;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_user_list,viewGroup,false);
        return new UserListAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, final int i) {
        holder.name.setText(expenseModels.get(i).getProfileName());
        holder.balance.setText("₹ "+String.valueOf(expenseModels.get(i).getAvailableBalance()));
        Glide.with(context).load(expenseModels.get(i).getProfilePictureUrl())
                .fallback(R.color.colorGreenAlpha)
                .error(R.color.colorGreenAlpha)
                .placeholder(R.color.colorGreenAlpha)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserDetailsActivity.class);
                UserDetailsActivity.setExpenseModel(expenseModels.get(i));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return expenseModels.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView balance,name;
        ImageView image;
        CardView container;


        public DataObjectHolder(View itemView) {
            super(itemView);
            balance      = itemView.findViewById(R.id.customer_balance_tv);
            name    = itemView.findViewById(R.id.customer_name_tv);
            image = itemView.findViewById(R.id.customer_iv);
            container   = itemView.findViewById(R.id.card_container);

        }
    }
}
