package utils;

import java.util.HashMap;
import java.util.Map;

public class JSONifier {

    public static String StringToJSON(String[] properties, String[] values){

        if(properties.length == values.length){
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            for(int i=0; i<properties.length; i++){
                builder.append("\"" + properties[i] + "\":\"" + values[i] + "\"");
                if(i < properties.length - 1){
                    builder.append(",");
                }
            }
            builder.append("}");

            return builder.toString();
        }

        return "";
    }

    public static String ArrayToJSON(){
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
}
