package utils;

public class HTTPResponse {

    private int status;
    private String response;
    private boolean result;

    public HTTPResponse(){
        status = 0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(){
        if(status != 0){
            result = (status < 400);
        }
    }
}
