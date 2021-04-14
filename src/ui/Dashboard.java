package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Dashboard extends JFrame {

	private static final long serialVersionUID = 1L;

	public String Username;

	private GroupLayout gl_contentPane;

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JPanel panel;

	private JTextField txtUsername;
	private JTextField txtSearch;

	private static JTable tblBillHistory;

	private JButton btnPrintBill;
	private JButton btnRefresh;
	private JButton btnNewBill;
	private JButton btnSearch;
	private JButton btnEditBill;
	private JButton btnBalance;
	private JButton btnIncome;

	private JMenuItem mnuitmChangePassword;
	private JMenu menuSettings;
	private JMenuItem mnuitmLogout;
	private JMenuBar menuBar;

	private JLabel lblSearch;
	private JLabel lblBillHistory;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard frame = new Dashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Dashboard(String username) {
		this();
		Username = username;
		username(Username);
	}

	public Dashboard() {
		setTitle("Dashboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 869, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		lblSearch = new JLabel("Search:");

		txtSearch = new JTextField();
		txtSearch.setColumns(10);

		scrollPane = new JScrollPane();

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});

		lblBillHistory = new JLabel("Bill History:");

		btnNewBill = new JButton("Add Bill");
		btnNewBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				NewBill newBill = new NewBill();
				newBill.setVisible(true);

			}
		});

		btnEditBill = new JButton("Edit Bill");
		btnEditBill.setEnabled(false);
		btnEditBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				edit_bill();

			}
		});

		panel = new JPanel();

		btnPrintBill = new JButton("Print Bill");

		btnPrintBill.setEnabled(false);
		btnPrintBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		txtUsername = new JTextField();
		txtUsername.setEditable(false);
		txtUsername.setBorder(null);
		txtUsername.setColumns(10);

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				txtSearch.setText("");
				database_thing();

			}
		});

		btnBalance = new JButton("Balance");
		btnBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				balance();
			}
		});

		btnIncome = new JButton("Income");
		btnIncome.setVisible(false);
		btnIncome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Income income = new Income();
				income.setVisible(true);
			}
		});
		group_layout();

		menuBar = new JMenuBar();
		panel.add(menuBar);

		menuSettings = new JMenu("Settings");
		menuBar.add(menuSettings);

		mnuitmChangePassword = new JMenuItem("Change Password");
		mnuitmChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				change_password();

			}
		});
		menuSettings.add(mnuitmChangePassword);

		mnuitmLogout = new JMenuItem("Logout");
		mnuitmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LoginPage loginpage = new LoginPage();
				loginpage.setVisible(true);
				dispose();

			}
		});
		menuSettings.add(mnuitmLogout);

		tblBillHistory = new JTable();
		tblBillHistory.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Costumer Name", "Date", "Bill No.", "id" }) {
					Class[] columnTypes = new Class[] { Object.class, Object.class, String.class, Integer.class };

					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}

					boolean[] columnEditables = new boolean[] { true, true, true, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
		tblBillHistory.getColumnModel().getColumn(0).setPreferredWidth(92);
		tblBillHistory.getColumnModel().getColumn(3).setResizable(false);
		tblBillHistory.getColumn("id").setPreferredWidth(0);
		tblBillHistory.getColumn("id").setMinWidth(0);
		tblBillHistory.getColumn("id").setWidth(0);
		tblBillHistory.getColumn("id").setMaxWidth(0);
		scrollPane.setViewportView(tblBillHistory);
		contentPane.setLayout(gl_contentPane);
		database_thing();
		tblBillHistory.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				btnPrintBill.setEnabled(true);
				editbill_validation();
			}
		});
	}

	public void balance() {
		Balance balance = new Balance(this.Username);
		balance.setVisible(true);
	}

	public void group_layout() {
		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
								.createParallelGroup(Alignment.TRAILING)
								.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
										.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblBillHistory)
												.addGap(35).addComponent(btnRefresh))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 97,
														GroupLayout.PREFERRED_SIZE)
												.addGap(18).addComponent(btnSearch)))
										.addGap(228)))
								.addPreferredGap(ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
								.addComponent(btnIncome).addGap(32).addComponent(btnBalance).addGap(34)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnEditBill, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnPrintBill, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnNewBill, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addComponent(lblSearch, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 684, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblSearch).addComponent(panel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(30)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(Alignment.TRAILING,
														gl_contentPane.createParallelGroup(Alignment.BASELINE)
																.addComponent(lblBillHistory).addComponent(btnRefresh))
												.addGroup(gl_contentPane.createSequentialGroup().addGap(68)
														.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPane.createSequentialGroup().addGap(29)
														.addComponent(btnEditBill).addGap(18)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
																.addComponent(btnPrintBill).addComponent(btnBalance)
																.addComponent(btnIncome))))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(50).addComponent(btnNewBill))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(50)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnSearch))))
						.addContainerGap()));

	}

	public void username(String username) {
		this.txtUsername.setText(username);
		if (username.equals("admin")) {
			this.btnIncome.setVisible(true);
		}

	}

	public void editbill_validation() {
		if (this.Username.equals("admin")) {
			btnEditBill.setEnabled(true);
		}
	}

	public void change_password() {
		ChangePassword changepassword = new ChangePassword(Username);
		changepassword.setVisible(true);
	}

	public void edit_bill() {
		DefaultTableModel model = (DefaultTableModel) tblBillHistory.getModel();

		// i = the index of the selected row
		int i = tblBillHistory.getSelectedRow();
		int id = (int) model.getValueAt(i, 3);
		EditBill editbill = new EditBill(id,this.Username);
		editbill.setVisible(true);
	}

	public void database_thing() {
		DefaultTableModel model = (DefaultTableModel) tblBillHistory.getModel();
		int rowCount = model.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		Connection con = DatabaseConnection.getConnection();

		String query = "SELECT customer, date, invoice_no,id FROM invoice";

		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4) });

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void search() {
		DefaultTableModel model = (DefaultTableModel) tblBillHistory.getModel();
		Connection con = DatabaseConnection.getConnection();
		String query = "SELECT * FROM invoice WHERE customer LIKE '%" + txtSearch.getText() + "%' OR invoice_no ='"
				+ txtSearch.getText() + "'";
		PreparedStatement ps;

		try {
			int a = 0;
			int rowCount = model.getRowCount();
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			for (int i = rowCount - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			while (rs.next()) {
				a++;
				model.addRow(new Object[] { rs.getString(3), rs.getDate(5), rs.getString(2), rs.getInt(1) });
			}
			if (a < 1) {
				JOptionPane.showMessageDialog(null, "No Data Found ");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static void AddRowToJTable(Object[] dataRow) {
		DefaultTableModel model = (DefaultTableModel) tblBillHistory.getModel();
		model.addRow(dataRow);
	}
}
