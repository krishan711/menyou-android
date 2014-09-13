package com.menyou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mobi.parchment.widget.adapterview.listview.ListView;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends Activity
{
    @InjectView(R.id.grid_trending)
    GridView gridTrending;

    @InjectView(R.id.lv_last_week)
    ListView listLastWeek;

    @InjectView(R.id.lv_fridge)
    android.widget.ListView listFridge;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.inject(this);

        ArrayList<FoodItem> trendingItems = new ArrayList<FoodItem>();

        FoodItem item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        trendingItems.add(item);
        TrendingAdapter trendingAdapter = new TrendingAdapter(this, trendingItems);
        gridTrending.setAdapter(trendingAdapter);

        ArrayList<FoodItem> lastWeekItems = new ArrayList<FoodItem>();
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add(new Ingredient("Apple 40g", 1.40f));

        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        item = new FoodItem();
        item.title = "Apple";
        item.calCount = 143;
        item.ingredients = ingredients;
        lastWeekItems.add(item);
        listLastWeek.setAdapter(new HorizontalFoodAdapter(this, lastWeekItems));

        listFridge.setAdapter(new FridgeListAdapter(this, lastWeekItems));
    }

    public class TrendingAdapter extends BaseAdapter
    {
        protected Context context;
        private final List<FoodItem> foodItems;

        public TrendingAdapter(Context context, List<FoodItem> foodItems)
        {
            this.context = context;
            this.foodItems = foodItems;
        }

        public FoodItemView getView(int position, View convertView, ViewGroup parent)
        {
            FoodItemView view = (FoodItemView) convertView;
            if (convertView == null)
                view = getNewView();
            view.setFoodItem(getItem(position));
            return view;
        }

        protected FoodItemView getNewView()
        {
            FoodItemView view = new FoodItemView(context);
            view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount()
        {
            return foodItems.size();
        }

        @Override
        public FoodItem getItem(int position)
        {
            return foodItems.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }
    }

    public class HorizontalFoodAdapter extends TrendingAdapter
    {
        public HorizontalFoodAdapter(Context context, List<FoodItem> foodItems)
        {
            super(context, foodItems);
        }

        protected FoodItemView getNewView()
        {
            FoodItemView view = new FoodItemView(context);
            view.setLayoutParams(new AbsListView.LayoutParams(listLastWeek.getMeasuredHeight(), listLastWeek.getMeasuredHeight()));
            return view;
        }
    }

    public class FridgeListAdapter extends BaseAdapter
    {
        protected Context context;
        public List<Ingredient> fridgeItems;

        public FridgeListAdapter(Context context, List<FoodItem> foodItems)
        {
            this.context = context;
            this.fridgeItems = new ArrayList<Ingredient>();
            for (FoodItem item : foodItems)
            {
                fridgeItems.addAll(item.ingredients);
            }
        }

        @Override
        public int getCount()
        {
            return fridgeItems.size();
        }

        @Override
        public Ingredient getItem(int position)
        {
            return fridgeItems.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            FridgeItemView view = (FridgeItemView) convertView;
            if (view == null)
                view = new FridgeItemView(context);
            view.setIngredient(getItem(position));
            return view;
        }
    }

    @OnClick(R.id.btn_shop_now)
    void onShopNowClicked()
    {
        startActivity(new Intent(this, ChoicesActivity.class));
    }

}
