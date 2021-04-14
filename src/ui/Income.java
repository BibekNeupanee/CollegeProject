package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JYearChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import com.toedter.calendar.JMonthChooser;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

public class Income extends JFrame {

	private static final long serialVersionUID = 1L;

	private Date date;
	private float daysBetween;

	private GroupLayout gl_panelMonthly;
	private GroupLayout gl_contentPane;
	private JPanel contentPane;
	private JPanel panelAnnual;
	private JPanel panelMonthly;
	private JTextField txtAnnual;
	private JTextField txtMonthly;
	private JLabel lblAnnualIncome;
	private JYearChooser yearChooser;
	private JLabel lblIs;
	private JButton btnAnnualGo;
	private JLabel lblMonthlyIncome;
	private JLabel lblIsMonthly;
	private JButton btnMonthlyGo;
	private JButton btnDone;
	private JLabel lblTo;
	private JDateChooser dateChooserFrom;
	private JDateChooser dateChooserTo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Income frame = new Income();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Income() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 771, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panelAnnual = new JPanel();

		panelAnnual.setBorder(BorderFactory.createTitledBorder("Annual Income"));

		panelMonthly = new JPanel();
		panelMonthly.setBorder(BorderFactory.createTitledBorder("Monthly Income"));

		btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		lblMonthlyIncome = new JLabel("Monthly Income From:");

		lblTo = new JLabel("To:");

		lblIsMonthly = new JLabel("Is:");

		txtMonthly = new JTextField();
		txtMonthly.setEditable(false);
		txtMonthly.setColumns(10);
		btnMonthlyGo = new JButton("Go");
		btnMonthlyGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Date dateBefore = dateChooserFrom.getDate();
					Date dateAfter = dateChooserTo.getDate();
					long difference = dateAfter.getTime() - dateBefore.getTime();
					daysBetween = (difference / (1000 * 60 * 60 * 24));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (dateChooserFrom.getDate().after(dateChooserTo.getDate())) {
					JOptionPane.showMessageDialog(null, "Enter Valid Date");
				}else if (daysBetween > 30) {
					JOptionPane.showMessageDialog(null, "Days cannot exceed 30 days");
				} else {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Connection con = DatabaseConnection.getConnection();

					String query = "SELECT \r\n" + "  SUM(id.rate * id.quantity) AS Total\r\n" + "FROM\r\n"
							+ "    billing_system.invoice AS i\r\n" + "        INNER JOIN\r\n"
							+ "    billing_system.invoice_detail AS id ON i.id = id.invoice_id\r\n"
							+ "    where i.date >= '" + df.format(dateChooserFrom.getDate()) + "'AND i.date <= '"
							+ df.format(dateChooserTo.getDate()) + "'";

					PreparedStatement ps;
					try {
						ps = con.prepareStatement(query);
						ResultSet rs = ps.executeQuery();
						while (rs.next()) {
							if (rs.getObject(1) != null && !rs.wasNull()) {
								txtMonthly.setText(rs.getString(1));
							} else {
								txtMonthly.setText("0");
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		dateChooserFrom = new JDateChooser();
		date = new Date();
		dateChooserFrom.setDate(date);
		dateChooserTo = new JDateChooser();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fromDate = df.format(dateChooserFrom.getDate());
		LocalDate arrival = LocalDate.parse(fromDate, formatter);
		LocalDate toDate = arrival.plusDays(30);
		String nextDate = toDate.format(formatter);
		try {
			dateChooserTo.setDate(df.parse(nextDate));
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		

		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(panelAnnual, GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
								.addComponent(panelMonthly, GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
								.addComponent(btnDone, Alignment.TRAILING))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(21)
						.addComponent(panelAnnual, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(panelMonthly, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE).addComponent(btnDone)
						.addContainerGap()));

		gl_panelMonthly = new GroupLayout(panelMonthly);
		gl_panelMonthly.setHorizontalGroup(gl_panelMonthly.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMonthly.createSequentialGroup().addContainerGap().addGroup(gl_panelMonthly
						.createParallelGroup(Alignment.LEADING).addComponent(lblMonthlyIncome)
						.addGroup(gl_panelMonthly.createSequentialGroup()
								.addComponent(dateChooserFrom, GroupLayout.PREFERRED_SIZE, 99,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(lblTo).addGap(18)
								.addComponent(dateChooserTo, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(lblIsMonthly).addGap(18)
								.addComponent(txtMonthly, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 263, Short.MAX_VALUE)
								.addComponent(btnMonthlyGo)))
						.addContainerGap()));
		gl_panelMonthly.setVerticalGroup(gl_panelMonthly.createParallelGroup(Alignment.LEADING).addGroup(gl_panelMonthly
				.createSequentialGroup().addContainerGap().addComponent(lblMonthlyIncome).addGap(18)
				.addGroup(gl_panelMonthly.createParallelGroup(Alignment.LEADING)
						.addComponent(dateChooserTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTo)
						.addGroup(gl_panelMonthly.createParallelGroup(Alignment.BASELINE).addComponent(lblIsMonthly)
								.addComponent(txtMonthly, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnMonthlyGo))
						.addComponent(dateChooserFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panelMonthly.setLayout(gl_panelMonthly);

		lblAnnualIncome = new JLabel("Annual Income Of:");

		yearChooser = new JYearChooser();

		lblIs = new JLabel("Is:");

		txtAnnual = new JTextField();
		txtAnnual.setEditable(false);
		txtAnnual.setColumns(10);

		btnAnnualGo = new JButton("Go");
		btnAnnualGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = DatabaseConnection.getConnection();

				String query = "SELECT \r\n" + "  SUM(id.rate * id.quantity) AS Total\r\n" + "FROM\r\n"
						+ "    billing_system.invoice AS i\r\n" + "        INNER JOIN\r\n"
						+ "    billing_system.invoice_detail AS id ON i.id = id.invoice_id\r\n"
						+ "    where year(i.date) = " + yearChooser.getYear();

				PreparedStatement ps;
				try {
					ps = con.prepareStatement(query);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						txtAnnual.setText(rs.getString(1));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panelAnnual = new GroupLayout(panelAnnual);
		gl_panelAnnual.setHorizontalGroup(gl_panelAnnual.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAnnual.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelAnnual.createParallelGroup(Alignment.TRAILING)
								.addComponent(yearChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAnnualIncome))
						.addGap(33).addComponent(lblIs).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtAnnual, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 416, Short.MAX_VALUE).addComponent(btnAnnualGo)
						.addContainerGap()));
		gl_panelAnnual.setVerticalGroup(gl_panelAnnual.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAnnual.createSequentialGroup().addContainerGap().addComponent(lblAnnualIncome)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panelAnnual.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(Alignment.LEADING, gl_panelAnnual.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblIs, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(txtAnnual, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(yearChooser, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap(21, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panelAnnual.createSequentialGroup()
						.addContainerGap(40, Short.MAX_VALUE).addComponent(btnAnnualGo).addContainerGap()));
		panelAnnual.setLayout(gl_panelAnnual);
		contentPane.setLayout(gl_contentPane);

	}
}
