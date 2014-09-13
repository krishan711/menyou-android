package com.menyou;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import java.util.List;
import java.util.Random;

public class MealTypeChoiceView extends LinearLayout implements MealChoiceView.Provider
{
    @InjectView(R.id.v_meal_1)
    MealChoiceView vMeal1;

    @InjectView(R.id.v_meal_2)
    MealChoiceView vMeal2;

    @InjectView(R.id.v_meal_3)
    MealChoiceView vMeal3;

    @InjectView(R.id.v_meal_4)
    MealChoiceView vMeal4;

    @InjectView(R.id.v_meal_5)
    MealChoiceView vMeal5;

    @InjectView(R.id.v_meal_6)
    MealChoiceView vMeal6;

    @InjectView(R.id.v_meal_7)
    MealChoiceView vMeal7;

    @InjectView(R.id.txt_title)
    TextView txtTitle;

    @InjectView(R.id.btn_select_all)
    Button selectAll;

    public List<FoodItem> foodItems;

    public MealTypeChoiceView(Context context)
    {
        this(context, null);
    }

    public MealTypeChoiceView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public MealTypeChoiceView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_meal_type_choice, this, true);
        ButterKnife.inject(this);
    }

    public void setTitle(String title)
    {
        txtTitle.setText(title);
    }

    public void setFoodItems(List<FoodItem> foodItems)
    {
        this.foodItems = foodItems;
        vMeal1.setProvider(this);
        vMeal2.setProvider(this);
        vMeal3.setProvider(this);
        vMeal4.setProvider(this);
        vMeal5.setProvider(this);
        vMeal6.setProvider(this);
        vMeal7.setProvider(this);
    }

    @OnClick(R.id.btn_select_all)
    public void onSelectAllClicked()
    {
        vMeal1.setSelected(true);
        vMeal2.setSelected(true);
        vMeal3.setSelected(true);
        vMeal4.setSelected(true);
        vMeal5.setSelected(true);
        vMeal6.setSelected(true);
        vMeal7.setSelected(true);
        selectAll.setVisibility(View.GONE);
    }

    @Override
    public FoodItem getMenuItem()
    {
        return foodItems.get(new Random().nextInt(foodItems.size()));
    }

}
