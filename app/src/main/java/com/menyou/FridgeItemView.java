package com.menyou;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import java.util.Random;

public class FridgeItemView extends LinearLayout
{
    @InjectView(R.id.txt_title)
    TextView txtTitle;

    @InjectView(R.id.btn_remove)
    ImageButton btnRemove;

    public FridgeItemView(Context context)
    {
        this(context, null);
    }

    public FridgeItemView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public FridgeItemView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.view_fridge_item, this, true);
        ButterKnife.inject(this);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.padding);
        setPadding(padding*2, padding, padding*2, padding);
    }

    public void setIngredient(Ingredient ingredient)
    {
        txtTitle.setText(ingredient.title);
    }

    @OnClick(R.id.btn_remove)
    public void onRemoveClicked()
    {

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

}
