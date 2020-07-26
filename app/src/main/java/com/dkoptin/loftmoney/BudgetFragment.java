package com.dkoptin.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dkoptin.loftmoney.cells.money.MoneyAdapter;
import com.dkoptin.loftmoney.cells.money.MoneyCellModel;
import com.dkoptin.loftmoney.db.Prefs;
import com.dkoptin.loftmoney.util.RequestCode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    RecyclerView recyclerView;
    MoneyAdapter moneyAdapter;
    private String name;
    private String price;
    private Prefs prefs;

    public static BudgetFragment newInstance(BudgetFragmentTags tag) {
        BudgetFragment myFragment = new BudgetFragment();

        Bundle args = new Bundle();
        args.putSerializable("someTag", tag);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = new Prefs(requireContext());
        recyclerView = view.findViewById(R.id.costsRecyclerView);
        moneyAdapter = new MoneyAdapter();
        moneyAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((BudgetFragmentTags) getArguments().getSerializable("someTag")) == BudgetFragmentTags.EXPENSES) {
                    int itemPosition = recyclerView.getChildLayoutPosition(view);
                    MoneyCellModel item = moneyAdapter.getData().get(itemPosition);
                    prefs.removeExpenses(item.getName());
                } else {
                    int itemPosition = recyclerView.getChildLayoutPosition(view);
                    MoneyCellModel item = moneyAdapter.getData().get(itemPosition);
                    prefs.removeInCome(item.getName());
                }

                if (((BudgetFragmentTags) getArguments().getSerializable("someTag")) == BudgetFragmentTags.EXPENSES) {
                    moneyAdapter.setData(prefs.getExpensesList());
                } else {
                    moneyAdapter.setData(prefs.getInComeList());
                }
            }
        });

        recyclerView.setAdapter(moneyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        if (((BudgetFragmentTags) getArguments().getSerializable("someTag")) == BudgetFragmentTags.EXPENSES) {
            moneyAdapter.setData(prefs.getExpensesList());
        } else {
            moneyAdapter.setData(prefs.getInComeList());
        }

        FloatingActionButton addCellExpenses = view.findViewById(R.id.addCellExpeneses);
        addCellExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_ITEM);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private List<MoneyCellModel> generateExpenses() {
        List<MoneyCellModel> moneyCellModels = new ArrayList<>();
        MoneyCellModel local = new MoneyCellModel(name, (price + " ₽"), R.color.expenseColor);
        prefs.addExpenses(local);
        moneyCellModels.add(local);
        return moneyCellModels;
    }

    private List<MoneyCellModel> generateIncome() {
        List<MoneyCellModel> moneyCellModels = new ArrayList<>();
        MoneyCellModel local = new MoneyCellModel(name, (price + " ₽"), R.color.colorTemp);
        prefs.addInCome(local);
        moneyCellModels.add(local);
        return moneyCellModels;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        name = data.getStringExtra("name");
        price = data.getStringExtra("price");
        if (((BudgetFragmentTags) getArguments().getSerializable("someTag")) == BudgetFragmentTags.EXPENSES) {
            moneyAdapter.addData(generateExpenses());
        } else {
            moneyAdapter.addData(generateIncome());
        }
    }
}
