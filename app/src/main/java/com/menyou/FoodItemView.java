package com.menyou;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import java.util.Random;

public class FoodItemView extends FrameLayout
{
    @InjectView(R.id.img_food)
    ImageView imgFood;

    @InjectView(R.id.txt_cals)
    TextView txtCals;

    @InjectView(R.id.txt_trending_count)
    TextView txtTitle;

    @InjectView(R.id.v_cal_holder)
    View vCalHolder;

    public FoodItemView(Context context)
    {
        this(context, null);
    }

    public FoodItemView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public FoodItemView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.view_food_item, this, true);
        ButterKnife.inject(this);
    }

    public void showCalories(boolean showCals)
    {
        vCalHolder.setVisibility(showCals ? VISIBLE : GONE);
    }

    public void setFoodItem(FoodItem foodItem)
    {
        setBackgroundColor(getColor());
        if (foodItem.imageResId > 0)
            imgFood.setImageResource(foodItem.imageResId);
        else
            imgFood.setImageBitmap(null);
        txtTitle.setText(foodItem.title);
        txtCals.setText(String.valueOf(foodItem.calCount));
    }

    public int getColor()
    {
        int mix = Color.WHITE;
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // mix the color
        red = (red + Color.red(mix)) / 2;
        green = (green + Color.green(mix)) / 2;
        blue = (blue + Color.blue(mix)) / 2;

        return Color.rgb(red, green, blue);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }

}
