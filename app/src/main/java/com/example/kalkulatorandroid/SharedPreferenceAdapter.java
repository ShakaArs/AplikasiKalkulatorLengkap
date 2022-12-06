package com.example.kalkulatorandroid;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SharedPreferenceAdapter extends RecyclerView.Adapter<SharedPreferenceAdapter.ExampleViewHolder> {
    private final ArrayList<com.example.kalkulatorandroid.ItemList> ListHitung;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewLine1;
        public TextView mTextViewLineOperasi;
        public TextView mTextViewLine2;
        public TextView mTextViewLineHasil;

        @SuppressLint("CutPasteId")
        public ExampleViewHolder(View itemView) {
            super(itemView);
            mTextViewLine1 = itemView.findViewById(R.id.textview_line1);
            mTextViewLineOperasi = itemView.findViewById(R.id.textview_operasi);
            mTextViewLine2 = itemView.findViewById(R.id.textview_line2);
            mTextViewLineHasil = itemView.findViewById(R.id.textview_hasil);
        }
    }

    public SharedPreferenceAdapter(ArrayList<com.example.kalkulatorandroid.ItemList> exampleList) {
        ListHitung = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        com.example.kalkulatorandroid.ItemList currentItem = ListHitung.get(position);

        holder.mTextViewLine1.setText(currentItem.getLine1());
        holder.mTextViewLine2.setText(currentItem.getLine2());
        holder.mTextViewLineOperasi.setText(currentItem.getLineOperasi());
        holder.mTextViewLineHasil.setText(currentItem.getLineHasil());
    }

    @Override
    public int getItemCount() {
        return ListHitung.size();
    }
}
