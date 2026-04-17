package cc.reconnected.client;

import com.google.gson.Gson;

public class SupporterData {
    public int amount; // Total amount donated
    public int count; // Total count of donations this month
    public int average; // Average donation this month
    public int goal; // Donation goal for this month.

    public SupporterData(int amount, int count, int average, int goal) {
        this.amount = amount;
        this.count = count;
        this.average = average;
        this.goal = goal;
    }
    public static SupporterData deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SupporterData.class);
    }
}

