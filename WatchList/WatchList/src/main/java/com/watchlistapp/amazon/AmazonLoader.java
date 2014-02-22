package com.watchlistapp.amazon;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VEINHORN on 22/02/14.
 */
public class AmazonLoader {
    private final static String BASE_URL = "http://watchlist-app-server.herokuapp.com/amazon/dvds?title=";

    private Context context;
    private String movieTitle;
    private AmazonItemsContainer amazonItemsContainer;
    private AmazonItemAdapter amazonItemAdapter;

    public AmazonLoader(Context context, String movieTitle, AmazonItemsContainer amazonItemsContainer, AmazonItemAdapter amazonItemAdapter) {
        this.context = context;
        this.movieTitle = movieTitle;
        this.amazonItemsContainer = amazonItemsContainer;
        this.amazonItemAdapter = amazonItemAdapter;
    }

    public void loadDataFromAmazon() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = BASE_URL + movieTitle.replaceAll(" ", "%20");
        requestQueue.add(new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                parseJSONArray(jsonArray);
                amazonItemAdapter.notifyDataSetChanged();
            }
        }, null));
    }

    private void parseJSONArray(JSONArray jsonArray) {
        int size = jsonArray.length();
        for(int i = 0; i < size; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String detailPageUrl = jsonObject.getJSONArray("DetailPageURL").getString(0);
                String posterUrl = jsonObject.getJSONArray("MediumImage").getJSONObject(0).getJSONArray("URL").getString(0).replace("SL160","SL250");

                JSONArray itemAttributesJSONArray = jsonObject.getJSONArray("ItemAttributes");
                JSONObject jsonObject1 = itemAttributesJSONArray.getJSONObject(0);
                String title = jsonObject1.getJSONArray("Title").getString(0);

                JSONArray OfferSummaryJSONArray = jsonObject.getJSONArray("OfferSummary");
                JSONObject jsonObject2 = OfferSummaryJSONArray.getJSONObject(0);
                JSONArray LowestNewPriceJSONArray = jsonObject2.getJSONArray("LowestNewPrice");
                JSONObject jsonObject3 = LowestNewPriceJSONArray.getJSONObject(0);
                String price = jsonObject3.getJSONArray("FormattedPrice").getString(0);



                AmazonItem amazonItem = new AmazonItem(title, price, detailPageUrl, posterUrl);
                amazonItemsContainer.getAmazonItemArrayList().add(amazonItem);
            } catch(JSONException exception) {
                exception.printStackTrace();
            }
        }
    }
}
