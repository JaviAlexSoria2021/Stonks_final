package com.bryansoria.socialappv4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout inputEmail,inputPassword;
    Button btnLogin;
    TextView createNewAccount;
    ProgressDialog mLoadingBar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnLogin);
        createNewAccount=findViewById(R.id.createNewAccount);
        mLoadingBar= new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        //Crear nueva cuenta
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Iniciar Sesion
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtamptLogin();
            }
        });
    }

    //Este metodo controlara los datos introducidos por el usuario
    private void AtamptLogin() {
        String email = inputEmail.getEditText().getText().toString();
        String password = inputPassword.getEditText().getText().toString();

        if (email.isEmpty() || !email.contains("@gmail")){
            messageError(inputEmail,"Email no válido");
        }else if (password.isEmpty() || password.length()<5){
            messageError(inputPassword, "Contraseña no válida");
        }else{
            mLoadingBar.setTitle("Login");;
            mLoadingBar.setMessage("Por favor espere.");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Se ha iniciado sesion correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class); //,SetupActivity.class asi antes
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, task.getException().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void messageError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
}