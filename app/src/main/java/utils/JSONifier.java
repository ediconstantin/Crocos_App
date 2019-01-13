package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Category;
import entities.Question;
import entities.Test;

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

    public static List<Question> jsonToQuestions(String jsonData){

        List<Question> questions = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);

            for(int i=0; i<jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                questions.add(jsonToQuestion(jsonObj.toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public static Question jsonToQuestion(String jsonData){

        Question question = new Question();

        try {
            JSONObject parser = new JSONObject(jsonData);

            question.setId(parser.getInt("id"));
            question.setQuestion(parser.getString("question"));
            question.setAns1(parser.getString("ans1"));
            question.setAns2(parser.getString("ans2"));
            question.setAns3(parser.getString("ans3"));
            question.setAns4(parser.getString("ans4"));
            question.setCorrect(parser.getString("correct"));
            question.setFeedback(parser.getInt("feedback"));
            question.setPhoto(parser.getString("photo"));
            question.setMultiple(parser.getInt("multiple"));
            question.setOpen(parser.getInt("open"));
            question.setDuration(parser.getInt("duration"));

            if(parser.has("category")){
                question.setCategory(jsonToCategory(parser.getJSONObject("category").toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return question;

    }

    public static Test jsonToTest(String jsonData){
        Test test = new Test();

        try {
            JSONObject parser = new JSONObject(jsonData);

            test.setId(parser.getInt("id"));
            test.setName(parser.getString("name"));
            test.setDuration(parser.getInt("duration"));
            test.setQuestionsNo(parser.getInt("questionsNumber"));
            test.setRetries(parser.getInt("retries"));
            test.setBackwards(parser.getBoolean("backwards"));
            test.setFeedback(parser.getInt("feedback"));
            test.setPrivacy(parser.getBoolean("isPublic"));

            test.setCategory(jsonToCategory(parser.getJSONObject("category").toString()));

            test.setQuestions(jsonToQuestions(parser.getJSONArray("questions").toString()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return test;
    }




}
