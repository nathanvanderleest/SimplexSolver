package com.simplexsolver.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.simplexsolver.ui.SimplexSolverApp;
import com.simplexsolver.mathcore.SimplexMethod;

@SuppressWarnings("serial")
public class OpenDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private SimplexSolverApp simplexSolverApp;
	private JPanel eastPanel;
	private JLabel[][] soluLabel;
	//private SimplexMethod problem;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SimplexMethod s = new SimplexMethod(3, 6);
			OpenDialog dialog = new OpenDialog(s);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OpenDialog(SimplexMethod problem) {
		setTitle("Simplex Method Problem");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				JLabel lblSimplexMethodProblem = new JLabel("Pick the Number of rows & columns");
				panel.add(lblSimplexMethodProblem);
			}
		}
		{
			if(!problem.done())
			{
				eastPanel = new JPanel();
				eastPanel.setBorder(new TitledBorder(new EtchedBorder(), "Notes"));
				soluLabel = new JLabel[1][1];
				soluLabel[0][0] = new JLabel("Your matrix must be in standard form.");
				eastPanel.add(soluLabel[0][0]);
				contentPanel.add(eastPanel, BorderLayout.CENTER);
			}
			else if(problem.done() && problem.bound())
			{
				eastPanel = new JPanel(new GridLayout(0, 2) );
				eastPanel.setBorder(new TitledBorder(new EtchedBorder(), "Solution"));
				System.out.println("createEastPanel getRows() = " + problem.getRows());
				System.out.println("getColumns() = " + problem.getColumns());
				soluLabel = new JLabel[problem.getColumns() - 2 - (problem.getRows() - 1)][2];
				eastPanel.add(new JLabel("P = "));
				eastPanel.add(new JLabel(Double.toString(problem.getP()) ));
				for(int i = 0; i < (problem.getColumns() - 2 - (problem.getRows() - 1)); i++)
				{
					soluLabel[i][0] = new JLabel("X" + (i+1) + " = ");
					eastPanel.add(soluLabel[i][0]);
					soluLabel[i][1] = new JLabel(Double.toString(problem.getXValue(i)) );
					eastPanel.add(soluLabel[i][1]);
				}
				contentPanel.add(eastPanel, BorderLayout.CENTER);
			}
			else if(!problem.bound())
			{
				eastPanel = new JPanel();
				eastPanel.setBorder(new TitledBorder(new EtchedBorder(), "Solution"));
				soluLabel = new JLabel[1][1];
				soluLabel[0][0] = new JLabel("This maxamim problem is Unbound.\nThere is No Solution.");
				eastPanel.add(soluLabel[0][0]);
				contentPanel.add(eastPanel, BorderLayout.CENTER);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			{
				JLabel lblRowsColumns = new JLabel("Rows & Columns");
				panel.add(lblRowsColumns);
			}
			{
				comboBox = new JComboBox<String>();
				comboBox.addItem("3 X 6");
				comboBox.addItem("3 X 7");
				comboBox.addItem("3 X 8");
				comboBox.addItem("4 X 7");
				comboBox.addItem("4 X 8");
				comboBox.addItem("4 X 9");
				panel.add(comboBox);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String selectedString = (String) comboBox.getSelectedItem();
						System.out.println(selectedString);
						int selectedIndex = comboBox.getSelectedIndex();
						System.out.println(selectedIndex);
						
						simplexSolverApp = new SimplexSolverApp(selectedIndex);
						simplexSolverApp.setVisible(true);
						
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("System.exit(0)");
						System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	
}
