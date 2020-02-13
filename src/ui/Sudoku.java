package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import algorithms.BacktrackingAlgorithm;
import algorithms.dancinglinks.DancingLinksAlgorithm;

public class Sudoku extends JFrame {

	private JPanel contentPane;
	
	private JPanel gridPanel;
	private JPanel[] boxPanels;
	private static JTextField[][] grid;
	
	private JPanel uiPanel;
	private JButton solveBacktrackingButton;
	private JButton solveExactCoverButton;
	private JLabel resultLabel;
	private JButton clearButton;
	private JList<String> testsList;
	private JScrollPane testsScroller;
	private JButton selectTestButton;

	private int testsCount = 3;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Sudoku frame = new Sudoku();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Solves sudoku using given algorithm.
	 * */
	private String solve(String algorithm) {
		int values [][] = new int[9][9];
		boolean result;

		for(int row = 0; row < 9; row++) {
			for(int column = 0; column < 9; column++) {
				String text = grid[row][column].getText();
				if(text != null && !text.equals(" ") && !text.equals("")) {
					values[row][column] = Integer.parseInt(text);
					grid[row][column].setBackground(Color.YELLOW);
				}
				else {
					values[row][column] = 0;
				}
			}
		}
		
		long start = System.nanoTime();
		if(algorithm.equals("backtracking")) {
			result = BacktrackingAlgorithm.solve(values);
		}
		else {
			result = DancingLinksAlgorithm.solve(values);
		}
		long finish = System.nanoTime();
		
		if(result) {
			for(int row = 0; row < 9; row++) {
				for(int column = 0; column < 9; column++) {
					grid[row][column].setText(Integer.toString(values[row][column]));
				}
			}
			return "solved in " + Double.toString((double)(finish - start) / 1000000000) + " seconds.";
		}
		
		return "failed.";
	}
	
	/**
	 * Clears values from sudoku.
	 * */
	private void clear() {
		for(int row = 0; row < 9; row++) {
			for(int column = 0; column < 9; column++) {
				grid[row][column].setText(null);
				grid[row][column].setBackground(Color.WHITE);
			}
		}
		resultLabel.setText("Result:");
	}
	
	/**
	 * Prints sudoku.
	 * */
	@SuppressWarnings("unused")
	private void print() {
		System.out.println("Cells: ");
		for(int row = 0; row < 9; row++) {
			for(int column = 0; column < 9; column++) {
				System.out.print("\"" + grid[row][column].getText() + "\"|");
			}
			System.out.println();
		}
	}
	
	/**
	 * Inserts test values.
	 * */
	private void insertTestValues(int set) {
		int[][][] tests = {
				{
					{ 8, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 3, 6, 0, 0, 0, 0, 0 },
					{ 0, 7, 0, 0, 9, 0, 2, 0, 0 },
					{ 0, 5, 0, 0, 0, 7, 0, 0, 0 },
					{ 0, 0, 0, 0, 4, 5, 7, 0, 0 },
					{ 0, 0, 0, 1, 0, 0, 0, 3, 0 },
					{ 0, 0, 1, 0, 0, 0, 0, 6, 8 },
					{ 0, 0, 8, 5, 0, 0, 0, 1, 0 },
					{ 0, 9, 0, 0, 0, 0, 4, 0, 0 }
				},
				{
					{ 3, 8, 2, 0, 0, 1, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 9, 0, 1, 8 },
					{ 0, 0, 0, 0, 7, 0, 0, 0, 2 },
					{ 0, 5, 0, 0, 0, 7, 4, 0, 0 },
					{ 0, 0, 3, 0, 0, 0, 2, 0, 0 },
					{ 0, 0, 4, 9, 0, 0, 0, 5, 0 },
					{ 5, 0, 0, 0, 6, 0, 0, 0, 0 },
					{ 1, 2, 0, 5, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 3, 0, 0, 5, 6, 9 }
				},
				{
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 3, 0, 8, 5 },
					{ 0, 0, 1, 0, 2, 0, 0, 0, 0 },
					{ 0, 0, 0, 5, 0, 7, 0, 0, 0 },
					{ 0, 0, 4, 0, 0, 0, 1, 0, 0 },
					{ 0, 9, 0, 0, 0, 0, 0, 0, 0 },
					{ 5, 0, 0, 0, 0, 0, 0, 7, 3 },
					{ 0, 0, 2, 0, 1, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 4, 0, 0, 0, 9 }
				}
		};
		
		for(int r = 0; r < 9; r++) {
			for(int c = 0; c < 9; c++) {
				if(tests[set][r][c] != 0) {
					grid[r][c].setText(Integer.toString(tests[set][r][c]));
				} else {
					grid[r][c].setText(null);
				}
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public Sudoku() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 520);
		setMinimumSize(new Dimension(750, 520));
		setTitle("Sudoku solver");
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		gridPanel = new JPanel();
		gridPanel.setPreferredSize(new Dimension(450, 450));
		contentPane.add(gridPanel);
		gridPanel.setLayout(new GridLayout(3, 3, 0, 0));
		
		boxPanels = new JPanel[9];
		for(int i = 0; i < 9; i++) {
			boxPanels[i] = new JPanel();
			boxPanels[i].setBorder(new LineBorder(new Color(0, 0, 0), 2));
			boxPanels[i].setLayout(new GridLayout(3, 3, 0, 0));
			boxPanels[i].setMinimumSize(new Dimension(150, 150));
			gridPanel.add(boxPanels[i]);
		}
		
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("*");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter.setValidCharacters("123456789 ");
		
		grid = new JFormattedTextField[9][9];
		for(int row = 0; row < 9; row++) {
			for(int column = 0; column < 9; column++) {
				grid[row][column] = new JFormattedTextField(formatter);
				grid[row][column].setMargin(new Insets(0, 0, 0, 0));
				grid[row][column].setBorder(new LineBorder(new Color(0, 0, 0)));
				grid[row][column].setPreferredSize(new Dimension(50, 50));
				grid[row][column].setFont(new Font("Tahoma", Font.PLAIN, 40));
				grid[row][column].setHorizontalAlignment(SwingConstants.CENTER);
				grid[row][column].setColumns(1);
				grid[row][column].setText(null);
				boxPanels[row/3*3 + column/3].add(grid[row][column]);
				final int r = row;
				final int c = column;
				grid[r][c].addFocusListener(new FocusListener() {
					@Override
					public void focusLost(FocusEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void focusGained(FocusEvent e) {
						grid[r][c].setText(null);
					}
				});
			}
		}
		
		uiPanel = new JPanel();
		
		solveBacktrackingButton = new JButton("Solve with backtracking");
		solveBacktrackingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultLabel.setText("Result: " + solve("backtracking"));
		    }
		});
		
		solveExactCoverButton = new JButton("Solve with dancing links");
		solveExactCoverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultLabel.setText("Result: " + solve("dancinglinks"));
		    }
		});
		
		resultLabel = new JLabel("Result:");
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
		    }
		});
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int t = 0; t < testsCount; t++) {
			listModel.addElement("Test " + Integer.toString(t+1));
		}
		
		testsList = new JList<String>(listModel);
		testsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testsList.setSelectedIndex(0);
		testsList.setLayoutOrientation(JList.VERTICAL);
		
		testsScroller = new JScrollPane(testsList);
		testsScroller.setPreferredSize(new Dimension(100,100));
		
		selectTestButton = new JButton("Insert test values");
		selectTestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertTestValues(testsList.getSelectedIndex());
		    }
		});
		
		GroupLayout uiLayout = new GroupLayout(uiPanel);
		uiPanel.setLayout(uiLayout);	
		uiLayout.setHorizontalGroup(
				uiLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(solveBacktrackingButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addComponent(solveExactCoverButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addComponent(testsScroller, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addComponent(selectTestButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addComponent(resultLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
		);
		uiLayout.setVerticalGroup(
				uiLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(solveBacktrackingButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(solveExactCoverButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(testsScroller, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(selectTestButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
		);	
		
		GroupLayout mainLayout = new GroupLayout(contentPane);
		contentPane.setLayout(mainLayout);
		mainLayout.setAutoCreateGaps(true);
		mainLayout.setAutoCreateContainerGaps(true);
		mainLayout.setHorizontalGroup(
				mainLayout.createSequentialGroup()
					.addComponent(gridPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(uiPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
		);
		mainLayout.setVerticalGroup(
				mainLayout.createParallelGroup()
					.addComponent(gridPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
					.addComponent(uiPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE ,GroupLayout.PREFERRED_SIZE)
		);	
	}
}
