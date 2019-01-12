package utils;

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
}
