package com.menyou;

import android.app.Activity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.InjectView;

import java.util.ArrayList;

public class ChoicesActivity extends Activity
{
    @InjectView(R.id.mtcv_breakfast)
    MealTypeChoiceView vBreakfast;

    @InjectView(R.id.mtcv_lunch)
    MealTypeChoiceView vLunch;

    @InjectView(R.id.mtcv_dinner)
    MealTypeChoiceView vDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
        ButterKnife.inject(this);

        vBreakfast.setTitle("Breakfast");
        vLunch.setTitle("Lunch");
        vDinner.setTitle("Dinner");

        ArrayList<FoodItem> breakfasts = new ArrayList<FoodItem>();
        FoodItem item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        breakfasts.add(item);

        vBreakfast.setFoodItems(breakfasts);
        vLunch.setFoodItems(breakfasts);
        vDinner.setFoodItems(breakfasts);
    }

}
