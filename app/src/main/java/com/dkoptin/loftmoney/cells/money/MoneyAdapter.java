package com.dkoptin.loftmoney.cells.money;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dkoptin.loftmoney.R;

import java.util.ArrayList;
import java.util.List;

public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder> {

    List<MoneyCellModel> moneyCellModels = new ArrayList<>();
    View.OnClickListener onClickListener;

    public void addData(List<MoneyCellModel> moneyCellModels) {
        this.moneyCellModels.addAll(moneyCellModels);
        notifyDataSetChanged();
    }

    public List<MoneyCellModel> getData() {
        return moneyCellModels;
    }

    public void setData(List<MoneyCellModel> moneyCellModels) {
        this.moneyCellModels.clear();
        this.moneyCellModels.addAll(moneyCellModels);
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoneyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_money, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(moneyCellModels.get(position));
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return moneyCellModels.size();
    }

    static class MoneyViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        TextView valueView;

        public MoneyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.cellMoneyNameView);
            valueView = itemView.findViewById(R.id.cellMoneyValueView);

        }

        public void bind(MoneyCellModel moneyCellModel) {
            nameView.setText(moneyCellModel.getName());
            valueView.setText(moneyCellModel.getValue());
            valueView.setTextColor(ContextCompat.getColor(valueView.getContext(), moneyCellModel.getColor()));
        }
    }
}
