import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationForm extends JDialog{
    private JTextField textName;
    private JTextField textEmail;
    private JTextField textConfirmEmail;
    private JTextField textUsername;
    private JPasswordField passwordField1;
    private JPasswordField passwordConfirm;
    private JButton cancelButton;
    private JButton confirmButton;
    private JPanel RegisterPanel;
    //contracture
    public RegistrationForm(JDialog parent)
    {
        super(parent);
        setTitle("Create a new account");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(430,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //create a registered new user
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        //Closing the application
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    //implement the register user
    private void registerUser() {
        String name = textName.getText();
        String email = textEmail.getText();
        String confirmEmail = textConfirmEmail.getText();
        String username = textUsername.getText();
        String password = String.valueOf(passwordField1.getPassword());
        String confirm_password = String.valueOf(passwordConfirm.getPassword());

        //If filed is empty display the Error message
        if (name.isEmpty() || email.isEmpty() || confirmEmail.isEmpty() ||  username.isEmpty()
                || password.isEmpty()) {
            JOptionPane.showInternalMessageDialog( RegistrationForm.this,
                    "Please fill all the blank field","Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirm_password))
        {
            JOptionPane.showMessageDialog(this,
                    "Confirmed password does not match","Try again",JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Adding new user to database
        User = addUserToDatabase(name,email,confirmEmail,username,password,confirm_password );
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
    private user addUserToDatabase(String name,String email, String confirmEmail,String username,
                                   String password,String confirm_password)
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
            String sql = "INSERT INTO users (name, email, confirmEmail,username, password,confirm_password) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,confirmEmail);
            preparedStatement.setString(4,username);
            preparedStatement.setString(5,password);
            preparedStatement.setString(6,confirm_password);

            //Insert row into the table and execute the quarry
            int addRows = preparedStatement.executeUpdate();
            if (addRows > 0)
            {
                User = new user();
                User.name = name;
                User.email = email;
                User.confirmEmail = confirmEmail;
                User.username = username;
                User.password = password;
                User.confirm_password = confirm_password;

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
        RegistrationForm my_form = new RegistrationForm(null);
        user User = my_form.User;
        if (User != null)
        {
            System.out.println("successfully registered : " + User.name);
        }
        else{
            System.out.println("Registration canceled");

        }
    }
}
