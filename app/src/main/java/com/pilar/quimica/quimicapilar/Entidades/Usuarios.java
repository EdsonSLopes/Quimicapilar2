package com.pilar.quimica.quimicapilar.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.pilar.quimica.quimicapilar.DAO.ConfiguracaoFirebase;

import java.util.HashMap;
import java.util.Map;

public class Usuarios {

    private String id;
    private String Nome;
    private String Email;
    private String Senha;
    private String CofirmaSenha;
    //private String id;


    public Usuarios() {
    }

    public void Salvar(){

        DatabaseReference referenciaFaribase = ConfiguracaoFirebase.getFirebase();
        referenciaFaribase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude

    public Map<String, Object> toMap(){

        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("Nome", getNome());
        hashMapUsuario.put("Email", getEmail());
        hashMapUsuario.put("Senha", getSenha());
        hashMapUsuario.put("ConfirmaSenha", getCofirmaSenha());

        return hashMapUsuario;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCofirmaSenha() {
        return CofirmaSenha;
    }

    public void setCofirmaSenha(String cofirmaSenha) {
        CofirmaSenha = cofirmaSenha;
    }
}
