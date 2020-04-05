package com.example.menu.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    public static List<StatisticData> getIncomeStatisticData(List<Transaction> transactions) {
        Map<String, Double> map = new HashMap<>();
        for (Transaction transaction: transactions) {
            if (transaction.getAmount() > 0) {
                if (map.containsKey(transaction.getCategory())) {
                    map.replace(transaction.getCategory(), map.get(transaction.getCategory()) + transaction.getAmount());
                } else {
                    map.put(transaction.getCategory(), transaction.getAmount());
                }
            }
        }
        List<StatisticData> data = new ArrayList<>();
        double total = 0;
        for (String category : map.keySet()) {
            data.add(new StatisticData(category, map.get(category)));
            total += map.get(category);
        }
        for (StatisticData single : data) {
            single.setPercent((float) (single.getAmount() / total));
        }
        return data;
    }

    public static List<StatisticData> getExpenseStatisticData(List<Transaction> transactions) {
        Map<String, Double> map = new HashMap<>();
        for (Transaction transaction: transactions) {
            if (transaction.getAmount() < 0) {
                if (map.containsKey(transaction.getCategory())) {
                    map.replace(transaction.getCategory(), map.get(transaction.getCategory()) + transaction.getAmount());
                } else {
                    map.put(transaction.getCategory(), transaction.getAmount());
                }
            }
        }
        List<StatisticData> data = new ArrayList<>();
        double total = 0;
        for (String category : map.keySet()) {
            data.add(new StatisticData(category, map.get(category)));
            total += map.get(category);
        }
        for (StatisticData single : data) {
            single.setPercent((float) (single.getAmount() / total));
        }
        return data;
    }

    public static List<StatisticData> getIncomeStatisticDataByMonth(List<Transaction> transactions, String dateMonth) {
        Map<String, Double> map = new HashMap<>();
        for (Transaction transaction: transactions) {
            if (transaction.getAmount() > 0 && transaction.getDateString().contains(dateMonth)) {
                if (map.containsKey(transaction.getCategory())) {
                    map.replace(transaction.getCategory(), map.get(transaction.getCategory()) + transaction.getAmount());
                } else {
                    map.put(transaction.getCategory(), transaction.getAmount());
                }
            }
        }
        List<StatisticData> data = new ArrayList<>();
        double total = 0;
        for (String category : map.keySet()) {
            data.add(new StatisticData(category, map.get(category)));
            total += map.get(category);
        }
        for (StatisticData single : data) {
            single.setPercent((float) (single.getAmount() / total));
        }
        return data;
    }

    public static List<StatisticData> getExpenseStatisticDataByMonth(List<Transaction> transactions, String dateMonth) {
        Map<String, Double> map = new HashMap<>();
        for (Transaction transaction: transactions) {
            if (transaction.getAmount() < 0 && transaction.getDateString().contains(dateMonth)) {
                if (map.containsKey(transaction.getCategory())) {
                    map.replace(transaction.getCategory(), map.get(transaction.getCategory()) + transaction.getAmount());
                } else {
                    map.put(transaction.getCategory(), transaction.getAmount());
                }
            }
        }
        List<StatisticData> data = new ArrayList<>();
        double total = 0;
        for (String category : map.keySet()) {
            data.add(new StatisticData(category, map.get(category)));
            total += map.get(category);
        }
        for (StatisticData single : data) {
            single.setPercent((float) (single.getAmount() / total));
        }
        return data;
    }
}
