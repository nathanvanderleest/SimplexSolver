package com.simplexsolver.ui;

import java.awt.BorderLayout;
//import java.awt.EventQueue;


import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import com.simplexsolver.mathcore.SimplexMethod;
import com.simplexsolver.ui.OpenDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SimplexSolverApp extends JFrame {

	private JPanel contentPane;
	private JPanel centerPanel;
	private JTextField[][] gridText;
	private SimplexMethod problem;
	private OpenDialog dialog;
	private int r;
	private int c;

	/**
	 * Create the frame.
	 */
	
	public SimplexSolverApp(int selectedIndex) {
		
		setRowsColumns(selectedIndex);
		problem = new SimplexMethod(r, c);
		
		setTitle("Simplex Solver App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblSimplexSolver = new JLabel("Simplex Solver");
		panel.add(lblSimplexSolver);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(0, problem.getColumns() + 1) );
		addLabelRow(centerPanel);
		intializeGridTextAndAddToPanel(centerPanel);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		
		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < problem.getRows(); i++)
				{
					for(int j = 0; j < problem.getColumns(); j++)
					{
						System.out.println("getRows() = " + problem.getRows());
						System.out.println("getColumns() = " + problem.getColumns());
						problem.setValueAtIndex(i, j, Double.parseDouble(gridText[i][j].getText()) );
						System.out.print(problem.getValueAtIndex(i, j) + " ");
					}
					System.out.println();
				}
				problem.simplexSolver();
				
				dialog = new OpenDialog(problem);
				dialog.setVisible(true);
				
				setVisible(false);
				dispose();
			}
		});
		contentPane.add(btnSolve, BorderLayout.SOUTH);
	}
	
	public void setRowsColumns(int rc)
	{
		switch(rc)
		{
			case 0: r = 3; c = 6;
				break;
			case 1: r = 3; c = 7;
				break;
			case 2: r = 3; c = 8;
				break;
			case 3: r = 4; c = 7;
				break;
			case 4: r = 4; c = 8;
				break;
			case 5: r = 4; c = 9;
				break;
			default: r = 3; c = 6;
				break;
		}
	}
	
	public void addLabelRow(JPanel panel)
	{
		panel.add(new JLabel("BV"));
		panel.add(new JLabel("P"));
		for(int i = 0; i < problem.getColumns() - 2 - (problem.getRows() - 1); i++)
		{
			panel.add(new JLabel("X" + (i+1)));
		}
		for(int i = 0; i < (problem.getRows() - 1); i++)
		{
			panel.add(new JLabel("S" + (i+1)));
		}
		panel.add(new JLabel("RHS"));
	}
	
	public void intializeGridTextAndAddToPanel(JPanel panel)
	{
		gridText = new JTextField[problem.getRows()][problem.getColumns()];
		for(int i = 0; i < problem.getRows(); i++)
		{
			if(i < problem.getRows() - 1) { panel.add(new JLabel("S" + (i+1) )); }
			else { panel.add(new JLabel("P")); }
			for(int j = 0; j < problem.getColumns(); j++)
			{
				gridText[i][j] = new JTextField();
				gridText[i][j].setText("0");
				panel.add(gridText[i][j]);
			}
		}
	}
	
}
