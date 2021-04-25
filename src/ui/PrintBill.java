package ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class PrintBill extends JFrame implements Printable {
	private static final long serialVersionUID = 1L;

	private int id;

	private JPanel contentPane;
	private JTextField txtBillNo;
	private JTextField txtPanNo;
	private JTextField txtCustomerName;
	private JTable tblBill;
	private JDateChooser dateChooser;
	private JTextField txtTotal;
	private JTextField txtAdvance;
	private JTextField txtBalance;
	private JTextField txtBillClearance;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrintBill frame = new PrintBill();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PrintBill(int id) {
		this();
		this.id = id;
		pulling_from_database();
	}

	public PrintBill() {
		setTitle("Print Bill");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1014, 807);
		contentPane = new JPanel();
//		this.setResizable(false);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNepaleseClothing = new JLabel("Nepalese Clothing");
		lblNepaleseClothing.setFont(new Font("Times New Roman", Font.BOLD, 30));

		JLabel lblManufracturer = new JLabel("Manufracturer & Exporter");
		lblManufracturer.setFont(new Font("Times New Roman", Font.BOLD, 11));

		JLabel lblContact = new JLabel("Thamel, Kathmandu, Nepal, Tel: 4358870");
		lblContact.setFont(new Font("Times New Roman", Font.BOLD, 11));

		JLabel lblBillNo = new JLabel("Bill No:");
		lblBillNo.setFont(new Font("Times New Roman", Font.BOLD, 11));

		txtBillNo = new JTextField();
		txtBillNo.setEditable(false);
		txtBillNo.setColumns(10);

		JLabel lblPanNo = new JLabel("Pan No:");
		lblPanNo.setFont(new Font("Times New Roman", Font.BOLD, 11));

		txtPanNo = new JTextField();
		txtPanNo.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtPanNo.setText("600308850");
		txtPanNo.setEditable(false);
		txtPanNo.setColumns(10);

		JLabel lblCustomerName = new JLabel("Customer Name:");
		lblCustomerName.setFont(new Font("Times New Roman", Font.BOLD, 11));

		txtCustomerName = new JTextField();
		txtCustomerName.setEditable(false);
		txtCustomerName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtCustomerName.setColumns(30);

		dateChooser = new JDateChooser();

		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(new Font("Times New Roman", Font.BOLD, 11));

		JLabel lblempty = new JLabel("");

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblTotal = new JLabel("Grand Total:");
		
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		
		JLabel lblAdvance = new JLabel("Advance:");
		
		txtAdvance = new JTextField();
		txtAdvance.setEnabled(true);
		txtAdvance.setEditable(false);
		txtAdvance.setText("");
		txtAdvance.setColumns(10);
		
		JLabel lblBalance = new JLabel("Balance:");
		
		txtBalance = new JTextField();
		txtBalance.setEnabled(true);
		txtBalance.setEditable(false);
		txtBalance.setText("");
		txtBalance.setColumns(10);
		
		JButton btnPrint = new JButton("Print");
		PrintBill pf = this;
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrinterJob job = PrinterJob.getPrinterJob();
				
				job.setPrintable(pf);
				try {
					job.print();
				} catch (PrinterException e1) {
					System.err.println("Error Printing");
				}
			}
		});
		
		txtBillClearance = new JTextField();
		txtBillClearance.setEditable(false);
		txtBillClearance.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(425)
					.addComponent(lblNepaleseClothing, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
					.addGap(420))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(469)
					.addComponent(lblManufracturer, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
					.addGap(393))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(438)
					.addComponent(lblContact, GroupLayout.PREFERRED_SIZE, 375, Short.MAX_VALUE)
					.addGap(283))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBillNo)
						.addComponent(lblPanNo)
						.addComponent(lblCustomerName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtPanNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(631)
										.addComponent(lblempty, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(87))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(23)
										.addComponent(txtCustomerName, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addComponent(lblDate)
								.addGap(18)
								.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addGap(119))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(txtBillNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtBillClearance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnPrint)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 630, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTotal)
								.addComponent(lblAdvance)
								.addComponent(lblBalance))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtBalance)
								.addComponent(txtAdvance)
								.addComponent(txtTotal, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
							.addGap(127))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
					.addGap(136))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNepaleseClothing)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblManufracturer)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblContact)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBillNo)
						.addComponent(txtBillNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPanNo)
						.addComponent(txtPanNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblDate)
							.addComponent(lblempty)
							.addComponent(lblCustomerName)
							.addComponent(txtCustomerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotal)
						.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtBillClearance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAdvance)
						.addComponent(txtAdvance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBalance)
						.addComponent(txtBalance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addComponent(btnPrint))
		);

		tblBill = new JTable();
		tblBill.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Particulars", "Quantity", "Rate", "Amount" }) {
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
		tblBill.getColumnModel().getColumn(0).setPreferredWidth(1000);
		tblBill.getColumnModel().getColumn(1).setPreferredWidth(75);
		tblBill.getColumnModel().getColumn(2).setPreferredWidth(75);
		tblBill.getColumnModel().getColumn(3).setPreferredWidth(75);
		tblBill.getColumnModel().getColumn(1).setResizable(false);
		tblBill.getColumnModel().getColumn(2).setResizable(false);
		tblBill.getColumnModel().getColumn(3).setResizable(false);
		scrollPane.setViewportView(tblBill);
		contentPane.setLayout(gl_contentPane);
		
	}

	public void pulling_from_database() {
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
				txtBillNo.setText(rs.getString(4));
				paid = rs.getString(5);
			}
			if (paid.equals("n")) {
				txtBillClearance.setText("Bill Not Cleared");;
			} else {
				txtBillClearance.setText("Bill Cleared");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// till here
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) {
			return NO_SUCH_PAGE;
		}
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		printAll(graphics);
		return PAGE_EXISTS;
	}
}
