package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;

public class EditBill extends JFrame {

	private static final long serialVersionUID = 1L;

	public int id;
	public String username;

	private GroupLayout gl_layeredPane;
	private GroupLayout gl_layeredPane_1;
	private GroupLayout gl_contentPane;

	private JLayeredPane layeredPane;
	private JLayeredPane layeredPane_1;

	private JPanel contentPane;

	private JTable tblBill;

	private JTextField txtCustomerName;
	private JTextField txtParticulars;
	private JTextField txtQuantity;
	private JTextField txtRate;
	private JTextField txtTotal;
	private JTextField txtAdvance;
	private JTextField txtBalance;

	private JDateChooser dateChooser;
	private Date date;

	private JLabel lblParticulars;
	private JLabel lblQuantity;
	private JLabel lblRate;
	private JLabel lblBalance;
	private JLabel lblAdvance;
	private JLabel lblTotal;

	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnUpdate;
	private JButton btnAdd;
	private JButton btnDelete;

	private JLabel lblDate;
	private JLabel lblAdress;
	private JLabel lblTitle;
	private JLabel lblCustomerName;

	private JScrollPane scrollPane;
	private JLabel lblInvoiceNumber;
	private JTextField txtInvoiceNumber;
	private JCheckBox chckbxBillClearance;
	private JTextField txtBillClearance;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditBill frame = new EditBill();
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EditBill(int id, String username) {
		this();
		this.id = id;
		this.username = username;
		System.out.println(username);
		user_validation();
		addingdata_to_table();
	}

	public EditBill() {
		setTitle("Edit Bill");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 873, 714);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);

		layeredPane = new JLayeredPane();

		btnSave = new JButton("Save");

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		layeredPane_1 = new JLayeredPane();

		gl_contentPane();

		lblParticulars = new JLabel("Particulars:");
		lblQuantity = new JLabel("Quantity:");
		lblRate = new JLabel("Rate:");

		txtParticulars = new JTextField();
		txtParticulars.setColumns(30);

		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);

		txtRate = new JTextField();
		txtRate.setColumns(10);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Double.parseDouble(txtQuantity.getText());
					Double.parseDouble(txtRate.getText());
					boolean kabTaken = false;
					int a = tblBill.getModel().getRowCount();
					for (int i = 0; i < a; i++) {
						if (txtParticulars.getText().equals(tblBill.getValueAt(i, 0).toString())
								&& Double.parseDouble(txtQuantity.getText()) == Double
										.parseDouble(tblBill.getValueAt(i, 1).toString())
								&& Double.parseDouble(txtRate.getText()) == Double
										.parseDouble((tblBill.getValueAt(i, 2).toString()))) {
							kabTaken = true;
						}
					}
					if (txtParticulars.getText().isEmpty() || txtQuantity.getText().isEmpty()
							|| txtRate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Above information must be filled.");
					} else if (Double.parseDouble(txtQuantity.getText()) < 0
							&& Double.parseDouble(txtRate.getText()) < 0) {
						JOptionPane.showMessageDialog(null, "Quantity And Rate must not be less Than 0.");
					} else if (kabTaken) {
						JOptionPane.showMessageDialog(null, "Value already in table. Cannot add");
					} else {
						kabTaken = false;
						adding_into_table();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Quantity And Rate must not be in Character.");
				}
			}
		});

		btnUpdate = new JButton("Update");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Double.parseDouble(txtQuantity.getText());
					Double.parseDouble(txtRate.getText());
					if (txtParticulars.getText().isEmpty() || txtQuantity.getText().isEmpty()
							|| txtRate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Above information must be filled.");
					} else if (Double.parseDouble(txtQuantity.getText()) < 0
							&& Double.parseDouble(txtRate.getText()) < 0) {
						JOptionPane.showMessageDialog(null, "Quantity And Rate must not be less Than 0.");
					} else {
						updating_to_table();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Quantity And Rate must not be in Character Or Less Than 0.");
				}
			}
		});

		chckbxBillClearance = new JCheckBox("Bill Cleared");
		chckbxBillClearance.setVisible(false);

		txtBillClearance = new JTextField();
		txtBillClearance.setEditable(false);
		txtBillClearance.setColumns(10);
		txtBillClearance.setBorder(null);
//		txtBillClearance.setFont(new Font("Verdana", Font.PLAIN, 14));
//		txtBillClearance.setForeground(Color.red);

		gl_layeredPane_1();

		scrollPane = new JScrollPane();

		tblBill = new JTable();
		tblBill.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Particulars", "Quantity", "Rate", "Amount" }) {
					Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class };

					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
		tblBill.getColumnModel().getColumn(0).setResizable(false);
		tblBill.getColumnModel().getColumn(0).setPreferredWidth(300);
		tblBill.getColumnModel().getColumn(1).setResizable(false);
		tblBill.getColumnModel().getColumn(2).setResizable(false);
		tblBill.getColumnModel().getColumn(3).setResizable(false);
		scrollPane.setViewportView(tblBill);

		lblTitle = new JLabel("Nepalese Clothing");

		lblAdress = new JLabel("Thamel");

		lblCustomerName = new JLabel("Customer Name:");

		AbstractAction action = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				txtCustomerName.setEditable(false);
				txtCustomerName.setBorder(null);

			}
		};

		txtCustomerName = new JTextField();
		txtCustomerName.addActionListener(action);
		txtCustomerName.setColumns(30);

		txtCustomerName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				txtCustomerName.setEditable(true);
				txtCustomerName.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));

			}
		});

		lblDate = new JLabel("Date:");
		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete_from_table();
			}
		});

		lblTotal = new JLabel("Total:");

		txtTotal = new JTextField();
		txtTotal.setColumns(10);
		txtTotal.setEditable(false);
		txtTotal.setBorder(null);

		lblAdvance = new JLabel("Advance:");

		lblBalance = new JLabel("Balance:");

		txtAdvance = new JTextField();
		txtAdvance.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				calculating_TotalAndBalance();

			}
		});
		txtAdvance.setEditable(true);
		txtAdvance.setColumns(10);

		txtBalance = new JTextField();
		txtBalance.setEditable(false);
		txtBalance.setColumns(10);
		txtBalance.setBorder(null);

		dateChooser = new JDateChooser();
		date = new Date();
		dateChooser.setDate(date);

		txtInvoiceNumber = new JTextField();
		txtInvoiceNumber.setColumns(10);
		txtInvoiceNumber.setEditable(false);
		txtInvoiceNumber.setBorder(null);

		gl_layeredPane();

		tblBill.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				button_enable();
				DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
				// i = the index of the selected row
				int i = tblBill.getSelectedRow();
				txtParticulars.setText(model.getValueAt(i, 0).toString());
				txtQuantity.setText(model.getValueAt(i, 1).toString());
				txtRate.setText(model.getValueAt(i, 2).toString());
			}
		});

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updating_into_database();
				dispose();
			}
		});
		txtCustomerName.setEditable(false);
		dateChooser.setEnabled(false);
		txtAdvance.setEditable(false);
		btnAdd.setEnabled(false);
	}

	public void button_enable() {
		if (this.username.equals("admin")) {
			btnUpdate.setEnabled(true);
			btnDelete.setEnabled(true);
		}
	}

	public void gl_contentPane() {
		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(layeredPane_1, GroupLayout.PREFERRED_SIZE, 359,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(btnCancel).addGap(18)
								.addComponent(btnSave).addGap(209)))));
		gl_contentPane
				.setVerticalGroup(
						gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
								gl_contentPane.createSequentialGroup().addContainerGap()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 600,
														Short.MAX_VALUE)
												.addComponent(layeredPane_1))
										.addGap(18).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnCancel).addComponent(btnSave))
										.addGap(13)));
	}

	public void gl_layeredPane() {

		lblInvoiceNumber = new JLabel("Bill No:");

		gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING).addGroup(gl_layeredPane
				.createSequentialGroup()
				.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane.createSequentialGroup().addGap(200).addComponent(lblTitle,
								GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane.createSequentialGroup().addGap(225).addComponent(lblAdress,
								GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane.createSequentialGroup().addGap(10)
								.addGroup(gl_layeredPane.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(gl_layeredPane.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(txtCustomerName, GroupLayout.PREFERRED_SIZE, 182,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 37,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(dateChooser,
														GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 410,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(
								gl_layeredPane.createSequentialGroup().addContainerGap().addComponent(btnDelete)
										.addGap(114)
										.addGroup(gl_layeredPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(Alignment.LEADING, gl_layeredPane.createSequentialGroup()
														.addComponent(lblBalance, GroupLayout.PREFERRED_SIZE, 74,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED).addComponent(
																txtBalance, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_layeredPane.createSequentialGroup()
														.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
																.addComponent(lblTotal, GroupLayout.PREFERRED_SIZE, 40,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(lblAdvance, GroupLayout.PREFERRED_SIZE,
																		74, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(gl_layeredPane
																.createParallelGroup(Alignment.LEADING, false)
																.addComponent(txtAdvance, 0, 0, Short.MAX_VALUE)
																.addComponent(txtTotal))
														.addGap(105))))
						.addGroup(gl_layeredPane.createSequentialGroup().addContainerGap().addComponent(lblCustomerName,
								GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_layeredPane.createSequentialGroup().addContainerGap().addComponent(lblInvoiceNumber)
										.addGap(18).addComponent(txtInvoiceNumber, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_layeredPane.setVerticalGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane.createSequentialGroup().addContainerGap().addComponent(lblTitle).addGap(2)
						.addComponent(lblAdress).addGap(50)
						.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE).addComponent(lblInvoiceNumber)
								.addComponent(txtInvoiceNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(57).addComponent(lblCustomerName).addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
								.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE).addComponent(lblDate)
										.addComponent(txtCustomerName, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE).addComponent(lblTotal)
								.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE).addComponent(lblAdvance)
								.addComponent(txtAdvance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(13)
						.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE).addComponent(btnDelete)
								.addComponent(lblBalance).addComponent(txtBalance, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(134, Short.MAX_VALUE)));
		layeredPane.setLayout(gl_layeredPane);
		contentPane.setLayout(gl_contentPane);
	}

	public void gl_layeredPane_1() {
		gl_layeredPane_1 = new GroupLayout(layeredPane_1);
		gl_layeredPane_1.setHorizontalGroup(gl_layeredPane_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_layeredPane_1.createSequentialGroup()
						.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblRate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(lblQuantity, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(lblParticulars, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_layeredPane_1.createSequentialGroup().addContainerGap()
										.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
												.addComponent(chckbxBillClearance).addComponent(btnUpdate))))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING).addComponent(btnAdd)
								.addComponent(txtParticulars, GroupLayout.PREFERRED_SIZE, 186,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(txtRate, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(txtQuantity, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 57,
												Short.MAX_VALUE)))
						.addGap(260))
				.addGroup(Alignment.LEADING,
						gl_layeredPane_1.createSequentialGroup().addContainerGap().addComponent(txtBillClearance,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(439, Short.MAX_VALUE)));
		gl_layeredPane_1.setVerticalGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane_1.createSequentialGroup().addContainerGap()
						.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.BASELINE).addComponent(lblParticulars)
								.addComponent(txtParticulars, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.BASELINE).addComponent(lblQuantity)
								.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.BASELINE).addComponent(lblRate)
								.addComponent(txtRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.BASELINE).addComponent(btnUpdate)
								.addComponent(btnAdd))
						.addGap(253).addComponent(chckbxBillClearance).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtBillClearance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(154)));
		layeredPane_1.setLayout(gl_layeredPane_1);
	}

	public void adding_into_table() {
		double amount = Double.parseDouble(txtQuantity.getText()) * Double.parseDouble(txtRate.getText());
		// adding value into table
		DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
		model.addRow(new Object[] { txtParticulars.getText(), Double.parseDouble(txtQuantity.getText()),
				Double.parseDouble(txtRate.getText()), amount });
		calculating_TotalAndBalance();
	}

	public void updating_to_table() {

		double amount = Double.parseDouble(txtQuantity.getText()) * Double.parseDouble(txtRate.getText());
		DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
		int i = tblBill.getSelectedRow();
		if (i >= 0) {
			model.setValueAt(txtParticulars.getText(), i, 0);
			model.setValueAt(txtQuantity.getText(), i, 1);
			model.setValueAt(txtRate.getText(), i, 2);
			model.setValueAt(amount, i, 3);
		} else {
			JOptionPane.showMessageDialog(btnUpdate, "Update Failed");
		}
		calculating_TotalAndBalance();
	}

	public void calculating_TotalAndBalance() {
		double sum = 0;
		for (int a = 0; a < tblBill.getRowCount(); a++) {
			sum = sum + Double.parseDouble(tblBill.getValueAt(a, 3).toString());
		}
		txtTotal.setText(Double.toString(sum));
		txtBalance.setText(Double.toString(sum - Double.parseDouble(txtAdvance.getText())));

	}

	public void delete_from_table() {

		DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
		int i = tblBill.getSelectedRow();
		if (i >= 0) {
			model.removeRow(i);
		} else {
			JOptionPane.showMessageDialog(btnDelete, "Delete Failed");
		}
		calculating_TotalAndBalance();
	}

	public void updating_into_database() {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DefaultTableModel model = (DefaultTableModel) tblBill.getModel();

		String paid = "n";
		if (chckbxBillClearance.isSelected()) {
			paid = "y";
		}

		// Updating into Invoice Table

		Connection con = DatabaseConnection.getConnection();
		String user = "Update invoice set customer = ?, advance = ?, date = ?, paid = ? WHERE id =" + this.id;
		PreparedStatement prep;
		try {
			prep = con.prepareStatement(user);
			prep.setString(1, txtCustomerName.getText());
			prep.setDouble(2, Double.parseDouble(txtAdvance.getText()));
			prep.setString(3, df.format(dateChooser.getDate()));
			prep.setString(4, paid);
			prep.executeUpdate();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(btnSave, "Error Updating");
		}

		// till here

		// deleting in Database

		String delete = "DELETE FROM invoice_detail WHERE id =" + this.id;
		PreparedStatement p;
		try {
			p = con.prepareStatement(delete);
			p.executeUpdate();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error Deleting Invoice Detail");
		}

		// till here

		// adding bill table into sql
		for (int count = 0; count < model.getRowCount(); count++) {
			String invoice_detail_sql = "INSERT INTO invoice_detail (invoice_id, particular, quantity, rate) "
					+ "VALUES (?,?,?,?)";
			PreparedStatement invoice_detail_prep;
			try {
				invoice_detail_prep = con.prepareStatement(invoice_detail_sql);
				invoice_detail_prep.setInt(1, this.id);
				invoice_detail_prep.setString(2, model.getValueAt(count, 0).toString());
				invoice_detail_prep.setDouble(3, Double.parseDouble(model.getValueAt(count, 1).toString()));
				invoice_detail_prep.setDouble(4, Double.parseDouble(model.getValueAt(count, 2).toString()));
				invoice_detail_prep.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// till here
	}

	public void addingdata_to_table() {

		DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
		int rowCount = model.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		String paid = "";
		Connection con = DatabaseConnection.getConnection();
		String query = "SELECT particular, rate, quantity FROM invoice_detail WHERE invoice_id = " + this.id;
		PreparedStatement ps;
		double amount;
		try {
			ps = con.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				amount = rs.getDouble(2) * rs.getDouble(3);
				model.addRow(new Object[] { rs.getString(1), rs.getDouble(2), rs.getDouble(3), amount });
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// next
		String query2 = "SELECT customer, advance, date, invoice_no, paid FROM invoice WHERE id = " + this.id;
		PreparedStatement ps2;
		try {
			ps2 = con.prepareStatement(query2);

			ResultSet rs = ps2.executeQuery();
			double sum = 0;
			for (int i = 0; i < tblBill.getRowCount(); i++) {
				sum = sum + Double.parseDouble(tblBill.getValueAt(i, 3).toString());
			}
			txtTotal.setText(Double.toString(sum));
			while (rs.next()) {
				txtCustomerName.setText(rs.getString(1));
				txtAdvance.setText(rs.getString(2));
				txtBalance.setText(Double.toString(sum - Double.parseDouble(rs.getString(2))));
				dateChooser.setDate(rs.getDate(3));
				txtInvoiceNumber.setText(rs.getString(4));
				paid = rs.getString(5);
			}
			if (paid.equals("n")) {
				chckbxBillClearance.setVisible(true);
			} else {
				txtBillClearance.setText("Bill Cleared");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// till here
	}

	public void user_validation() {

		if (this.username.equals("admin")) {
			txtCustomerName.setEditable(true);
			dateChooser.setEnabled(true);
			txtAdvance.setEditable(true);
			btnAdd.setEnabled(true);
		}
	}
}
