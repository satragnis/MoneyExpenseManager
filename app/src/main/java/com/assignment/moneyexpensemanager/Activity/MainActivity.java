package com.assignment.moneyexpensemanager.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.assignment.moneyexpensemanager.API.ApiUrls;
import com.assignment.moneyexpensemanager.API.FetchDataListener;
import com.assignment.moneyexpensemanager.API.GETAPIRequest;
import com.assignment.moneyexpensemanager.API.RequestQueueService;
import com.assignment.moneyexpensemanager.Adapter.UserListAdapter;
import com.assignment.moneyexpensemanager.Models.ExpenseModel;
import com.assignment.moneyexpensemanager.R;
import com.assignment.moneyexpensemanager.Util.GlobalDataService;
import com.assignment.moneyexpensemanager.Util.Params;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        recyclerView = findViewById(R.id.recyclerView_layout);
        floatingActionButton = findViewById(R.id.add_fab);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        getData();
    }

    private void getData() {
        try {
            GETAPIRequest getapiRequest = new GETAPIRequest();
            getapiRequest.request(MainActivity.this, getDataListener, ApiUrls.DATA_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    FetchDataListener getDataListener = new FetchDataListener() {
        @Override
        public void onFetchComplete(JSONArray data) {
            try {
                swipeRefreshLayout.setRefreshing(false);
                ArrayList<ExpenseModel> expenseModels = new ArrayList<>();
                RequestQueueService.cancelProgressDialog();
                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
                for (int i = 0; i < data.length(); i++) {
                    ExpenseModel expenseModel = gson.fromJson(data.getJSONObject(i).toString(), ExpenseModel.class);
                    expenseModels.add(expenseModel);
                }
                inflateUserListAdapter(expenseModels);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFetchFailure(String msg) {
            swipeRefreshLayout.setRefreshing(false);
            RequestQueueService.cancelProgressDialog();
            GlobalDataService.showToasty(MainActivity.this, msg, Params.TOASTY_ERROR);
        }

        @Override
        public void onFetchStart() {
            RequestQueueService.showProgressDialog(MainActivity.this);
        }
    };


    private void inflateUserListAdapter(ArrayList<ExpenseModel> expenseModels) {
        if (getSupportFragmentManager() != null) {
            UserListAdapter mAdapter =
                    new UserListAdapter(expenseModels,
                            getSupportFragmentManager(),
                            recyclerView,
                            MainActivity.this);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.getRecycledViewPool().clear();
            mAdapter.notifyDataSetChanged();
        }
    }


    public void gotoForm(View view) {
        presentActivity(view);
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, CreateNewRequestActivity.class);
        intent.putExtra(CreateNewRequestActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(CreateNewRequestActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
