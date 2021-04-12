package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Balance extends JFrame {

	private static final long serialVersionUID = 1L;

	private String username;

	private JPanel contentPane;

	private GroupLayout gl_contentPane;

	private JTextField textField;

	private JTable tblBillHistory;

	private JButton btnSearch;

	private JLabel lblSearch;
	private JLabel lblDate;
	private JLabel lblTo;
	private JLabel lblFrom;

	private JDateChooser dateFrom;
	private JDateChooser dateTo;

	private JScrollPane scrollPane;
	private JButton btnEditBill;
	private JLabel lblBillHistory;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Balance frame = new Balance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Balance(String username) {
		this();
		this.username = username;
		System.out.println(username);
	}

	public Balance() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 488, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		lblSearch = new JLabel("Customer Name/Bill No:");

		textField = new JTextField();
		textField.setColumns(10);

		lblDate = new JLabel("Search By Date:");

		lblFrom = new JLabel("From:");

		dateFrom = new JDateChooser();

		lblTo = new JLabel("To:");

		dateTo = new JDateChooser();

		btnSearch = new JButton("Search");

		scrollPane = new JScrollPane();

		btnEditBill = new JButton("Edit Bill");
		btnEditBill.setEnabled(false);
		btnEditBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edit_bill();
			}
		});

		group_layout();

		tblBillHistory = new JTable();
		tblBillHistory.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "id", "Bill No:", "Customer Name", "Date", "Total", "Balance" }) {
			private static final long serialVersionUID = 1L;

			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, true, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblBillHistory.getColumnModel().getColumn(0).setResizable(false);
		tblBillHistory.getColumnModel().getColumn(1).setResizable(false);
		tblBillHistory.getColumnModel().getColumn(2).setResizable(true);
		tblBillHistory.getColumnModel().getColumn(3).setResizable(false);
		tblBillHistory.getColumnModel().getColumn(4).setResizable(false);
		tblBillHistory.getColumnModel().getColumn(5).setResizable(false);
		tblBillHistory.getColumn("id").setPreferredWidth(0);
		tblBillHistory.getColumn("id").setMinWidth(0);
		tblBillHistory.getColumn("id").setWidth(0);
		tblBillHistory.getColumn("id").setMaxWidth(0);

		scrollPane.setViewportView(tblBillHistory);
		contentPane.setLayout(gl_contentPane);
		tblBillHistory.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				btnEditBill.setEnabled(true);
//				editbill_validation();
			}
		});
		importing_data_to_tblBillHistory();

	}

	public void importing_data_to_tblBillHistory() {
		DefaultTableModel model = (DefaultTableModel) tblBillHistory.getModel();
		int rowCount = model.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		Connection con = DatabaseConnection.getConnection();

		String query = "SELECT \r\n"
				+ "  i.paid, \r\n"
				+ "  i.id,\r\n"
				+ "  i.invoice_no, \r\n"
				+ "  i.customer,\r\n"
				+ "  i.date,\r\n"
				+ "  i.advance, \r\n"
				+ "  SUM(id.rate * id.quantity) - i.advance AS Balance,\r\n"
				+ "  SUM(id.rate * id.quantity) AS Total\r\n"
				+ "FROM\r\n"
				+ "    billing_system.invoice AS i\r\n"
				+ "        INNER JOIN\r\n"
				+ "    billing_system.invoice_detail AS id ON i.id = id.invoice_id\r\n"
				+ "GROUP BY i.customer\r\n"
				+ "HAVING i.paid = 'n';";

		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDate(5),
						rs.getDouble(7), rs.getDouble(8) });

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void group_layout() {

		lblBillHistory = new JLabel("Bill History:");

		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnSearch).addComponent(textField, 163, 163, 163).addComponent(
												lblSearch, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
								.addGap(48)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblDate)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																.addComponent(lblFrom).addComponent(dateFrom,
																		GroupLayout.PREFERRED_SIZE, 101,
																		GroupLayout.PREFERRED_SIZE))
														.addGap(27)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																.addComponent(lblTo).addComponent(dateTo,
																		GroupLayout.PREFERRED_SIZE, 104,
																		GroupLayout.PREFERRED_SIZE))))
										.addGroup(gl_contentPane.createSequentialGroup().addComponent(btnEditBill)
												.addPreferredGap(ComponentPlacement.RELATED)))
								.addGap(9))
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblBillHistory)
								.addContainerGap(403, Short.MAX_VALUE)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(lblDate)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblTo, GroupLayout.PREFERRED_SIZE, 17,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblFrom)))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(26).addComponent(lblSearch,
										GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(dateTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(dateFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(34)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnSearch)
								.addComponent(btnEditBill))
						.addGap(18).addComponent(lblBillHistory).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE).addContainerGap()));

	}

	public void edit_bill() {

		DefaultTableModel model = (DefaultTableModel) tblBillHistory.getModel();

		// i = the index of the selected row
		int i = tblBillHistory.getSelectedRow();
		int id = (int) model.getValueAt(i, 0);

		EditBill editbill = new EditBill(id, this.username);
		editbill.setVisible(true);

	}

}
