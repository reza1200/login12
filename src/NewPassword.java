import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class NewPassword extends JDialog {
    private JPanel newPassword;
    private JButton goButton;
    private JLabel NewPassword;
    private JLabel Confirm;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;

    public NewPassword (JDialog parent)
    {
        super(parent);
        setTitle(" Card2Cart New Password Form");
        setContentPane(newPassword);
        setMinimumSize(new Dimension(350,450));
        setModal(true);
        setLocationRelativeTo(parent);
        //setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Password_User();
            }
        });
        setVisible(true);
    }
    private void Password_User() {

        String txtpassword = String.valueOf(passwordField1.getPassword());
        String txtpassword1= String.valueOf(passwordField2.getPassword());

        //If filed is empty display the Error message
        if ( txtpassword1.isEmpty()) {
            JOptionPane.showInternalMessageDialog( NewPassword.this,
                    "Please fill all the blank field","Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!txtpassword.equals(txtpassword1))
        {
            JOptionPane.showMessageDialog(this,
                    "Confirmed password does not match","Try again",JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Adding new user to database
        User = addUserToDatabase(txtpassword,txtpassword1);
        if (User != null)
            dispose();
        else{
            JOptionPane.showInternalMessageDialog( this,"Please fill all the blank","Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    //global variable
    public user User;
    //returning valid user object
    private user addUserToDatabase( String txtpassword,String txtpassword1)
    {
        user User = null;
        final String DB_URL;
        DB_URL = "jdbc:mysql://localhost/card2cart?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            //concreting to database successfully
            Statement st = conn.createStatement();
            String sql;
            sql = "INSERT INTO NewPassword (passwordField1,passwordField2) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,txtpassword);
            preparedStatement.setString(2,txtpassword1);

            //Insert row into the table and execute the quarry
            int addRows = preparedStatement.executeUpdate();
            if (addRows > 0)
            {
                User = new user();
                User.txtpassword= txtpassword;
                User.txtpassword1 = txtpassword1;

            }
            //closing connection
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return User;
    }

    //Main method
    public static void main(String[] args)
    {   //object of registration form
        NewPassword new_form = new NewPassword(null);
        user User = new_form.User;
        if (User != null)
        {
            System.out.println("successfully registered new password : " + User.txtpassword);
        }
        else{
            System.out.println("Registration canceled");

        }
    }

}
