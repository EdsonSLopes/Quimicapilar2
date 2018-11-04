package com.pilar.quimica.quimicapilar.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.pilar.quimica.quimicapilar.DAO.ConfiguracaoFirebase;
import com.pilar.quimica.quimicapilar.Entidades.Usuarios;
import com.pilar.quimica.quimicapilar.Helper.Base64Custom;
import com.pilar.quimica.quimicapilar.Helper.Preferencias;
import com.pilar.quimica.quimicapilar.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadNome;
    private EditText edtCadEmail;
    private EditText edtCadSenha;
    private EditText edtCadConfirmaSenha;
    private Button btnCadSalvar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtCadNome = (EditText)findViewById(R.id.edtCadNome);
        edtCadEmail = (EditText)findViewById(R.id.edtCadEmail);
        edtCadSenha = (EditText)findViewById(R.id.edtCadSenha);
        edtCadConfirmaSenha = (EditText)findViewById(R.id.edtCadCnfirmaSenha);
        btnCadSalvar =(Button)findViewById(R.id.btnCadSalvar);

        btnCadSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCadSenha.getText().toString().equals("")|| edtCadConfirmaSenha.getText().toString().equals("")||
                        edtCadEmail.getText().toString().equals("") || edtCadNome.getText().toString().equals("")) {


                    Toast.makeText(CadastroActivity.this,"Preencha todos os campos.", Toast.LENGTH_LONG).show();


                } else if  (edtCadSenha.getText().toString().equals(edtCadConfirmaSenha.getText().toString())){

                    usuarios = new Usuarios();
                    usuarios.setNome(edtCadNome.getText().toString());
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setSenha(edtCadSenha.getText().toString());
                    usuarios.setCofirmaSenha(edtCadConfirmaSenha.getText().toString());

                    cadastrarUsuario();
                }else {

                    Toast.makeText(CadastroActivity.this,"Senhas não conferem", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()

        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,"Usuário Cadastrado com sucesso!!",Toast.LENGTH_LONG).show();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.Salvar();

                    Preferencias preferencias = new Preferencias(CadastroActivity.this);
                    preferencias.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                    abrirLoginUsuario();

                }else {
                    String erroExcecao = ("");
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "Digite uma novo E-mail ";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Esse E-mail Já está cadastrado no sistema";
                    }catch (Exception e){
                        erroExcecao = "erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this," "  + erroExcecao,Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

