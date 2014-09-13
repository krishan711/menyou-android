package com.menyou;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;

import java.util.Random;

public class MealChoiceView extends FrameLayout
{
    @InjectView(R.id.v_overlay)
    View vOverlay;

    @InjectView(R.id.v_food_item)
    FoodItemView vFoodItem;

    private FoodItem foodItem;

    public MealChoiceView(Context context)
    {
        this(context, null);
    }

    public MealChoiceView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public MealChoiceView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.view_meal_choice, this, true);
        ButterKnife.inject(this);
        vFoodItem.setBackgroundColor(getColor());
        setSelected(false);

//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                setSelected(true);
//            }
//        });
    }

    public void setSelected(boolean selected)
    {
        super.setSelected(selected);
        vOverlay.setVisibility(selected ? VISIBLE : GONE);
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

    public void setFoodItem(FoodItem item)
    {
        this.foodItem = item;
        vFoodItem.setFoodItem(item);
    }

}
