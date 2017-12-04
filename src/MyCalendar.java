
import java.sql.*;

/**
 * Created by Reid Nolan on 12/03/2017
 * MyCalendar Class
 * Class for managing appointment calendar with database in MySQL
 * @author Reid Nolan
 * @since 12/03/2017
 * @version 1.0
 */
public class MyCalendar
{
    private static final String URL = "jdbc:mysql://localhost/schedule?&useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "password";

    /**
     * main method
     * @param args args
     */
    public static void main(String[] args){
        MyCalendar myCalendar = new MyCalendar();

        System.out.println("JavaII_HW6_AppointmentCalendar");
        createTestAppointments(URL, USER_NAME, PASSWORD);
        printMainMenu();

        while(true){
            boolean stringIsValid = false;
            while (!stringIsValid)
            {
                String answer = Validator.getString("Enter selection: ");

                if (answer.equalsIgnoreCase("A")){
                    myCalendar.createNewAppointment();
                    stringIsValid = true;
                }
                else if (answer.equalsIgnoreCase("C")){
                    myCalendar.cancelAppointment();
                    stringIsValid = true;
                }
                else if (answer.equalsIgnoreCase("L")){
                    myCalendar.checkAppointments();
                    stringIsValid = true;
                }
                else if (answer.equalsIgnoreCase("E")){
                    System.out.println("Goodbye!");
                    System.exit(999);
                }
                else{
                    System.out.println("Error! Invalid input. Try again.");
                }
                printMainMenu();
            }
        }
    }

    /**
     * class constructor
     */
    private MyCalendar(){

    }

    /**
     * displays the application main menu
     */
    private static void printMainMenu(){
        System.out.println("\n---Main Menu---");
        System.out.println("A)dd a new appointment\nC)ancel an existing appointment\nL)ist a day's appointments\nE)xit the application");
    }

    /**
     *
     * @param URL URL
     * @param USER_NAME USER_NAME
     * @param PASSWORD PASSWORD
     */
    private static void createTestAppointments(String URL, String USER_NAME, String PASSWORD){
        try{
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            Statement st = connection.createStatement();

            st.execute("CREATE TABLE Appointments(description VARCHAR(30), date VARCHAR(30), startTime VARCHAR (30), endTime VARCHAR (30), cancelled VARCHAR(30))");

            st.execute("INSERT INTO Appointments VALUES('Doctor Walker', '12-04-2017', '1045', '1145', '0')");
            st.execute("INSERT INTO Appointments VALUES('Movie with Hailee', '12-08-2017', '1800', '1900', '0')");
            st.execute("INSERT INTO Appointments VALUES('Interview w/IBM', '01-04-2017', '0900', '0930', '0')");
            st.execute("INSERT INTO Appointments VALUES('P/T conference', '01-09-2018', '1630', '1830', '0')");
            st.execute("INSERT INTO Appointments VALUES('X-mas Shopping', '12-24-2013', '2358', '2359', '1')");
            System.out.println("\nTest appointment table was created successfully.");

            connection.close();
        }
        catch (Exception e){
            System.out.println("\nTest appointment table was not created.");
            //e.printStackTrace();
        }
    }

    /**
     * creates a new appointment
     */
    private void createNewAppointment(){
        try{
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println();

            String description = Validator.getString("Enter the description of the appointment: ");
            String date = Validator.getString("Enter the date of the appointment(MM-DD-YYYY): ");
            String startTime = Validator.getString("Enter the start time of the appointment(00:00-23:59): ");
            String endTime = Validator.getString("Enter the end time of the appointment(00:00-23:59): ");
            String cancelled = "0";

            PreparedStatement stat1 = connection.prepareStatement("INSERT INTO Appointments(" + "description," + "date," + "startTime," + "endTime," + "cancelled)" + "VALUES(?,?,?,?,?)");

            stat1.setString(1, description);
            stat1.setString(2, date);
            stat1.setString(3, startTime);
            stat1.setString(4, endTime);
            stat1.setString(5, cancelled);
            stat1.execute();

            System.out.println("The appointment has been added.");
            connection.close();
        }
        catch (Exception e){
            System.out.println("The appointment could not be added.");
            //e.printStackTrace();
        }
    }

    /**
     * sets the status of a specified appointment to cancelled
     */
    private void cancelAppointment(){
        try{
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println();

            String description = Validator.getString("Enter the description of the event you want to cancel: ");
            String date = Validator.getString("Enter the date of the event you want to cancel(MM-DD-YYYY): ");
            String startTime = Validator.getString("Enter the start time of the event you want to cancel(00:00-23:59): ");
            String endTime = Validator.getString("Enter the end time of the event you want to cancel(00:00-23:59): ");

            PreparedStatement stat1 = connection.prepareStatement("UPDATE Appointments SET cancelled = '1' WHERE description = '"+ description+"' AND date = '"+ date+"' AND startTime = '"+startTime+"' AND endTime = '"+endTime+"'");
            stat1.execute();

            System.out.println("The appointment has been canceled.");
            connection.close();
        }
        catch (Exception e){
            System.out.println("The appointment could not be canceled.");
            //e.printStackTrace();
        }
    }

    /**
     * returns appointment details
     */
    private void checkAppointments(){
        try{
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println();

            String date = Validator.getString("What date would you like to check?(MM-DD-YYYY): ");
            String query = "SELECT * FROM Appointments WHERE date = '" + date + "'";

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()){
                for(int i = 1 ; i <= columnsNumber; i++){
                    if(i == 1){
                        System.out.print("Description: ");
                    }
                    else if(i == 2){
                        System.out.print("Date: ");
                    }
                    else if(i == 3){
                        System.out.print("Starting Time: ");
                    }
                    else if(i == 4){
                        System.out.print("Ending Time: ");
                    }
                    else if (i == 5){
                    System.out.print("Status: " );
                    }
                    else {/*doNothing();*/}

                    if (i == 5){
                        if (rs.getString(i).equals("0")){
                            System.out.println("Scheduled");
                        }
                        else if (rs.getString(i).equals("1")){
                            System.out.println("Cancelled");
                        }
                        else {/*doNothing();*/}
                    }
                    else{
                        System.out.println(rs.getString(i));
                    }
                }
            }
            connection.close();
        }
        catch (Exception e){
            System.out.println("Details for the specified date could not be retrieved");
            //e.printStackTrace();
        }
    }
}