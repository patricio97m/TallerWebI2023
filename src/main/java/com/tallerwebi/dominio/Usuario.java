package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nombre;
    private java.sql.Date nacimiento;
    private Integer genero;
    private String password;
    private String rol;
    private Boolean activo = false;
    private int ayudasRepartirCartas = 0;
    private int ayudasIntercambiarCartas = 0;
    private int ayudasSumarPuntos = 0;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public int getAyudasRepartirCartas() {
        return ayudasRepartirCartas;
    }
    public void setAyudasRepartirCartas(int ayudasRepartirCartas) {
        this.ayudasRepartirCartas = ayudasRepartirCartas;
    }
    public int getAyudasIntercambiarCartas() {
        return ayudasIntercambiarCartas;
    }
    public void setAyudasIntercambiarCartas(int ayudasIntercambiarCartas) {
        this.ayudasIntercambiarCartas = ayudasIntercambiarCartas;
    }
    public int getAyudasSumarPuntos() {
        return ayudasSumarPuntos;
    }
    public void setAyudasSumarPuntos(int ayudasSumarPuntos) {
        this.ayudasSumarPuntos = ayudasSumarPuntos;
    }
}
