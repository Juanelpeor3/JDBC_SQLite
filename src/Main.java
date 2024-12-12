import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void connect() {
        // connection string
        Scanner scanner=new Scanner(System.in);
        System.out.println("Elige cual consulta quiere ejecutar \n\t1:Unir tablas departments y employees_realistic\n\t" +
                "2:Unir tablas employee_projects y projects\n\t" +
                "3:Unir tablas order_items y orders\n\t" +
                "4:Consulta Costos salariales (punto c)\n\t" +
                "5:Consulta Combinar presupuestos (punto d)\n\t" +
                "6:Consulta Calcular fraccion (punto e)");
        int opcion=scanner.nextInt();
        if(opcion==1){
            Opcion1();
        }
        if(opcion==2){
            Opcion2();
        }
        if(opcion==3){
            Opcion3();
        }
        if(opcion==4){
            Opcion4();
        }
        if(opcion==5){
            Opcion5();
        }
        if(opcion==6){
            Opcion6();
        }

    }

    public static void main(String[] args) {
        connect();
    }
    public static void Opcion1(){
        //Unir tablas departments y employees_realistic
        var url = "jdbc:sqlite:src/company_database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Se ha conectado a la base de datos");
            String sql="Select d.department_id,d.department_name,d.manager_id,er.employee_id,er.first_name,er.last_name,er.department_id,er.hire_date,er.salary,er.position " +
                    "from departments d " +
                    "join employees_realistic er on d.department_id=er.department_id";

            try(Statement statement=conn.createStatement()){
                ResultSet resultSet=statement.executeQuery(sql);
                do{
                    String department_id=resultSet.getString(1);
                    String department_name=resultSet.getString(2);
                    String manager_id=resultSet.getString(3);

                    String employee_id=resultSet.getString(4);
                    String first_name=resultSet.getString(5);
                    String last_name=resultSet.getString(6);

                    String department_id2=resultSet.getString(7);
                    String hire_date=resultSet.getString(8);
                    String salary=resultSet.getString(9);
                    String position=resultSet.getString(10);
                    System.out.println(department_id+" | "+department_name+" | "+manager_id+" | "+employee_id+" | "+first_name+" | "+last_name+" | "+department_id2+" | "+hire_date+" | "+salary+" | "+position);
                }while(resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void Opcion2(){
        //Unir tablas employee_projects y projects
        var url = "jdbc:sqlite:src/company_database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Se ha conectado a la base de datos");
            String sql="select ep.employee_id,ep.project_id,ep.hours_worked,p.project_id,p.project_name,p.department_id,p.budget,p.start_date,p.end_date " +
                    "from employee_projects ep " +
                    "join projects p on ep.project_id=p.project_id";


            try(Statement statement=conn.createStatement()){
                ResultSet resultSet=statement.executeQuery(sql);
                do{
                    String employee_id=resultSet.getString(1);
                    String project_id=resultSet.getString(2);
                    String hours_worked=resultSet.getString(3);

                    String project_id2=resultSet.getString(4);
                    String project_name=resultSet.getString(5);
                    String department_id=resultSet.getString(6);

                    String budget=resultSet.getString(7);
                    String start_date=resultSet.getString(8);
                    String end_date=resultSet.getString(9);

                    System.out.println(employee_id+" | "+project_id+" | "+hours_worked+" | "+project_id2+" | "+project_name+" | "+department_id+" | "+budget+" | "+start_date+" | "+end_date);
                }while(resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void Opcion3(){
        //Unir tablas order_items y orders
        var url = "jdbc:sqlite:src/company_database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Se ha conectado a la base de datos");
            String sql="Select ot.order_item_id,ot.order_id,ot.product_name,ot.quantity,ot.price,o.order_id,o.customer_id,o.order_date,o.amount " +
                    "from order_items ot " +
                    "join orders o on ot.order_id=o.order_id";

            try(Statement statement=conn.createStatement()){
                ResultSet resultSet=statement.executeQuery(sql);
                do{
                    String order_item_id=resultSet.getString(1);
                    String order_id=resultSet.getString(2);
                    String product_name=resultSet.getString(3);

                    String quantity=resultSet.getString(4);
                    String price=resultSet.getString(5);
                    String order_id2=resultSet.getString(6);

                    String customer_id=resultSet.getString(7);
                    String order_date=resultSet.getString(8);
                    String amount=resultSet.getString(9);
                    System.out.println(order_item_id+" | "+order_id+" | "+product_name+" | "+quantity+" | "+price+" | "+order_id2+" | "+customer_id+" | "+order_date+" | "+amount);
                }while(resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void Opcion4(){
        //Unir tablas order_items y orders
        var url = "jdbc:sqlite:src/company_database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Se ha conectado a la base de datos");
            String sql="Select er.first_name,((er.salary/1900)*ep.hours_worked) as costo_salarial " +
                    "From employees_realistic er " +
                    "join employee_projects ep on er.employee_id = ep.employee_id";

            try(Statement statement=conn.createStatement()){
                ResultSet resultSet=statement.executeQuery(sql);
                do{
                    String first_name=resultSet.getString(1);
                    String costo_salarial=resultSet.getString(2);
                    System.out.println(first_name+" | "+costo_salarial);
                }while(resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void Opcion5(){
        //Unir tablas order_items y orders
        var url = "jdbc:sqlite:src/company_database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Se ha conectado a la base de datos");
            String sql="Select p.project_name,SUM((er.salary/1900)*ep.hours_worked) as costo_salarial,p.budget as presupuesto " +
                    "From employees_realistic er " +
                    "join employee_projects ep on er.employee_id = ep.employee_id " +
                    "join projects p on p.project_id=ep.project_id " +
                    "group by p.project_name, p.budget";

            try(Statement statement=conn.createStatement()){
                ResultSet resultSet=statement.executeQuery(sql);
                do{
                    String project_name=resultSet.getString(1);
                    String costo_salarial=resultSet.getString(2);
                    String presupuesto=resultSet.getString(2);
                    System.out.println(project_name+" | "+costo_salarial+" | "+presupuesto);
                }while(resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void Opcion6(){
        //Unir tablas order_items y orders
        var url = "jdbc:sqlite:src/company_database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Se ha conectado a la base de datos");
            String sql="Select p.project_name,SUM((er.salary/1900)*ep.hours_worked) as costo_salarial,p.budget as presupuesto, ((SUM((er.salary/1900)*ep.hours_worked)))/p.budget as fraccion_presupuesto " +
                    "From employees_realistic er " +
                    "join employee_projects ep on er.employee_id = ep.employee_id " +
                    "join projects p on p.project_id=ep.project_id " +
                    "group by p.project_name, p.budget";

            try(Statement statement=conn.createStatement()){
                ResultSet resultSet=statement.executeQuery(sql);
                do{
                    String project_name=resultSet.getString(1);
                    String costo_salarial=resultSet.getString(2);
                    String presupuesto=resultSet.getString(3);
                    String fraccion_presupuesto=resultSet.getString(4);
                    System.out.println(project_name+" | "+costo_salarial+" | "+presupuesto+" | "+fraccion_presupuesto);
                }while(resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
