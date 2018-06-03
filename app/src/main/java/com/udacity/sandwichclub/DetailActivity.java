package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
// import of list utility because of list of Also known as and Ingredients of the sandwich
import java.util.List;
import java.util.zip.Inflater;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // textViews of sandwich items in detail activity
    public ImageView imageIv;

    public TextView alsoKnownTv;
    public TextView alsoKnownHeadingTv;

    public TextView placeOfOriginTv;
    public TextView placeOfOriginHeadingTv;

    public TextView descriptionTv;
    public TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageIv = findViewById(R.id.image_iv);
        // another items of the sendwich below the image
        alsoKnownTv = findViewById(R.id.also_known_tv);
        // to hide also known if its not found
        alsoKnownHeadingTv = findViewById(R.id.also_known_heading);

        placeOfOriginTv = findViewById(R.id.origin_tv);
        placeOfOriginHeadingTv = findViewById(R.id.origin_heading);

        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // populating UI by object sandwich
        populateUI(sandwich);
        Picasso.with(this).load(sandwich.getImage()).into(imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // if (sandwich.getImage().isEmpty()) {

        /*if (imageIv.getVisibility() != GONE) {
            imageIv.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageIv.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            imageIv.setLayoutParams(layoutParams);*/
        // }

        // if also known as is empty then set unvissible, LIST!! thus delimeter
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownHeadingTv.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
            // else set setText and populate
        } else {
            alsoKnownTv.setText(android.text.TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }

        // populate Place of Origin
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginTv.setVisibility(View.GONE);
            placeOfOriginHeadingTv.setVisibility(View.GONE);
        } else {
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        // populate Ingredients - LIST!!
        if (sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setVisibility(View.GONE);
        } else {
            ingredientsTv.setText(android.text.TextUtils.concat("-", android.text.TextUtils.join(",\n ", sandwich.getIngredients())));
        }

        // populate Description (not a list)
        if (sandwich.getDescription().isEmpty()) {
            descriptionTv.setVisibility(View.GONE);
        } else {
            descriptionTv.setText(sandwich.getDescription());
        }

    }
}
