package ro.ase.pdm.crocos;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText nEmailField;
    private EditText nPasswordField;
    private Button nLoginBtn;
    private ProgressBar nLoginProgress;
    private FirebaseAuth nAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nAuth = FirebaseAuth.getInstance();

        nEmailField = (EditText)findViewById(R.id.etEmail);
        nPasswordField = (EditText)findViewById(R.id.etPass);
        nLoginBtn = (Button)findViewById(R.id.btnLogin);
        nLoginProgress = (ProgressBar)findViewById(R.id.login_progress);


        nLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = nEmailField.getText().toString();
                String pass = nPasswordField.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(MainActivity.this, "At least one field is empty", Toast.LENGTH_LONG).show();
                }

                else if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)){
                    nLoginProgress.setVisibility(View.VISIBLE);
                    nAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                sentToMainScene();
                            }
                            else {
                                String errormessage = task.getException().getMessage();
                                Toast.makeText(MainActivity.this, errormessage, Toast.LENGTH_LONG).show();
                            }
                            nLoginProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }

                }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = nAuth.getCurrentUser();
        if(currentUser!=null){
            sentToMainScene();
        }
    }

    private void sentToMainScene(){
        if(nEmailField.getText().toString().toLowerCase().contains("@csie.ase.ro")){
            startActivity(new Intent(MainActivity.this, TeacherActivity.class));
            finish();
        }
        if(nEmailField.getText().toString().toLowerCase().contains("@stud.ase.ro")){
            startActivity(new Intent(MainActivity.this,StudentActivity.class));
            finish();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
