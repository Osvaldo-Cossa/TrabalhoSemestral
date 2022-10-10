/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Modelo;

/**
 *
 * @author Hp ProBook
 */
public abstract class Pessoa {
    String nome;
    int idade;
    String morada;
    String telefone;
    String estadoCivil;
    String sexo;
 
    public Pessoa() {
    }
    

    public Pessoa(String nome, int idade, String morada, String telefone, String estadoCivil, String sexo) {
        this.nome = nome;
        this.idade = idade;
        this.morada = morada;
        this.telefone = telefone;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
    }
 
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    
    
}
