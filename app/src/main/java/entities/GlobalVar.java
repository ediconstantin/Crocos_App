package entities;

public class GlobalVar {

    private String appKey = "";
    private Boolean initialized = false;
    private static GlobalVar instance;

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

    static {
        instance = new GlobalVar();
    }
}