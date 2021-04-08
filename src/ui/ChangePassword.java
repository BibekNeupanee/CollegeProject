package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class ChangePassword extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPasswordField txtOldPassword;
	private JPasswordField txtNewPassword;
	private JPasswordField txtConfirmPassword;
	JButton btnSave;

	private String username;
	private JButton btnCancel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword frame = new ChangePassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ChangePassword(String username) {
		this();
		this.username = username;
		System.out.println(username);
	}

	public ChangePassword() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 687, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblOldPassword = new JLabel("Old Password:");

		txtOldPassword = new JPasswordField();
		txtOldPassword.setColumns(64);

		JLabel lblNewPassword = new JLabel("New Password:");

		JLabel lblConfirmPassword = new JLabel("Confirm Password");

		txtNewPassword = new JPasswordField();

		txtConfirmPassword = new JPasswordField();

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oldPassword_Validation();
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(97)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblOldPassword)
								.addComponent(lblNewPassword).addComponent(lblConfirmPassword))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtConfirmPassword).addComponent(txtNewPassword)
								.addComponent(txtOldPassword, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
						.addContainerGap(379, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING,
						gl_contentPane.createSequentialGroup().addContainerGap(479, Short.MAX_VALUE)
								.addComponent(btnSave).addGap(18).addComponent(btnCancel).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(68)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblOldPassword)
								.addComponent(txtOldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewPassword)
								.addComponent(txtNewPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(
								gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblConfirmPassword)
										.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE).addGroup(gl_contentPane
								.createParallelGroup(Alignment.BASELINE).addComponent(btnCancel).addComponent(btnSave))
						.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}

	public void password_validation() {

		Connection con = DatabaseConnection.getConnection();

		String user = "Update user set password = ? WHERE username = ?";
		PreparedStatement prep;
		try {
			prep = con.prepareStatement(user);
			prep.setString(1, txtConfirmPassword.getText());
			prep.setString(2, this.username);
			prep.executeUpdate();
			JOptionPane.showMessageDialog(btnSave, "Password Updated Sucessfully.");
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(btnSave, "Failed to update password");
		}
	}

	public void oldPassword_Validation() {
		Connection con = DatabaseConnection.getConnection();

		String query = "SELECT * FROM user WHERE username = ? AND password = ?";

		PreparedStatement ps;
		boolean isvalid = false;
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, this.username);
			ps.setString(2, txtOldPassword.getText());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				isvalid = true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (isvalid) {
			if (txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
				password_validation();
			} else {
				JOptionPane.showMessageDialog(btnSave, "New Password and Confirm Password Didnot Matched.");
			}
		} else {
			JOptionPane.showMessageDialog(btnSave, "Old Password Incorrect.");
		}
	}
}
