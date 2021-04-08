package projectPractice;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import ui.LoginPage;

public class ProgressBar extends JFrame {

	// create a frame
	static JFrame f;

	static JProgressBar b;

	public static void main(String[] args) {
		f = new JFrame("ProgressBar demo");
		JPanel p = new JPanel();
		b = new JProgressBar();
		b.setValue(0);
		b.setStringPainted(true);
		p.add(b);
		f.add(p);
		f.setSize(500, 500);
		f.setVisible(true);
		fill();
	}
	public static void fill() {
		int i = 0;
		try {
			while (i <= 100) {
				b.setValue(i + 10);
				Thread.sleep(1000);
				i += 20;
			}
		} catch (Exception e) {
		}
	}
}