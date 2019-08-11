package com.assignment.moneyexpensemanager.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.assignment.moneyexpensemanager.Models.ExpenseModel;
import com.assignment.moneyexpensemanager.R;
import com.assignment.moneyexpensemanager.Util.DataParser;
import com.assignment.moneyexpensemanager.Util.GlobalDataService;
import com.assignment.moneyexpensemanager.Util.Params;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDetailsActivity extends AppCompatActivity{

    static ExpenseModel expenseModel = null;
    BarChart barChart;
    List<ExpenseModel.Transaction> transactionList =new ArrayList<>();

    public static ExpenseModel getExpenseModel() {
        return expenseModel;
    }

    public static void setExpenseModel(ExpenseModel expenseModel1) {
      expenseModel = expenseModel1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        barChart = findViewById(R.id.barchart);
        initialisePieChart();
    }

    private void initialisePieChart() {
        if(expenseModel!=null){
            transactionList = expenseModel.getTransactions();
            PieChart pieChart = findViewById(R.id.piechart);
            ArrayList monthlyExpenses = new ArrayList();
            ArrayList<String> transactionCategories = new ArrayList();
            PieDataSet dataSet = null;
            HashMap<Integer,Integer> groupedExpensesMap =  groupTotalExpenditure(transactionList);
            for(int i=1;i<=groupedExpensesMap.size();i++){
                    monthlyExpenses.add(new Entry(groupedExpensesMap.get(i), i-1));
                    dataSet = new PieDataSet(monthlyExpenses, "Monthly Expenses");
                    transactionCategories.add(DataParser.getTransactionCategoryType(i));
            }
            if(dataSet!=null){
            PieData data = new PieData(transactionCategories, dataSet);
            data.setValueTextSize(14);
            data.setValueTextColor(R.color.white);
            pieChart.setData(data);
            dataSet.setColors(ColorTemplate.PASTEL_COLORS);
            pieChart.animateXY(3000, 3000);
            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    int catType = e.getXIndex()+1;
                    if(DataParser.getTransactionCategoryType(catType).equalsIgnoreCase("expense")){
                        HashMap<Integer,Integer> groupedExpensesMap =  groupExpenses(transactionList);
                        showBarChart(groupedExpensesMap);
                    }else{
                        barChart.setVisibility(View.GONE);
                        GlobalDataService.showToasty(UserDetailsActivity.this,"No data available currently", Params.TOASTY_INFO);
                    }
                }

                @Override
                public void onNothingSelected() {

                }
            });
            }


        }
    }

    private void showBarChart(HashMap<Integer, Integer> groupedExpensesMap) {
        ArrayList monthlyExpenses2 = new ArrayList();
        ArrayList<String> transactionSubCategories = new ArrayList();
        BarDataSet dataSet = null;
        for (int i = 1; i <= groupedExpensesMap.size(); i++) {
            monthlyExpenses2.add(new BarEntry(groupedExpensesMap.get(i), i - 1));
            dataSet = new BarDataSet(monthlyExpenses2, "Detailed Monthly Expenses");
            transactionSubCategories.add(DataParser.getTransactionSubCategoryType(i));
        }
        if (dataSet != null) {
            barChart.setVisibility(View.VISIBLE);
            BarData data = new BarData(transactionSubCategories, dataSet);
            barChart.setData(data);
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barChart.animateXY(5000, 3000);
        }
    }

    private HashMap<Integer,Integer> groupTotalExpenditure(List<ExpenseModel.Transaction> transactionList) {
        HashMap<Integer,Integer> integerHashMap =new HashMap<>();
        int exp1 = 0 ,exp2=0,exp3=0;
        for(int i=0;i<transactionList.size();i++){
            ExpenseModel.Transaction transaction = transactionList.get(i);
            if(transaction.getTransactionCatagory()!=4) {
                if(transaction.getTransactionCatagory()==1){
                    exp1 = exp1+transaction.getTransactionAmount();
                }else if(transaction.getTransactionCatagory()==2){
                    exp2 = exp2+transaction.getTransactionAmount();
                }else if(transaction.getTransactionCatagory()==3){
                    exp3 = exp3+transaction.getTransactionAmount();
                }
            }
        }
        integerHashMap.put(1,exp1);
        integerHashMap.put(2,exp2);
        integerHashMap.put(3,exp3);
        return integerHashMap;
    }

    private HashMap<Integer,Integer> groupExpenses(List<ExpenseModel.Transaction> transactionList) {
        HashMap<Integer,Integer> integerHashMap =new HashMap<>();
        int exp1 = 0 ,exp2=0,exp3=0,exp4=0,exp5=0;
        for(int i=0;i<transactionList.size();i++){
            ExpenseModel.Transaction transaction = transactionList.get(i);
                if(transaction.getTransactionSubCatagory()==1){
                    exp1 = exp1+transaction.getTransactionAmount();
                }else if(transaction.getTransactionSubCatagory()==2){
                    exp2 = exp2+transaction.getTransactionAmount();
                }else if(transaction.getTransactionSubCatagory()==3){
                    exp3 = exp3+transaction.getTransactionAmount();
                }else if(transaction.getTransactionSubCatagory()==4){
                    exp4 = exp4+transaction.getTransactionAmount();
                }else if(transaction.getTransactionSubCatagory()==5){
                    exp5 = exp5+transaction.getTransactionAmount();
                }
        }
        integerHashMap.put(1,exp1);
        integerHashMap.put(2,exp2);
        integerHashMap.put(3,exp3);
        integerHashMap.put(4,exp4);
        integerHashMap.put(5,exp5);
        return integerHashMap;
    }

}
