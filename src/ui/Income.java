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
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

public class Income extends JFrame {

	private static final long serialVersionUID = 1L;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 771, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panelAnnual = new JPanel();

		panelAnnual.setBorder(BorderFactory.createTitledBorder("Annual Income"));

		panelMonthly = new JPanel();
		panelMonthly.setBorder(BorderFactory.createTitledBorder("Monthly Income"));

		btnDone = new JButton("Done");

		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(panelAnnual, GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
										.addComponent(panelMonthly, GroupLayout.PREFERRED_SIZE, 725,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnDone, Alignment.TRAILING))
								.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(21)
						.addComponent(panelAnnual, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(panelMonthly, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE).addComponent(btnDone)
						.addContainerGap()));

		lblMonthlyIncome = new JLabel("Monthly Income From:");

		lblTo = new JLabel("To:");

		lblIsMonthly = new JLabel("Is:");

		txtMonthly = new JTextField();
		txtMonthly.setEditable(false);
		txtMonthly.setEnabled(false);
		txtMonthly.setColumns(10);

		btnMonthlyGo = new JButton("Go");
		btnMonthlyGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (dateChooserFrom.getDate() < dateChooserTo.getDate())) {
					JOptionPane.showMessageDialog(null, "Error");
				}

			}
		});

		dateChooserFrom = new JDateChooser();

		dateChooserTo = new JDateChooser();
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
		txtAnnual.setEnabled(false);
		txtAnnual.setColumns(10);

		btnAnnualGo = new JButton("Go");
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
