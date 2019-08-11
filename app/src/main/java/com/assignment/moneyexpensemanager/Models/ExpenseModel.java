package com.assignment.moneyexpensemanager.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseModel {
    @SerializedName("available_balance")
    @Expose
    private Integer availableBalance;
    @SerializedName("profile_name")
    @Expose
    private String profileName;
    @SerializedName("profile_picture_url")
    @Expose
    private String profilePictureUrl;
    @SerializedName("profile_serial_no")
    @Expose
    private Integer profileSerialNo;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;

    public Integer getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Integer availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Integer getProfileSerialNo() {
        return profileSerialNo;
    }

    public void setProfileSerialNo(Integer profileSerialNo) {
        this.profileSerialNo = profileSerialNo;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public class Transaction {

        @SerializedName("transaction_amount")
        @Expose
        private Integer transactionAmount;
        @SerializedName("transaction_catagory")
        @Expose
        private Integer transactionCatagory;
        @SerializedName("transaction_date")
        @Expose
        private Integer transactionDate;
        @SerializedName("transaction_sub_catagory")
        @Expose
        private Integer transactionSubCatagory;
        @SerializedName("transaction_type")
        @Expose
        private Integer transactionType;

        public Integer getTransactionAmount() {
            return transactionAmount;
        }

        public void setTransactionAmount(Integer transactionAmount) {
            this.transactionAmount = transactionAmount;
        }

        public Integer getTransactionCatagory() {
            return transactionCatagory;
        }

        public void setTransactionCatagory(Integer transactionCatagory) {
            this.transactionCatagory = transactionCatagory;
        }

        public Integer getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(Integer transactionDate) {
            this.transactionDate = transactionDate;
        }

        public Integer getTransactionSubCatagory() {
            return transactionSubCatagory;
        }

        public void setTransactionSubCatagory(Integer transactionSubCatagory) {
            this.transactionSubCatagory = transactionSubCatagory;
        }

        public Integer getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(Integer transactionType) {
            this.transactionType = transactionType;
        }

    }
}
