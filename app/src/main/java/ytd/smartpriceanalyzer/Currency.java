package ytd.smartpriceanalyzer;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Currency {

    private static String CurrencyOneId;
    private static String CurrencyTwoId;

    public static String getCurrencyOneId() {
        return CurrencyOneId;
    }

    public static void setCurrencyOneId(String currencyOneId) {
        CurrencyOneId = currencyOneId;
    }

    public static String getCurrencyTwoId() {
        return CurrencyTwoId;
    }

    public static void setCurrencyTwoId(String currencyTwoId) {
        CurrencyTwoId = currencyTwoId;
    }

    private static double rate = 0.0;

    public static double getRate() {
        return rate;
    }

    public static void setRate(double rate) {
        Currency.rate = rate;
    }

}
