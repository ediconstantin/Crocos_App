package ro.ase.pdm.crocos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText nEmailField;
    private EditText nPasswordField;
    private Button nLoginBtn;
    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener nAutchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nAuth = FirebaseAuth.getInstance();

        nEmailField = findViewById(R.id.etEmail);
        nPasswordField = findViewById(R.id.etPass);
        nLoginBtn = findViewById(R.id.btnLogin);

        nAutchListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!= null){
                    if(nEmailField.getText().toString().toLowerCase().contains("@csie.ase.ro")){
                        startActivity(new Intent(MainActivity.this, TeacherActivity.class));
                    }
                    if(nEmailField.getText().toString().toLowerCase().contains("@stud.ase.ro")){
                        startActivity(new Intent(MainActivity.this,StudentActivity.class));
                    }
                    nEmailField.setText("");
                    nPasswordField.setText("");
                }
            }
        };


        nLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        nAuth.addAuthStateListener(nAutchListener);
    }

    private void startSignIn(){
        String email = nEmailField.getText().toString();
        String pass = nPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(MainActivity.this, "At least one field is empty", Toast.LENGTH_LONG).show();
        }
        else {
            nAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Sign-in problem", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
