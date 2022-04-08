/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misql;

import data.Preferencias;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jesue
 */
public class Conexion {

    static String BD = "codigoqr";

    private static Connection cnx = null;

    public static Connection obtener() {
        String RUTA= Preferencias.get(Preferencias.Key.DB_ROUTE);
        String USUARIO = Preferencias.get(Preferencias.Key.DB_USER);
        String PASSWORD = Preferencias.get(Preferencias.Key.DB_PASSWORD);
        try {
            cnx = DriverManager.getConnection("jdbc:mysql://"+RUTA+"/"+BD+"?useSSL=false&allowPublicKeyRetrieval=true"
                    , USUARIO, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex.getMessage());
            /*JOptionPane.showMessageDialog(null, "Se perdio la conexion con el servidor. \n"
                    + "Por favor intente de nuevo mas tarde");
    */
        }
        return cnx;
    }

    public static void cerrar() {
        if (cnx != null) {
            try {
                cnx.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
