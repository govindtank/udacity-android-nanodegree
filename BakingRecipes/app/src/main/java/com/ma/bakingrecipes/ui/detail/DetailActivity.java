package com.ma.bakingrecipes.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ma.bakingrecipes.R;
import com.ma.bakingrecipes.ui.detail.ingredients.IngredientFragment;
import com.ma.bakingrecipes.ui.detail.ingredients.IngredientsActivity;
import com.ma.bakingrecipes.ui.detail.steps.StepDescriptionActivity;
import com.ma.bakingrecipes.ui.detail.steps.StepDescriptionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements ItemFragment.OnItemClickListener {

    private final String TAG = DetailActivity.class.getName();
    private final String KEY_RECIPE_NAME = "recipe_name";
    private final String KEY_DESCRIPTION_NUMBER = "description_number";

    private boolean isTablet;
    private String recipeName;

    @BindView(R.id.pie_name)
    TextView pieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        if(savedInstanceState != null ){
            recipeName = savedInstanceState.getString(KEY_RECIPE_NAME);
        } else{
            if (getIntent() != null && getIntent().getExtras() != null &&
                    getIntent().getExtras().containsKey(KEY_RECIPE_NAME)) {

                Log.d(TAG, "passed recipe name: " + getIntent().getExtras().getString(KEY_RECIPE_NAME));
                recipeName = getIntent().getExtras().getString(KEY_RECIPE_NAME);
            }

            Bundle bundle = new Bundle();
            bundle.putString(KEY_RECIPE_NAME, recipeName);

            ItemFragment fragment = new ItemFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_container, fragment)
                    .commit();
            Log.d(TAG, "RECIPE NAME " + recipeName);
        }

        pieName.setText(recipeName);

        // check if device is a tablet
        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public void onItemSelected(int position) {
        if (isTablet) {
            Bundle bundle = new Bundle();
            bundle.putString("recipe_name", recipeName);

            if (position == 0) {
                IngredientFragment fragment = new IngredientFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            } else {
                StepDescriptionFragment fragment = new StepDescriptionFragment();
                bundle.putInt(KEY_DESCRIPTION_NUMBER, position -1);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
        } else {
            Intent intent;
            if (position == 0) {
                intent = new Intent(DetailActivity.this, IngredientsActivity.class);
                Log.d(TAG, "OPEN ING");

            } else {
                intent = new Intent(DetailActivity.this, StepDescriptionActivity.class);
                Log.d(TAG, "OPEN STEPS");
            }
            intent.putExtra(KEY_DESCRIPTION_NUMBER, position);
            intent.putExtra(KEY_RECIPE_NAME, recipeName);
            startActivity(intent);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_RECIPE_NAME, recipeName);
        super.onSaveInstanceState(outState);
    }
}
