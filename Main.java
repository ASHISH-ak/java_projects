import java.security.spec.RSAOtherPrimeInfo;
import java.sql.*;
import java.util.Scanner;


public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String user_name ="root";
    private static final String password = "root@123root";

    public static void main(String args[]) throws ClassNotFoundException,SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection connection = DriverManager.getConnection(url,user_name,password);
            while(true){
                System.out.println("WELCOME IN HOTEL RESERVATION MANAGMENT SYSTEM");
                Scanner sc = new Scanner(System.in);
                System.out.println("1.ADD Reservation");
                System.out.println("2. View Reservations");
                System.out.println("3. Get room no");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("6. Exit");
                int choice = sc.nextInt();

                switch (choice){
                    case 1:addReservation(connection,sc);
                    break;
                    case 2:viewReservation(connection);
                    break;
                    case 3:getRoomno(connection,sc);
                    break;
                    case 4:updateReservation(connection,sc);
                    break;
                    case 5:deleteReservation(connection,sc);
                    break;
                    case 6:exit(connection,sc);
                    connection.close();
                    sc.close();
                    return;
                    default:
                        System.out.println("Invalid input . please enter valid choice");
                }
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }


    public static void addReservation(Connection connection,Scanner sc){

        try {
            System.out.print("Enter Guest name");
            String name = sc.next();
            System.out.print("Enter Room No");
            int room_no = sc.nextInt();
            System.out.print("Enter Contact no");
            int contact_no = sc.nextInt();

           String sql = "INSERT INTO reservation (guest_name, room_number, contact_number) " +
                    "VALUES ('" + name + "', " + room_no + ", " + contact_no + ")";

            try ( Statement st = connection.createStatement();){
                int affected_rows = st.executeUpdate(sql);
                if(affected_rows>0){
                    System.out.println("Reservation added successfully");
                }
                else {
                    System.out.println("failed.Reservation not added");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }


    }
    public static void viewReservation(Connection connection){
        String sql = "SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM reservation";

        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("reservation_id");
                String name = rs.getString("guest_name");
                int room_no = rs.getInt("room_number");
                int contact_no = rs.getInt("contact_number");
                Timestamp reservationdate = rs.getTimestamp("reservation_date");

                System.out.print("id :"+id);
                System.out.print(" name :"+name);
                System.out.print(" room no :"+room_no);
                System.out.print(" contact no :"+contact_no);
                System.out.print(" Reservation Date :"+reservationdate);
                System.out.println();
                System.out.println();
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public  static void getRoomno(Connection connection,Scanner sc){
        System.out.print("Enter id :");
        int id = sc.nextInt();
        System.out.print("Enter name :");
        String name = sc.next();

        String sql= "SELECT * FROM reservation WHERE reservation_id="+id+" AND "+" guest_name="+"'"+name+"'";

        try{
            Statement st=connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int room_no = rs.getInt("room_number");
                System.out.println("Room no is :"+room_no);
            }
        }
        catch (SQLException e){
           e.printStackTrace();
        }

    }
    public static void updateReservation(Connection connection,Scanner sc){
        System.out.print("Enter reservation id to update");
        int id = sc.nextInt();
        System.out.print("Enter new Guest name :");
        String name=sc.next();
        System.out.print("Enter new room no.");
        int room_no = sc.nextInt();
        System.out.print("Enter new contact no.");
        String contact_no = sc.next();

        String sql = "UPDATE reservation SET guest_name='"+name+"',"+"room_number="+room_no+","+
                "contact_number= '"+contact_no+"'  WHERE reservation_id = "+ id;

        try{
            Statement st = connection.createStatement();
            int affectedrows=st.executeUpdate(sql);

            if(affectedrows>0){
                System.out.println("Update successfull");
            }
            else {
                System.out.println("Update unsuccessfull");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void deleteReservation(Connection connection,Scanner sc){
        System.out.print("Enter id for deletion :");
        int id=sc.nextInt();

        String sql = "DELETE FROM reservation WHERE reservation_id="+id;

        try{
            Statement st = connection.createStatement();
            int affectedRows=st.executeUpdate(sql);

            if(affectedRows>0){
                System.out.println("DELETION Successfull");
            }
            else {
                System.out.println("DELETION unsuccessfull");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void exit(Connection connection,Scanner sc){
        System.out.println("Exiting from the system");
    }
}