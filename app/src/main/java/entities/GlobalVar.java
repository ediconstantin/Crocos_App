package entities;

import java.util.List;

public class GlobalVar {

    private String appKey = "";
    private Boolean initialized = false;
    private static GlobalVar instance;
    private static List<Category> categories;

    private GlobalVar() {
    }

    public static GlobalVar getInstance() {
        return instance;
    }

    public String getAppKey() {
        return appKey;
    }

    public Boolean getInitialized() {
        return initialized;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
        this.initialized = true;
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public static void setCategories(List<Category> categories) {
        GlobalVar.categories = categories;
    }

    static {
        instance = new GlobalVar();
    }
}