import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class Employee {

	private JFrame frame;
	private JTextField jtxtEmployeeID;
	private JTable table;
	private JTextField jtxtCPF;
	private JTextField jtxtNome;
	private JTextField jtxtSobrenome;
	private JTextField jtxtSexo;
	private JTextField jtxtNasc;
	private JTextField jtxtIdade;
	private JTextField jtxtSalario;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	DefaultTableModel model = new DefaultTableModel();

	/**
	 * Launch the application.
	 */
	
	public void updateTable() {
		conn = EmployeeData.ConnectDB();
		
		if (conn != null) {
			String sql = "Select EmpID, CPF, Nome, Sobrenome, Sexo, Nasc, Idade, Salario";
		
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			Object[] columnData = new Object[8];
			
			while (rs.next()) {
				columnData [0] = rs.getString("EmpID");
				columnData [1] = rs.getString("CPF");
				columnData [2] = rs.getString("Nome");
				columnData [3] = rs.getString("Sobrenome");
				columnData [4] = rs.getString("Sexo");
				columnData [5] = rs.getString("EmpNascID");
				columnData [6] = rs.getString("Idade");
				columnData [7] = rs.getString("Salario");
				
				model.addRow(columnData);
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee window = new Employee();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Employee() {
		initialize();
		
		conn = EmployeeData.ConnectDB();
		Object col[] = {"EmpID", "CPF", "Nome", "Sobrenome", "Sexo", "Nasc", "Idade", "Salario"};
		model.setColumnIdentifiers(col);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1210, 654);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID do Funcionario");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(53, 93, 170, 23);
		frame.getContentPane().add(lblNewLabel);
		
		jtxtEmployeeID = new JTextField();
		jtxtEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtEmployeeID.setBounds(233, 78, 148, 38);
		frame.getContentPane().add(jtxtEmployeeID);
		jtxtEmployeeID.setColumns(10);
		
		JButton btnAddNew = new JButton("Adicionar");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "INSERT INTO employee(EmpID,CPF,Nome,Sobrenome,Sexo,Nasc,Idade,Salario)VALUES(?,?,?,?,?,?,?,?)";
				
				try {
					pst = conn.prepareStatement(sql);
					pst.setString(1, jtxtEmployeeID.getText());
					pst.setString(1, jtxtCPF.getText());
					pst.setString(1, jtxtNome.getText());
					pst.setString(1, jtxtSobrenome.getText());
					pst.setString(1, jtxtSexo.getText());
					pst.setString(1, jtxtNasc.getText());
					pst.setString(1, jtxtIdade.getText());
					pst.setString(1, jtxtSalario.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
				}
				catch(Exception ev) {
					JOptionPane.showMessageDialog(null, "System Update Completed");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {
						jtxtEmployeeID.getText(),
						jtxtCPF.getText(),
						jtxtNome.getText(),
						jtxtSobrenome.getText(),
						jtxtSexo.getText(),
						jtxtNasc.getText(),
						jtxtIdade.getText(),
						jtxtSalario.getText(),
				});
				if (table.getSelectedRow() == 1){
					if (table.getRowCount() == 0) {
						JOptionPane.showMessageDialog(null, "Membership Update confirmed","Employee Database System",
								JOptionPane.OK_OPTION);
					}
				}
			}
		});
		btnAddNew.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAddNew.setBounds(51, 522, 185, 51);
		frame.getContentPane().add(btnAddNew);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(420, 78, 747, 370);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EmpID", "CPF", "Nome", "Sobrenome", "Sexo", "Nasc", "Idade", "Salario"
			}
		));
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1 = new JLabel("CPF");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(51, 138, 136, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		jtxtCPF = new JTextField();
		jtxtCPF.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtCPF.setColumns(10);
		jtxtCPF.setBounds(233, 123, 148, 38);
		frame.getContentPane().add(jtxtCPF);
		
		JLabel lblNewLabel_2 = new JLabel("Nome");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(51, 180, 136, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		jtxtNome = new JTextField();
		jtxtNome.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtNome.setColumns(10);
		jtxtNome.setBounds(233, 165, 148, 38);
		frame.getContentPane().add(jtxtNome);
		
		JLabel lblNewLabel_2_1 = new JLabel("Sobrenome");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2_1.setBounds(51, 222, 114, 23);
		frame.getContentPane().add(lblNewLabel_2_1);
		
		jtxtSobrenome = new JTextField();
		jtxtSobrenome.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtSobrenome.setColumns(10);
		jtxtSobrenome.setBounds(233, 207, 148, 38);
		frame.getContentPane().add(jtxtSobrenome);
		
		JLabel lblNewLabel_2_2 = new JLabel("Sexo");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2_2.setBounds(51, 264, 114, 23);
		frame.getContentPane().add(lblNewLabel_2_2);
		
		jtxtSexo = new JTextField();
		jtxtSexo.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtSexo.setColumns(10);
		jtxtSexo.setBounds(233, 249, 148, 38);
		frame.getContentPane().add(jtxtSexo);
		
		JLabel lblNewLabel_2_3 = new JLabel("Data de Nasc.");
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2_3.setBounds(51, 306, 172, 23);
		frame.getContentPane().add(lblNewLabel_2_3);
		
		jtxtNasc = new JTextField();
		jtxtNasc.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtNasc.setColumns(10);
		jtxtNasc.setBounds(233, 291, 148, 38);
		frame.getContentPane().add(jtxtNasc);
		
		JLabel lblNewLabel_2_4 = new JLabel("Idade");
		lblNewLabel_2_4.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2_4.setBounds(51, 348, 114, 23);
		frame.getContentPane().add(lblNewLabel_2_4);
		
		jtxtIdade = new JTextField();
		jtxtIdade.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtIdade.setColumns(10);
		jtxtIdade.setBounds(233, 333, 148, 38);
		frame.getContentPane().add(jtxtIdade);
		
		JLabel lblNewLabel_2_4_1 = new JLabel("Sal\u00E1rio");
		lblNewLabel_2_4_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2_4_1.setBounds(51, 390, 114, 23);
		frame.getContentPane().add(lblNewLabel_2_4_1);
		
		jtxtSalario = new JTextField();
		jtxtSalario.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtSalario.setColumns(10);
		jtxtSalario.setBounds(233, 375, 148, 38);
		frame.getContentPane().add(jtxtSalario);
		
		JButton btnPrint = new JButton("Imprimir");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				MessageFormat header = new MessageFormat("Printing in Progress");
				MessageFormat footer = new MessageFormat("PIdade    {0, number, integer}");
				
				try {
					table.print();
				}
				catch(java.awt.print.PrinterException ev) {
					System.err.format("No Printer found", ev.getMessage());
				}
				
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnPrint.setBounds(351, 522, 185, 51);
		frame.getContentPane().add(btnPrint);
		
		JButton btnReset = new JButton("Resetar");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				jtxtEmployeeID.setText(null);
				jtxtCPF.setText(null);
				jtxtNome.setText(null);
				jtxtSobrenome.setText(null);
				jtxtSexo.setText(null);
				jtxtNasc.setText(null);
				jtxtIdade.setText(null);
				jtxtSalario.setText(null);
			
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReset.setBounds(660, 522, 185, 51);
		frame.getContentPane().add(btnReset);
		
		JButton btnExit = new JButton("Sair");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja sair?", "Employee Database System",
					JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
					System.exit(0);
		}
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExit.setBounds(981, 522, 185, 51);
		frame.getContentPane().add(btnExit);
		
		JLabel lblNewLabel_3 = new JLabel("Controle do Banco de Dados de Funcionarios");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblNewLabel_3.setBounds(121, 11, 974, 56);
		frame.getContentPane().add(lblNewLabel_3);
	}
}
