package com.pilar.quimica.quimicapilar.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pilar.quimica.quimicapilar.DAO.ConfiguracaoFirebase;
import com.pilar.quimica.quimicapilar.Entidades.Usuarios;
import com.pilar.quimica.quimicapilar.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private TextView tvAbreCadastro;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;
    private TextView tvRecSenha;
    private DatabaseReference referenceFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        tvAbreCadastro = (TextView)findViewById(R.id.tvAbreCadastro);
        tvRecSenha = (TextView)findViewById(R.id.tvRecSenha);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")) {

                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());

                    validarLogin();

                } else {
                    Toast.makeText(LoginActivity.this, " Preencha os campos de E-mail é Senha", Toast.LENGTH_SHORT).show();

                }

            }

        });

        //BOTAO TELA CADASTRO
        tvAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroUsuario();
            }
        });

        //BOTAO TELA RESET SENHA
        tvRecSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreRecuperaSenha();
            }
        });
    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(),usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){


                    abrirTelaHistorico();
                    Toast.makeText(LoginActivity.this,"Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"E-mail ou Senha inválida.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //  botao login para abrir tela de historico
    public void abrirTelaHistorico(){

        Intent intentabrirTelaHistorico = new Intent(LoginActivity.this, HistoricoActivity.class);
        startActivity(intentabrirTelaHistorico);
    }
    // botao salvar Cadastro Usuario
    public void abreCadastroUsuario(){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    // Botao Recupera Senha
    public void abreRecuperaSenha(){
        Intent intent = new Intent(LoginActivity.this,ResetActivity.class );
        startActivity(intent);
    }
}
