package com.dkoptin.loftmoney.db

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.dkoptin.loftmoney.cells.money.MoneyCellModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs(context: Context) {

    private val gson: Gson = Gson()
    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
                context
        )
    }

    fun removeInCome(name: String) {
        val inComeList = getInComeList().toMutableList()
        inComeList.remove(inComeList.find { it.name == name })
        preferences.edit().putString(INCOME, gson.toJson(inComeList)).apply()
    }

    fun removeExpenses(name: String) {
        val expensesList = getExpensesList().toMutableList()
        expensesList.remove(expensesList.find { it.name == name })
        preferences.edit().putString(EXPENSES, gson.toJson(expensesList)).apply()
    }

    fun addExpenses(moneyCellModel: MoneyCellModel) {
        val expensesList = getExpensesList().toMutableList()
        expensesList.add(moneyCellModel)
        preferences.edit().putString(EXPENSES, gson.toJson(expensesList)).apply()
    }

    fun addInCome(moneyCellModel: MoneyCellModel) {
        val inComeList = getInComeList().toMutableList()
        inComeList.add(moneyCellModel)
        preferences.edit().putString(INCOME, gson.toJson(inComeList)).apply()
    }

    fun getInComeList(): List<MoneyCellModel> {
        val itemType = object : TypeToken<List<MoneyCellModel>>() {}.type
        return gson.fromJson<List<MoneyCellModel>>(
                preferences.getString(INCOME, "") ?: "", itemType
        ) ?: emptyList()
    }

    fun getExpensesList(): List<MoneyCellModel> {
        val itemType = object : TypeToken<List<MoneyCellModel>>() {}.type
        return gson.fromJson<List<MoneyCellModel>>(
                preferences.getString(EXPENSES, "") ?: "", itemType
        ) ?: emptyList()
    }

    fun removeAll() {
        preferences.edit().clear().apply()
    }

    companion object {
        const val INCOME = "INCOME"
        const val EXPENSES = "EXPENSES"
    }
}