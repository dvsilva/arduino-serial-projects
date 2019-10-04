package br.com.dvs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.dvs.control.CtrlProgram;
import br.com.dvs.domain.Operations;

/**
 * @author Danyllo
 */
public class MainView extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5313060376526219954L;

	private static final int DELAY = 1000;
	private static final int PERIOD = 1000;
	
	private JLabel labelDataHora;
	private JLabel labelSensorValue;
	private JButton btTurnOn;
	private JButton btTurnOff;
	private JButton btClose;

	private CtrlProgram ctrlProgram;
	
	public MainView(CtrlProgram ctrl){
		super();
		
		this.ctrlProgram = ctrl;
		
		this.setTitle("Controlling arduino through the serial port!");
		
		BorderLayout gridLayout = new BorderLayout();
		this.getContentPane().setLayout(gridLayout);
		
		this.setSize(600, 300);
		this.setLocation(300, 80);
		this.getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel painelSuperior = new JPanel(new GridLayout());
		this.labelDataHora = new JLabel("No data");
		this.labelDataHora.setHorizontalAlignment(SwingConstants.CENTER);
		this.labelDataHora.setVerticalAlignment(SwingConstants.CENTER);
		this.labelDataHora.setFont(new Font("Serif", Font.PLAIN, 40));
		painelSuperior.add(labelDataHora);
		
		this.labelSensorValue = new JLabel("No data");
		this.labelSensorValue.setHorizontalAlignment(SwingConstants.CENTER);
		this.labelSensorValue.setVerticalAlignment(SwingConstants.CENTER);
		this.labelSensorValue.setFont(new Font("Serif", Font.PLAIN, 40));
		painelSuperior.add(this.labelSensorValue);
		this.add(painelSuperior, BorderLayout.NORTH);		
		
		JPanel painelInferior = new JPanel(new GridLayout());
		
		this.btTurnOn = new JButton("Turn On");
		this.btTurnOn.addActionListener(this);
		painelInferior.add(btTurnOn);
		
		this.btTurnOff = new JButton("Turn Off");
		this.btTurnOff.addActionListener(this);
		painelInferior.add(btTurnOff);
		
		this.btClose = new JButton("Close");
		this.btClose.addActionListener(this);
		painelInferior.add(btClose);
		
		this.add(painelInferior, BorderLayout.CENTER);
		
		this.setVisible(true);
		
		scheduleRefreshData();
	}

	private void scheduleRefreshData() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//int nextInt = new Random().nextInt(100);
				//ctrlProgram.getArduinoService().getArduino().setLastInputValue(String.valueOf(nextInt) + "°");
				refreshData();
			}
		}, DELAY, PERIOD);
	}

	public void refreshData() {
		String data = this.ctrlProgram.getLastData();
		this.labelSensorValue.setText(data);
		//System.out.println(data);

	    SimpleDateFormat SimpleTime = new SimpleDateFormat("H:mm:ss");
	    this.labelDataHora.setText(SimpleTime.format(new Date()));
	}
	
	public void actionPerformed(ActionEvent source) {
		
		if(source.getSource() == this.btTurnOn){
			this.ctrlProgram.execute(Operations.TURN_ON);
			JOptionPane.showMessageDialog(this, "Turned on");
		}
		
		if(source.getSource() == this.btTurnOff){
			this.ctrlProgram.execute(Operations.TURN_OFF);
			JOptionPane.showMessageDialog(this, "Turned off");
		}
		
		if(source.getSource() == this.btClose){
			this.ctrlProgram.terminate();
		}
	}

	public void renderErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
	}

}