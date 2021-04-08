package ui;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	
	private JLabel lblUserName;
	private JLabel lblPassword;
	
	private JButton btnLogin;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage loginPage = new LoginPage();
					loginPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginPage() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginPage.class.getResource("/icons/bill.png")));
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblUserName = new JLabel("Username:");
		lblUserName.setBounds(87, 68, 65, 14);
		contentPane.add(lblUserName);

		txtUsername = new JTextField();
		txtUsername.setBounds(151, 65, 86, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(87, 105, 65, 14);
		contentPane.add(lblPassword);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validation();
			}
		});

		btnLogin.setBounds(151, 133, 89, 23);
		contentPane.add(btnLogin);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(151, 102, 86, 20);
		contentPane.add(txtPassword);
		txtPassword.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				validation();
			}
		});
	}

	public void validation() {
		String username = txtUsername.getText();
		@SuppressWarnings("deprecation")
		String password = txtPassword.getText();
		Connection con = DatabaseConnection.getConnection();
		String query = "SELECT * FROM user WHERE username = ? AND password = ?";

		PreparedStatement ps;
		boolean isvalid = false;
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				isvalid = true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (isvalid) {
			Dashboard dashboard = new Dashboard(txtUsername.getText());
			dashboard.setVisible(true);
			dispose();
		} else {
			JOptionPane.showMessageDialog(btnLogin, "Wrong Username or Password");
		}
	}
}
