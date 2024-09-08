/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import com.mysql.cj.xdevapi.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author user
 */
public class Empleado extends Persona{
    private String codigo;
    private int id;
    private String puesto;
    Conexion cn;
    public Empleado(){}

    public Empleado(int id, String codigo, String puesto, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.codigo = codigo;
        this.id = id;
        this.puesto = puesto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    
    
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn= new Conexion();
            cn.abrir_conexion();
            String query;
            query = "SELECT id_empleado as id, codigo, nombres, apellidos, direccion, telefono, fecha_nacimiento, id_puesto FROM empleados;";
            ResultSet consulta = cn.conexionDB.createStatement().executeQuery(query);
            
            String encabezado[] = {"id","Codigo","Nombres","Apellidos","Direccion","Telefono","Nacimiento", "id_puesto"};
            tabla.setColumnIdentifiers(encabezado);
            
            String datos[]=new String[8];
            
            while(consulta.next()){
                datos[0] = consulta.getString("id");
                datos[1] = consulta.getString("codigo");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                datos[7] = consulta.getString("id_puesto");
                                
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println("Error: "+ ex.getMessage());
        }
        return tabla;
    }
    
    public void agregar() {
    try {
        PreparedStatement parametro;
        Conexion cn = new Conexion();
        cn.abrir_conexion();
        String query = "INSERT INTO empleados (codigo, nombres, apellidos, direccion, telefono, fecha_nacimiento, id_puesto) VALUES (?, ?, ?, ?, ?, ?, ?);";
        parametro = cn.conexionDB.prepareStatement(query);
         parametro.setString(1, getPuesto());
        parametro.setString(2, getCodigo());
        parametro.setString(3, getNombres());
        parametro.setString(4, getApellidos());
        parametro.setString(5, getDireccion());
        parametro.setString(6, getTelefono());
        parametro.setString(7, getFecha_nacimiento());
        
        
        int executer = parametro.executeUpdate();
        System.out.println("Inserto exitoso");
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        System.out.println("Algo salió mal: " + ex.getMessage());   
    }
    }
    public void eliminar(){
        try{
           PreparedStatement parametro;
           cn = new Conexion();
           cn.abrir_conexion();
           String query = "DELETE FROM empleados WHERE id_empleado = ?;";
           parametro = (PreparedStatement) cn.conexionDB.prepareStatement(query);
           parametro.setInt(1, this.getId());
           int executer = parametro.executeUpdate();
           System.out.println("Eliminacion exitosa");
           cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println("Error al eliminar" + ex.getMessage());
        }
    }
    
    public void rellenarComboBox(String tabla, String valor, JComboBox combo){
        String sql = "select * from " + tabla;
        cn.abrir_conexion();
        try{
            ResultSet consulta = cn.conexionDB.createStatement().executeQuery(sql);
            while (consulta.next()){
                combo.addItem(consulta.getString (valor));
            }
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println("Error en el ComboBox: " + ex.getMessage());
        }
    }
}
