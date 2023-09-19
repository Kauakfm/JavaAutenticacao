package com.br.var.solutions.domain.entities;

import org.springframework.boot.autoconfigure.security.SecurityProperties;

public class ValidaUsuario
{
    public enum ValidaUser {
        USER_1("var", "123456"),
        USER_2("kaua.martins", "Teste01&"),
        USER_3("blabla", "blabla");

        private String usuario;
        private String senha;

        ValidaUser(String username, String password) {
            this.usuario = username;
            this.senha = password;
        }

        public String getUsuario() {
            return usuario;
        }

        public String getSenha() {
            return senha;
        }

    }

    public static Boolean validaUsuario(String username, String password) {
        for (ValidaUser validaUser : ValidaUser.values())
        {
            if (validaUser.getUsuario().equalsIgnoreCase(username) && validaUser.getSenha().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public static String returnPassword(String username){
        for(ValidaUser validaUser : ValidaUser.values()){
            if (validaUser.getUsuario().equalsIgnoreCase(username)){
                return validaUser.getSenha();
            }
        }
        return  null;
    }
    public static String returnUsername(String username){
        for(ValidaUser validaUser : ValidaUser.values()){
            if(validaUser.getUsuario().equalsIgnoreCase(username)){
                return validaUser.getUsuario();
            }
        }
        return null;
    }





}






