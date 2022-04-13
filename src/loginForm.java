import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginForm extends JDialog{
    private JPanel icon;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton btnLogin;
    private JButton btnCreateAccount;
    private JButton forgotPasswordButton;
    private JButton btnCancel;
    private JPanel LoginPanel;

    //constructor
    public loginForm(JFrame parent)
    {
        super(parent);
        setTitle(" Card2Cart Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(400,475));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // return true if the user has been registered otherwise will go to registration form
        boolean has_Registre_User = connectToDatabase();
        final user[] User = {loginForm.User};
        if(User[0] != null)
        {
            setLocationRelativeTo(null);
            setVisible(true);
        }

        else{
            btnCreateAccount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    RegistrationForm registrationForm = new RegistrationForm( loginForm.this);
                    user User = registrationForm.User;
                }
            });
            dispose();
        }




        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = String.valueOf(passwordField1.getPassword());

                User[0] = getAuthenticatedUser(username, password);
                if (User[0] != null)
                {
                    JOptionPane.showMessageDialog( loginForm.this, "You are successfully logged in ");
                            dispose();
                }
                else{
                    JOptionPane.showMessageDialog( loginForm.this, "Username or password is invalid",
                            "Try again",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        //setting the cancel button
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }

        });
        setVisible(true);

        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



        //Clear the username and password field if do not want to log_in

    }
    private boolean connectToDatabase()
    {
        boolean has_Registre_User = false;
        return has_Registre_User;

    }

    public static user User;
    private user getAuthenticatedUser(String username, String password)
    {
        user User =null;
        //return User;
        final String DB_URL = "jdbc:mysql://localhost/card2cart?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                User= new user();
                User.name = resultSet.getString("name");
                User.email = resultSet.getString("email");
                User.confirmEmail = resultSet.getString("confirmEmail");
                User.username = resultSet.getString("username");
                User.password = resultSet.getString("password");
                User.confirm_password = resultSet.getString("confirm_password");
                stmt.close();
                conn.close();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }


        return User;
    }
    public static void main(String[] args)
    {
        loginForm loginForm = new loginForm(null);
        user User = loginForm.User;
        if (User != null)
        {
            System.out.println("Successful Authentication of " + User.name);
            System.out.println("                        email " + User.email);
            System.out.println("                        username " + User.username);



        }
        else{
            System.out.println(" Not Authorized ");

        }
    }

    private void actionPerformed(ActionEvent e) {
        textField1.setText(null);
        passwordField1.setText(null);

    }
}
