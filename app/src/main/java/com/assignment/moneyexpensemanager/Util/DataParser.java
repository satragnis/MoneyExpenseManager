package com.assignment.moneyexpensemanager.Util;

/*
transaction_type
1: credit
2: debit
transaction_catagory
1: expense
2: emi
3: investment
4: income
transaction_sub_catagory
1: shopping
2: rent
3: food
4: travel
5: other
 */
public class DataParser {

    public static String getTransactionType(int i) {
        if (i == 1) {
            return "Credit";
        } else if (i == 2) {
            return "Debit";
        } else {
            return "";
        }
    }

    public static String getTransactionCategoryType(int i) {
        if (i == 1) {
            return "Expense";
        } else if (i == 2) {
            return "EMI";
        } else if (i == 3) {
            return "Investment";
        } else if (i == 4) {
            return "Income";
        } else {
            return "";
        }
    }

    public static String getTransactionSubCategoryType(int i) {
        if (i == 1) {
            return "Shopping";
        } else if (i == 2) {
            return "Rent";
        } else if (i == 3) {
            return "Food";
        } else if (i == 4) {
            return "Travel";
        } else if (i == 5) {
            return "Other";
        } else {
            return "";
        }
    }


}
