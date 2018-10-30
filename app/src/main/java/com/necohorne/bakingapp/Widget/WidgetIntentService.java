package com.necohorne.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.necohorne.bakingapp.Models.Ingredients;
import com.necohorne.bakingapp.Models.Recipe;
import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.Utils.JsonUtils;
import com.necohorne.bakingapp.Utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

import static com.necohorne.bakingapp.Utils.NetworkUtils.recipeUrl;

public class WidgetIntentService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET = "com.necohorne.bakingapp.Widget.action.update_widget";
    public static int currentRecipeIndex = 0;

    public WidgetIntentService() {
        super("WidgetIntentService");
    }

    public static void startActionUpdateWidget(Context context){
        Intent intent = new Intent(context, WidgetIntentService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            if(ACTION_UPDATE_WIDGET.equals(action)) {
                try {
                    handleActionUpdateWidget();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleActionUpdateWidget() throws IOException {
        ArrayList<Recipe> recipeList = JsonUtils.getRecipeList(NetworkUtils.getResponseFromHttpUrl(recipeUrl()));

        if(currentRecipeIndex >= recipeList.size() -1){
            currentRecipeIndex = 0;
        }else currentRecipeIndex++;

        Recipe recipe = recipeList.get(currentRecipeIndex);
        String title = recipe.getName();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < recipe.getIngredients().size(); i++) {
            Ingredients ingredients = recipe.getIngredients().get(i);
            sb.append(ingredients.getIngredient());
            sb.append(" ");
            sb.append(ingredients.getMeasure());
            sb.append(" ");
            sb.append(ingredients.getQuantity());
            sb.append("\n");
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewAppWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_details_tv);
        NewAppWidget.updateAppWidget(this, appWidgetManager, appWidgetIds, title, sb.toString());
    }

}
