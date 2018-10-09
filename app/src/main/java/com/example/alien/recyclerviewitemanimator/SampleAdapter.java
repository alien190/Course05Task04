package com.example.alien.recyclerviewitemanimator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleViewHolder> {

    private Callback mCallback;
    private ArrayList<String> mDataset;

    public SampleAdapter(Context context) {
        mDataset = dataset();

        if (context instanceof Callback) {
            mCallback = (Callback) context;
        }
    }

    public void addItem() {
        mDataset.add(getRandomItemTitle());
        notifyItemInserted(mDataset.size() - 1);
    }

    private ArrayList<String> dataset() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add("Item " + i);
        }

        return list;
    }

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_item, parent, false);
        textView.setOnClickListener(mCallback::onItemClick);
        textView.setOnLongClickListener(mCallback::onItemLongClick);
        return new SampleViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void changeItemNumber(int position) {
        mDataset.set(position, getRandomItemTitle());
        notifyItemChanged(position);
    }

    private String getRandomItemTitle() {
        int number = new Random().nextInt(100);
        return "Item " + number;
    }

    public void deleteItem(int itemPosition) {
        if (itemPosition >= 0 && itemPosition < mDataset.size()) {
            mDataset.remove(itemPosition);
            notifyItemRemoved(itemPosition);
        }
    }

    static class SampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public SampleViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    interface Callback {

        void onItemClick(View view);

        boolean onItemLongClick(View view);
    }
}