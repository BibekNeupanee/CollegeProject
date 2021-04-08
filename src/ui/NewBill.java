package ui;

import java.awt.Color;
import java.awt.EventQueue;
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

public class NewBill extends JFrame {

	private static final long serialVersionUID = 1L;

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
	private JTextField txtInvoiceNumber;

	private JDateChooser dateChooser;
	private Date date;

	private JLabel lblParticulars;
	private JLabel lblQuantity;
	private JLabel lblRate;
	private JLabel lblBalance;
	private JLabel lblAdvance;
	private JLabel lblTotal;
	private JLabel lblInvoiceNo;
	private JLabel lblDate;
	private JLabel lblAdress;
	private JLabel lblTitle;
	private JLabel lblCustomerName;

	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnUpdate;
	private JButton btnAdd;
	private JButton btnDelete;


	private JScrollPane scrollPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewBill frame = new NewBill();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public NewBill() {
		setTitle("New Bill");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 873, 714);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);

		layeredPane = new JLayeredPane();
		layeredPane_1 = new JLayeredPane();

		btnSave = new JButton("Save");
		btnCancel = new JButton("Cancel");

		gl_contentPane();

		lblParticulars = new JLabel("Particulars:");
		txtParticulars = new JTextField();
		txtParticulars.setColumns(30);

		lblQuantity = new JLabel("Quantity:");
		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);

		lblRate = new JLabel("Rate:");
		txtRate = new JTextField();
		txtRate.setColumns(10);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.parseInt(txtQuantity.getText());
					Integer.parseInt(txtRate.getText());
					if (txtParticulars.getText().isEmpty() || txtQuantity.getText().isEmpty()
							|| txtRate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Above information must be filled.");
					} else {
						updating_to_table();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Quantity And Rate must not be in Character Or Less Than 0.");
				}
			}
		});

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.parseInt(txtQuantity.getText());
					Integer.parseInt(txtRate.getText());
					if (txtParticulars.getText().isEmpty() || txtQuantity.getText().isEmpty()
							|| txtRate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Above information must be filled.");
					} else {
						adding_into_table();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Quantity And Rate must not be in Character Or Less Than 0.");
				}

			}
		});

		gl_layeredPane_1();

		lblTitle = new JLabel("Nepalese Clothing");

		lblAdress = new JLabel("Thamel");

		lblCustomerName = new JLabel("Customer Name:");
		txtCustomerName = new JTextField();
		txtCustomerName.setColumns(30);
		txtCustomerName.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				txtCustomerName.setEditable(false);
				txtCustomerName.setBorder(null);

			}
		});

		lblDate = new JLabel("Date:");
		dateChooser = new JDateChooser();
		date = new Date();
		dateChooser.setDate(date);

		scrollPane = new JScrollPane();

		tblBill = new JTable();
		tblBill.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Particulars", "Quantity", "Rate", "Amount" }) {
					private static final long serialVersionUID = 1L;

					Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class };

					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}

					boolean[] columnEditables = new boolean[] { false, false, false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
		tblBill.getColumnModel().getColumn(0).setResizable(false);
		tblBill.getColumnModel().getColumn(0).setPreferredWidth(300);
		tblBill.getColumnModel().getColumn(1).setResizable(false);
		tblBill.getColumnModel().getColumn(2).setResizable(false);
		tblBill.getColumnModel().getColumn(3).setResizable(false);
		scrollPane.setViewportView(tblBill);

		txtCustomerName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				txtCustomerName.setEditable(true);
				txtCustomerName.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));

			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnDelete = new JButton("Delete");
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
		txtAdvance = new JTextField();
		txtAdvance.setText("0");
		txtAdvance.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (txtAdvance.getText().isEmpty()) {
					txtBalance.setText(txtTotal.getText());
				} else {
					double balance = Double.parseDouble(txtTotal.getText()) - Double.parseDouble(txtAdvance.getText());
					txtBalance.setText(Double.toString(balance));
				}

			}
		});
		txtAdvance.setEditable(true);
		txtAdvance.setColumns(10);

		txtInvoiceNumber = new JTextField();
		txtInvoiceNumber.setColumns(10);
		txtInvoiceNumber.setEditable(false);
		txtInvoiceNumber.setBorder(null);
		

		lblBalance = new JLabel("Balance:");
		txtBalance = new JTextField();
		txtBalance.setEditable(false);
		txtBalance.setColumns(10);
		txtBalance.setBorder(null);

		gl_layeredPane();

		invoice_number();

		tblBill.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
				// i = the index of the selected row
				int i = tblBill.getSelectedRow();
				txtParticulars.setText(model.getValueAt(i, 0).toString());
				txtQuantity.setText(model.getValueAt(i, 1).toString());
				txtRate.setText(model.getValueAt(i, 2).toString());
			}
		});

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCustomerName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Coustomer Name must be filled.");
				} else if (tblBill.getModel().getRowCount() <= 0) {
					JOptionPane.showMessageDialog(null, "One item in Bill must be added.");
				} else {
					adding_into_database();
					dispose();
				}
			}
		});
	}

	public void invoice_number() {

		Connection con = DatabaseConnection.getConnection();
		String query = "SELECT value FROM auto_number WHERE name = 'invoice'";
		PreparedStatement ps;
		String value = "";
		try {
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				value = rs.getString(1);
			}
			int a = Integer.parseInt(value) + 1;
			txtInvoiceNumber.setText(String.format("%04d", a));
		} catch (SQLException e1) {
			e1.printStackTrace();
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
		
		lblInvoiceNo = new JLabel("Bill No:");

		gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addGap(200)
							.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addGap(225)
							.addComponent(lblAdress, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane.createSequentialGroup()
									.addGroup(gl_layeredPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(Alignment.LEADING, gl_layeredPane.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblInvoiceNo)
											.addGap(18)
											.addComponent(txtInvoiceNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(txtCustomerName, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnDelete)
							.addGap(114)
							.addGroup(gl_layeredPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_layeredPane.createSequentialGroup()
									.addComponent(lblBalance, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtBalance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_layeredPane.createSequentialGroup()
									.addGroup(gl_layeredPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_layeredPane.createSequentialGroup()
											.addComponent(lblTotal, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
											.addGap(38))
										.addGroup(gl_layeredPane.createSequentialGroup()
											.addComponent(lblAdvance, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtAdvance, 0, 0, Short.MAX_VALUE)
										.addComponent(txtTotal))
									.addGap(105))))
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCustomerName, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_layeredPane.setVerticalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle)
					.addGap(2)
					.addComponent(lblAdress)
					.addGap(54)
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInvoiceNo)
						.addComponent(txtInvoiceNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(47)
					.addComponent(lblCustomerName)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblDate)
							.addComponent(txtCustomerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotal)
						.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtAdvance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAdvance))
					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addGap(13)
							.addComponent(btnDelete))
						.addGroup(gl_layeredPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBalance)
								.addComponent(txtBalance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(134, Short.MAX_VALUE))
		);
		layeredPane.setLayout(gl_layeredPane);
		contentPane.setLayout(gl_contentPane);
	}

	public void gl_layeredPane_1() {
		gl_layeredPane_1 = new GroupLayout(layeredPane_1);
		gl_layeredPane_1
				.setHorizontalGroup(gl_layeredPane_1.createParallelGroup(Alignment.TRAILING).addGroup(gl_layeredPane_1
						.createSequentialGroup().addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblRate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(lblQuantity, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(lblParticulars, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_layeredPane_1
										.createSequentialGroup().addContainerGap().addComponent(btnUpdate)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING).addComponent(btnAdd)
								.addComponent(txtParticulars, GroupLayout.PREFERRED_SIZE, 186,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_layeredPane_1.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(txtRate, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(txtQuantity, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 57,
												Short.MAX_VALUE)))
						.addGap(290)));
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
						.addGap(18).addGroup(gl_layeredPane_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnUpdate).addComponent(btnAdd))
						.addGap(452)));
		layeredPane_1.setLayout(gl_layeredPane_1);
	}

	public void adding_into_table() {
		double amount = Double.parseDouble(txtQuantity.getText()) * Double.parseDouble(txtRate.getText());
		// adding value into table
		DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
		model.addRow(new Object[] { txtParticulars.getText(), Double.parseDouble(txtQuantity.getText()),
				Double.parseDouble(txtRate.getText()), amount });
		// updating to total in bill
		double sum = 0;
		for (int i = 0; i < tblBill.getRowCount(); i++) {
			sum = sum + Double.parseDouble(tblBill.getValueAt(i, 3).toString());
		}
		txtTotal.setText(Double.toString(sum));
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
		double sum = 0;
		for (int a = 0; a < tblBill.getRowCount(); a++) {
			sum = sum + Double.parseDouble(tblBill.getValueAt(a, 3).toString());
		}
		txtTotal.setText(Double.toString(sum));

	}

	public void delete_from_table() {

		DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
		int i = tblBill.getSelectedRow();
		if (i >= 0) {
			model.removeRow(i);
		} else {
			JOptionPane.showMessageDialog(btnDelete, "Delete Failed");
		}

		int sum = 0;
		for (int a = 0; a < tblBill.getRowCount(); a++) {
			sum = sum + Integer.parseInt(tblBill.getValueAt(a, 3).toString());
		}
		txtTotal.setText(Integer.toString(sum));

	}

	public void adding_into_database() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DefaultTableModel model = (DefaultTableModel) tblBill.getModel();

		// inserting into sql
		Connection c = DatabaseConnection.getConnection();
		String invoice_sql = "INSERT INTO invoice (customer, advance, date, invoice_no) " + "VALUES (?,?,?,?)";
		PreparedStatement prep;
		int invoice_id = 0;
		String invoice_no = txtInvoiceNumber.getText();
		try {
			prep = c.prepareStatement(invoice_sql, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, txtCustomerName.getText());
			prep.setDouble(2, Double.parseDouble(txtAdvance.getText()));
			prep.setString(3, df.format(dateChooser.getDate()));
			prep.setString(4, invoice_no);
			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				invoice_id = rs.getInt(1);
				String user = "Update auto_number set value = ? WHERE name = ?";
				PreparedStatement pr;
				try {
					pr = c.prepareStatement(user);
					pr.setString(1, txtInvoiceNumber.getText());
					pr.setString(2, "invoice");
					pr.executeUpdate();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Failed to update.");
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// till here

		// adding bill table into sql
		for (int count = 0; count < model.getRowCount(); count++) {
			String invoice_detail_sql = "INSERT INTO invoice_detail (invoice_id, particular, quantity, rate) "
					+ "VALUES (?,?,?,?)";
			PreparedStatement invoice_detail_prep;
			try {
				invoice_detail_prep = c.prepareStatement(invoice_detail_sql);
				invoice_detail_prep.setInt(1, invoice_id);
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
}
