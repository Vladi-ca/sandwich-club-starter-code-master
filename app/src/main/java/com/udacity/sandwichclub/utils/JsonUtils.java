package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;
// otherwise writes "unhandled exception"
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // the attribute belonging to the json key for also know as item of the sandw, ..., ...
    public static final String NAME_KEY = "name";
    public static final String MAIN_NAME_KEY = "mainName";
    public static final String ALSO_KNOW_AS_KEY = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    public static final String DESCRIPTION_KEY = "description";
    public static final String IMAGE_KEY = "image";
    public static final String INGREDIENTS_KEY = "ingredients";

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        JSONObject sandwichJSON;

        String mainName = "";
        List<String> alsoKnown = new ArrayList<>();
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        List<String> ingredients = new ArrayList<>();

        try {

            sandwichJSON = new JSONObject(json);

            // will get name from json
            JSONObject nameSwObject = sandwichJSON.getJSONObject(NAME_KEY);

            // will get main name of the sandwich from json
            mainName = nameSwObject.getString(MAIN_NAME_KEY);


            // JSONArray for also known as
            JSONArray alsoKnownAsJson = nameSwObject.getJSONArray(ALSO_KNOW_AS_KEY);
            for (int i = 0; i < alsoKnownAsJson.length(); i++) {
                String alsoKnownAsNow = alsoKnownAsJson.getString(i);
                alsoKnown.add(alsoKnownAsNow);
            }

            // place of origin object value
            placeOfOrigin = sandwichJSON.getString(PLACE_OF_ORIGIN_KEY);

            // object of description
            description = sandwichJSON.getString(DESCRIPTION_KEY);

            // image object value
            image = sandwichJSON.getString(IMAGE_KEY);

            // JSONArray for ingredients list
            JSONArray ingredientsJson = sandwichJSON.getJSONArray(INGREDIENTS_KEY);

            for (int j = 0; j < ingredientsJson.length(); j++) {
                String ingredientsNow = ingredientsJson.getString(j);
                ingredients.add(ingredientsNow);
            }

            // returns parsed Sandwich JSON items
            return new Sandwich(mainName, alsoKnown, placeOfOrigin, description, image, ingredients);

        } catch (final JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Parsing JSON error", e);
            return null;
        }


    }
}
