package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Map;

import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;

public class MainActivity extends AppCompatActivity implements Constant {

    private GoogleSignInClient mGoogleSignInClient;
    private SharedPreferences sharedPreferences;
    private String email;
    private String firstname;
    private String lastname;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(Constant.NAIRU_PREFERENCES, MODE_PRIVATE);

        checkIfLoggedIn();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Constant.APP_KEY)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
    }

    private void checkIfLoggedIn(){
        int logged = sharedPreferences.getInt(Constant.LOGGED_IN, 0);
        if(logged == 1){
            email = sharedPreferences.getString(Constant.EMAIL, "");

            loginToApp();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_RESULT_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String message = JSONifier.StringToJSON(new String[]{"token"}, new String[]{account.getIdToken()});

            email = account.getEmail();
            firstname = account.getGivenName();
            lastname = account.getFamilyName();

            executeHttpHandler(message);

        } catch (ApiException e) {
            Toast.makeText(getApplicationContext(),  "signInResult:failed code=" + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void executeHttpHandler(String message){
        HTTPHandler httpHandler = new HTTPHandler(){
            @Override
            protected void onPostExecute(HTTPResponse response){
                if(response.getResult()){
                    savePreferences(response);
                } else {
                    Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        httpHandler.execute(POST_METHOD, API_URL + "/login", message);
    }

    private void savePreferences(HTTPResponse response){

        Map<String, String> jsonMap = JSONifier.SimpleJSONToMap(response.getResponse());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constant.LOGGED_IN, 1);
        editor.putString(Constant.API_KEY,jsonMap.get("token"));
        editor.putString(Constant.IS_ACTIVE, jsonMap.get("isActive"));
        editor.putString(Constant.IS_ADMIN, jsonMap.get("isAdmin"));
        editor.putString(Constant.IS_PROF, jsonMap.get("isProf"));
        editor.putString(Constant.GROUP, jsonMap.get("group"));
        editor.putString(Constant.SERIES, jsonMap.get("series"));
        editor.putString(Constant.EMAIL, email);
        editor.putString(Constant.FIRSTNAME, firstname);
        editor.putString(Constant.LASTNAME, lastname);

        editor.apply();

        loginToApp();
    }

    private void loginToApp(){
        Intent intent;

        if(email.contains("@stud.ase.ro")){
            intent = new Intent(this, TeacherActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent = new Intent(this, StudentActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
