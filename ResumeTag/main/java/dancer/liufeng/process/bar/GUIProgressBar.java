package dancer.liufeng.process.bar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author liufeng E-mail:fliu.Dancer@wind.com.cn
 * @version Time:Jul 17, 2014 3:04:02 PM
 * @Description 关于swing自带的ProgressBar的实现
 */
public class GUIProgressBar implements ActionListener, ChangeListener {
	JFrame frame = null;
	JProgressBar progressbar;
	JLabel label;
	Timer timer;
	JButton button;

	public GUIProgressBar() {
		frame = new JFrame("安装");
		frame.setBounds(100, 100, 400, 130);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPanel = frame.getContentPane();
		label = new JLabel("", JLabel.CENTER);
		progressbar = new JProgressBar();
		progressbar.setOrientation(JProgressBar.HORIZONTAL);
		progressbar.setMinimum(0);
		progressbar.setMaximum(100);
		progressbar.setValue(0);
		progressbar.setStringPainted(true);
		progressbar.addChangeListener(this);
		progressbar.setPreferredSize(new Dimension(300, 20));
		progressbar.setBorderPainted(true);
		progressbar.setBackground(Color.pink);

		JPanel panel = new JPanel();
		button = new JButton("安装");
		button.setForeground(Color.blue);
		button.addActionListener(this);
		panel.add(button);
		timer = new Timer(100, this);
		contentPanel.add(panel, BorderLayout.NORTH);
		contentPanel.add(progressbar, BorderLayout.CENTER);
		contentPanel.add(label, BorderLayout.SOUTH);
		// frame.pack();
		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			timer.start();
		}
		if (e.getSource() == timer) {
			int value = progressbar.getValue();
			if (value < 100)
				progressbar.setValue(++value);
			else {
				timer.stop();
				frame.dispose();
			}
		}

	}

	public void stateChanged(ChangeEvent e1) {
		int value = progressbar.getValue();
		if (e1.getSource() == progressbar) {
			label.setText("目前已完成进度：" + Integer.toString(value) + "%");
			label.setForeground(Color.blue);
		}

	}

	public static void main(String[] args) {
		GUIProgressBar app = new GUIProgressBar();
		System.out.println(app);
	}

}
