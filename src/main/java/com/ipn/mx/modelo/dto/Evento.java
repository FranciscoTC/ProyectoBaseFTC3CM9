/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dto;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FranciscoTC
 */

/*Primero deberá cmplir con la interfaz de la serialización*/
public class Evento implements Serializable{
    /*Crearemos atributios privados a la clase*/
    private int idEvento;
    private String nombreEvento;
    private String sede;
    private Date fechaInicio;
    private Date fechaTermino;
    
    /*Contructor*/
    public Evento(){
        //No se agrega nada
    }
    /*
    //Debemos contar con métodos de tipo mutator y de tipo accesor
    //Método mutator
    public void setIdEvento(int idEvento){
        this.idEvento = idEvento;
    }
    //Método accesor
    public int getIdEvento(){
        return idEvento;
    }
    //Estos anteriores se pueden agregar dierectamente con el IDE
    */
    // Sección de los métodos getters y setters
    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    //toString nos sirve para mostrar una representación entendible en texto plano de la info en él.
    //Es impresindible
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idEvento=").append(getIdEvento()).append("\n");
        sb.append(", nombreEvento=").append(nombreEvento).append("\n");
        sb.append(", sede=").append(sede).append("\n");
        sb.append(", fechaInicio=").append(fechaInicio).append("\n");
        sb.append(", fechaTermino=").append(fechaTermino).append("\n");
        return sb.toString();
    }
    
    //Método main
    public static void main(String[] args) {
        //Datos que se enviarán para un nuevo registro en la base de datos
        Evento e = new Evento();
        e.setIdEvento(4);
        e.setNombreEvento("Jornada ISC 2021");
        e.setSede("Sala 14");
        // la fecha la podemos recibir con un new date o entre comillas con un Date.valueOf("aaaa-mm-dd");
        e.setFechaInicio(Date.valueOf("2020-10-05"));
        e.setFechaTermino(Date.valueOf("2020-10-09"));

        //System.out.println(e);
        
        //Sección para el envio de credenciales y establecer conexión
        String user = "root"; //usuario
        String pwd = "root"; //contraseña
        String url = "jdbc:mysql://localhost:3306/3CM9?serverTimezone=America/Mexico_City&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false"; //jdbc:baseDeDatos://puerto/nombreBaseDeDatos //Cadena de conexión
        //Definición del driver
        String driverMySql = "com.mysql.cj.jdbc.Driver"; //Esto en mysql 8
        //String driverMySQL = "com.mysql.jdbc.Driver"; //Para mysql 5.7
        Connection conexion = null;
        //Aquí iba un fragmento try catch pero lo dejaremos al IDE que lo asigne
        try { // Intento de conexión con la base de datos
            Class.forName(driverMySql);// Con esta línea nos da la opción en el foco de agregar en try catch
            conexion = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        //insertar(conexion, e);
        //actualizar(conexion, e);
        //borrar(conexion, e);
        //consultar(conexion, e);
        
        //PreparedStatement ps = null;
    }

    //Métodos
    //Método consultar
    private static void consultar(Connection conexion, Evento e) {
        PreparedStatement ps;
        ResultSet rs = null; //Debe ir acompañando a la consulta select
        String SQL_SELECT="select * from Evento where idEvento = ?";
        try {//07 Octubre 2020
            ps = conexion.prepareStatement(SQL_SELECT);
            ps.setInt(1, e.getIdEvento());
            rs = ps.executeQuery();//necesito que me traiga un conjunto de elementos
            while (rs.next()) {//Mientras haya elementos
                e.setIdEvento(rs.getInt("idEvento"));
                e.setNombreEvento(rs.getString("nombreEvento"));
                e.setSede(rs.getString("sede"));
                //e.getFechaInicio(rs.getString("fechaInicio"));
                //e.getFechaTermino(rs.getString("fechaTermino"));
            }
            System.out.println(e);
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Método borrar
    private static void borrar(Connection conexion, Evento e) {
        PreparedStatement ps;
        String SQL_DELETE="delete from Evento where idEvento = ?";
        try {
            ps = conexion.prepareStatement(SQL_DELETE);
            ps.setInt(1, e.getIdEvento());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Método actuaizar
    private static void actualizar(Connection conexion, Evento e) {
        PreparedStatement ps;
        String SQL_UPDATE="update Evento set nombreEvento= ?, sede = ?, fechaInicio = ?, fechaTermino = ? where idEvento = ?";
        try {
            ps = conexion.prepareStatement(SQL_UPDATE);
            ps.setString(1, e.getNombreEvento());
            ps.setString(2, e.getSede());
            ps.setDate(3, e.getFechaInicio());
            ps.setDate(4, e.getFechaTermino());
            ps.setInt(5, e.getIdEvento());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Método insertar
    private static void insertar(Connection conexion, Evento e) {
        PreparedStatement ps = null;
        String SQL_INSERT = "insert into Evento(nombreEvento, sede, fechaInicio, fechaTermino) values (?, ?, ?, ?)";
        try {
            ps = conexion.prepareStatement(SQL_INSERT);
            ps.setString(1, e.getNombreEvento());
            ps.setString(2, e.getSede());
            ps.setDate(3, e.getFechaInicio());
            ps.setDate(4, e.getFechaTermino());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
