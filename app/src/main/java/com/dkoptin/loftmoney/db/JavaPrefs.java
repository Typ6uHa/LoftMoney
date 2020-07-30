package com.dkoptin.loftmoney.db;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dkoptin.loftmoney.cells.money.MoneyCellModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JavaPrefs {

    private static final String INCOME = "INCOME";
    private static final String EXPENSES = "EXPENSES";

    private Gson gson = new Gson();
    private Context context;
    private SharedPreferences preferences;

    public JavaPrefs(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void removeInCome(String name) {
        MoneyCellModel item = null;
        ArrayList<MoneyCellModel> list = getInComeList();
        while (list.iterator().hasNext()) {
            MoneyCellModel moneyCellModel = list.iterator().next();
            if (moneyCellModel.getName().equals(name)) {
                item = moneyCellModel;
                break;
            }
        }
        if (item != null) {
            list.remove(item);
            preferences.edit().putString(INCOME, gson.toJson(list)).apply();
        }
    }

    public void removeExpenses(String name) {
        MoneyCellModel item = null;
        ArrayList<MoneyCellModel> list = getExpensesList();
        while (list.iterator().hasNext()) {
            MoneyCellModel moneyCellModel = list.iterator().next();
            if (moneyCellModel.getName().equals(name)) {
                item = moneyCellModel;
                break;
            }
        }
        if (item != null) {
            list.remove(item);
            preferences.edit().putString(EXPENSES, gson.toJson(list)).apply();
        }
    }

    public void addExpenses(MoneyCellModel moneyCellModel) {
        ArrayList<MoneyCellModel> list = getExpensesList();
        list.add(moneyCellModel);
        preferences.edit().putString(EXPENSES, gson.toJson(list)).apply();
    }

    public void addInCome(MoneyCellModel moneyCellModel) {
        ArrayList<MoneyCellModel> list = getInComeList();
        list.add(moneyCellModel);
        preferences.edit().putString(INCOME, gson.toJson(list)).apply();
    }

    public ArrayList<MoneyCellModel> getExpensesList() {
        Type itemType = (new TypeToken<ArrayList<MoneyCellModel>>() {
        }).getType();
        String json = preferences.getString(EXPENSES, "");
        ArrayList<MoneyCellModel> list = gson.fromJson(json, itemType);
        if (list != null) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<MoneyCellModel> getInComeList() {
        Type itemType = (new TypeToken<ArrayList<MoneyCellModel>>() {
        }).getType();
        String json = preferences.getString(INCOME, "");
        ArrayList<MoneyCellModel> list = gson.fromJson(json, itemType);
        if (list != null) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public void removeAll() {
        preferences.edit().clear().apply();
    }
}
