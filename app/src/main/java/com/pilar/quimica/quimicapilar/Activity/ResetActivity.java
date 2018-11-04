package com.pilar.quimica.quimicapilar.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.pilar.quimica.quimicapilar.R;

public class ResetActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Button btnAlterarPassword;
    private EditText edtAlterarPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        btnAlterarPassword = (Button) findViewById(R.id.btnAlterarPassword);
        edtAlterarPassword = (EditText) findViewById(R.id.edtAlterarPassword);
        autenticacao = FirebaseAuth.getInstance();

        //   recuperação de senha

        btnAlterarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = edtAlterarPassword.getText().toString();

                if(TextUtils.isEmpty(userEmail)){

                    Toast.makeText(ResetActivity.this, "Por favor digite um E-mail válido",Toast.LENGTH_SHORT).show();

                }else {
                    //  autenticação ao recuperar senha

                    autenticacao.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(ResetActivity.this,"por favor, verifique sua conta de email, se você deseja redefinir sua senha",Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(ResetActivity.this,LoginActivity.class));

                            }else {

                                String message= task.getException().getMessage();
                                Toast.makeText(ResetActivity.this," Ocorreu um erro " + message,Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });

    }

}

