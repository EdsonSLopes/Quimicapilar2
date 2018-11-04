package com.pilar.quimica.quimicapilar.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {


    private Context context;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "SalaoMais.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);

        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String identificadorUsuario, String nomeUsuario){
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();
    }
    public String getidentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getnome(){
        return preferences.getString(CHAVE_NOME, null);
    }

}
