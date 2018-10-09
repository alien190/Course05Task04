package com.example.alien.recyclerviewitemanimator;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity implements SampleAdapter.Callback {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemAnimator mItemAnimator;
    private ObjectAnimator mRecyclerAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SampleAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mItemAnimator = new SampleItemAnimator();
        mRecyclerView.setItemAnimator(mItemAnimator);
    }

    @Override
    public void onItemClick(View view) {
        int itemPosition = mRecyclerView.getChildAdapterPosition(view);
        if (itemPosition != RecyclerView.NO_POSITION) {
            ((SampleAdapter) mAdapter).changeItemNumber(itemPosition);
        }
    }

    @Override
    public boolean onItemLongClick(View view) {
        int itemPosition = mRecyclerView.getChildAdapterPosition(view);
        if (itemPosition != RecyclerView.NO_POSITION) {
            ((SampleAdapter) mAdapter).deleteItem(itemPosition);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startAnimation();
    }

    private void startAnimation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mRecyclerAnimator = ObjectAnimator.ofFloat(mRecyclerView, View.X, -displayMetrics.widthPixels, 0);
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(1.5f);
        mRecyclerAnimator.setDuration(2500);
        mRecyclerAnimator.setInterpolator(decelerateInterpolator);
        mRecyclerAnimator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.miAdd) {
            ((SampleAdapter) mAdapter).addItem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}