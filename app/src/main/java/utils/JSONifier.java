package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Category;

public class JSONifier {

    public static String StringToJSON(String[] properties, String[] values){

        if(properties.length == values.length){
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            for(int i=0; i<properties.length; i++){
                if(android.text.TextUtils.isDigitsOnly(values[i])){
                    builder.append("\"" + properties[i] + ":" + values[i]);
                } else {
                    builder.append("\"" + properties[i] + "\":\"" + values[i] + "\"");
                }

                if(i < properties.length - 1){
                    builder.append(",");
                }
            }
            builder.append("}");

            return builder.toString();
        }

        return "";
    }

    public static Map<String, String> SimpleJSONToMap(String json){

        Map<String, String> jMap = new HashMap<>();

        json = json.replaceAll("[{}\"]", "");

        String[] jsonArray = json.split(",");

        for(int i = 0; i<jsonArray.length; i++){
            String[] attribute = jsonArray[i].split(":");
            jMap.put(attribute[0], attribute[1]);
        }

        return jMap;
    }

    public static Category jsonToCategory(String jsonData){

        Category category = new Category();

        try {
            JSONObject parser = new JSONObject(jsonData);

            category.setId(parser.getInt("id"));
            category.setName(parser.getString("name"));
            category.setDescription(parser.getString("description"));
            category.setPhoto(parser.getString("photo"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return category;
    }

    public static List<Category> jsonToCategories(String jsonData){
        List<Category> categories = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);

            for(int i=0; i<jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                categories.add(jsonToCategory(jsonObj.toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categories;
    }

    //json to questions
    //json to question
    //json to test
    //json to tests
    //json to category
    //json to categories

}
