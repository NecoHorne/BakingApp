package com.necohorne.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.necohorne.bakingapp.Models.Ingredients;
import com.necohorne.bakingapp.Models.Recipe;
import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.UI.Activities.MainActivity;
import com.necohorne.bakingapp.Utils.JsonUtils;
import com.necohorne.bakingapp.Utils.NetworkUtils;
import com.necohorne.bakingapp.Utils.RecyclerAdapters.RecipeRecyclerAdapter;

import java.io.IOException;
import java.util.ArrayList;

import static com.necohorne.bakingapp.Utils.NetworkUtils.recipeUrl;

public class NewAppWidget extends AppWidgetProvider {



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetId, String title, String ingredients) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.widget_recipe_name, title);
        views.setTextViewText(R.id.widget_recipe_details_tv, ingredients);

        Intent updateIntent = new Intent(context, WidgetIntentService.class);
        updateIntent.setAction(WidgetIntentService.ACTION_UPDATE_WIDGET);

        PendingIntent wateringPendingIntent = PendingIntent.getService(context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.widget_recipe_details_tv, wateringPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        WidgetIntentService.startActionUpdateWidget(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

