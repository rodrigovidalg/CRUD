/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author user
 */
public class Puesto extends Persona {
    private String puestos;
    Conexion cn;
    
    public Puesto(){}
    public Puesto(String puestos) {
        this.puestos = puestos;
    }

    public String getPuestos() {
        return puestos;
    }

    public void setPuestos(String puestos) {
        this.puestos = puestos;
    }
    
    
    //Agregar un nuevo Puesto
    public DefaultTableModel leerPuesto(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn= new Conexion();
            cn.abrir_conexion();
            String query;
            query = "SELECT id_puesto as id, puesto FROM puestos;";
            ResultSet consulta = cn.conexionDB.createStatement().executeQuery(query);
            
            String encabezado[] = {"Puestos Disponibles"};
            tabla.setColumnIdentifiers(encabezado);
            
            String datos[]=new String[2];
            
            while(consulta.next()){
                datos[0] = consulta.getString("puesto");
                datos[1] = consulta.getString("puesto");
        
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println("Error: "+ ex.getMessage());
        }
        return tabla;
    }
    public void agregarPuesto() {
    try {
        PreparedStatement parametro;
        Conexion cn = new Conexion();
        cn.abrir_conexion();
        String query = "INSERT INTO puestos (puesto) VALUES (?);";
        parametro = cn.conexionDB.prepareStatement(query);
        parametro.setString(1, getPuestos());
        
        int executer = parametro.executeUpdate();
        System.out.println("Puesto insertado exitosamente");
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        System.out.println("Algo salió mal: " + ex.getMessage());   
    }
    }
}
