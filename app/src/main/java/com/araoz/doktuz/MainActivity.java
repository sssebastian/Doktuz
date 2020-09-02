package com.araoz.doktuz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.araoz.doktuz.modelos.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;
    Button mButtonRegister;


    SharedPreferences mPref;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    AlertDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextInputEmail    = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin       = findViewById(R.id.btnLogin);
        mButtonRegister       = findViewById(R.id.btnRegister);

        //instanciamos con Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //diferenciamos users
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        mDialog = new SpotsDialog.Builder().setContext(MainActivity.this).setMessage("Espere un momento").build();
        String selectedUser = mPref.getString("user","");






        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });



        mDialog = new SpotsDialog.Builder().setContext(MainActivity.this).setMessage("Procesando ☺").build();



    }

     void register() {

         final String email = mTextInputEmail.getText().toString();
         final String password = mTextInputPassword.getText().toString();

         if (!email.isEmpty() && !password.isEmpty()) {
             if (password.length() >= 6) {
                   mDialog.show();
                 mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         mDialog.hide();
                         if (task.isSuccessful()) {
                             String id = mAuth.getCurrentUser().getUid();
                             saveUser(id, email,password);
                             Toast.makeText(MainActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                         }
                         else {
                             Toast.makeText(MainActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }
             else {
                 Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
             }
         }
         else {
             Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
         }

    }

    private void saveUser(String id,  String email,String password) {
        String selectedUser = mPref.getString("user", "");

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        if (selectedUser.equals("user")) {
            mDatabase.child("Users").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }















    private void login() {

        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "El login se realizó exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                            startActivity(intent);
                           /* String user = mPref.getString("user", "");
                            if (user.equals("client")) {
                                Intent intent = new Intent(LoginActivity.this, MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, MapDriverActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }*/
                        } else {
                            Toast.makeText(MainActivity.this, "La contraseña o el password son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();
                    }
                });
            } else {
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "La contraseña y el email son obligatorios", Toast.LENGTH_SHORT).show();
        }

    }
}
