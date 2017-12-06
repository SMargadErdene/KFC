package application;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
//import ListItemHover.WhiteYellowCellRenderer;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class NewOrderClass extends JFrame {
	private static final String FONT = null;
	private JLabel lblTotalValue = new JLabel();
	private JButton jbtnSave = new JButton("Хадгалах");
	private JButton jbtnReset = new JButton("Хэрэгсэхгүй");
	private JButton jbtnPrint = new JButton("Хэвлэх");

	private DefaultTableModel itemTableModel;
	private JTable itemTable;

	private DefaultTableModel setTableModel;
	private JTable setTable;

	private DefaultTableModel selectedItemTableModel;
	private JTable selectedItemTable;
	
	private ImageIcon imageIcon;
	
	public static String url = "jdbc:mysql://localhost/kfc";
	public static String user = "root";
	public static String pass = "mysql2017";
	private int width, height;
	
	public NewOrderClass(int w, int h) throws Exception {
		this.width = w;
		this.height = h;
		// connecting to the database
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(url, user, pass);
		// creating statement
		Statement st = connection.createStatement();
		drawItemTable(st, w);
		st.close();
		connection.close();

	}
	
	public void drawItemTable(Statement st, int w) throws SQLException {
		ResultSet rs = st.executeQuery("select id, name, price from item;");

		// Create itemTable
		String[] itemColumnNames = { "Дугаар","Бүтээгдэхүүн", "Үнэ", "Зураг" };
		DefaultTableModel itemTableModel = new DefaultTableModel(null, itemColumnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// create init value
				return false;
			}
		};

		itemTable = new JTable(itemTableModel);
		TableColumn photo = itemTable.getColumn("Зураг");
		photo.setCellRenderer(new MyImageCellRenderer());
		itemTable.setRowHeight(110);

		// adding item rows to the itemTable
		while (rs.next() && rs != null) {				
			imageIcon= new ImageIcon(getClass().getResource("/image/"+rs.getString("name")+".PNG"));			
			itemTableModel.addRow(new Object[] { rs.getInt("id"), rs.getString("name"), rs.getInt("price"), imageIcon });			
		}
		rs.close();
		int temp = (int)((w-150)*0.34);
		
		// make design itemtable
		itemTable.setTableHeader(null);
		itemTable.setGridColor(Color.LIGHT_GRAY);
		itemTable.setSelectionBackground(Color.LIGHT_GRAY);
		itemTable.setSelectionForeground(Color.BLACK);
		itemTable.setRowSelectionAllowed(true);
		// hide id, name, price column
		itemTable.getColumnModel().getColumn(2).setMinWidth(0);
		itemTable.getColumnModel().getColumn(2).setMaxWidth(0);
		itemTable.getColumnModel().getColumn(1).setMinWidth(0);
		itemTable.getColumnModel().getColumn(1).setMaxWidth(0);
		itemTable.getColumnModel().getColumn(0).setMinWidth(0);
		itemTable.getColumnModel().getColumn(0).setMaxWidth(0);
		itemTable.getColumnModel().getColumn(3).setMaxWidth(temp);
		
		// place itemTable into a scrollPane
		JScrollPane itemScroll = new JScrollPane(itemTable);
		itemScroll.setPreferredSize(new Dimension(temp, 400));
		
		// draw setTable
		drawSetTable(st, photo, itemScroll, w);
		
	}

	public void drawSetTable(Statement st, TableColumn photo, JScrollPane itemScroll, int w) throws SQLException {

		ResultSet res = st.executeQuery("select id, name, price from iset");
		// Create setTable
		String[] setColumnNames = { "Дугаар","Бүтээгдэхүүн", "Үнэ","Зураг" };
		DefaultTableModel setTableModel = new DefaultTableModel(null, setColumnNames){
			@Override
		    public boolean isCellEditable(int row, int column) {
		       // create init value
		       return false;
		    }
		};

		setTable = new JTable(setTableModel);	
		photo = setTable.getColumn("Зураг");
		photo.setCellRenderer(new MyImageCellRenderer());
		setTable.setRowHeight(110);
		
		// adding rows to the setTable
		while (res.next()) {			
			imageIcon= new ImageIcon(getClass().getResource("/image/"+res.getString("name")+".PNG"));
			setTableModel.addRow(new Object[] { res.getInt("id"), res.getString("name"), res.getInt("price"), imageIcon });			
			
		}
		res.close();
		
		int temp = (int)((w-150)*0.35);
		// table design
		setTable.setTableHeader(null);
		setTable.setGridColor(Color.LIGHT_GRAY);
		setTable.setSelectionBackground(Color.LIGHT_GRAY);
		setTable.setSelectionForeground(Color.BLACK);
		setTable.setRowSelectionAllowed(true);
		// hide id, name, price column
		setTable.getColumnModel().getColumn(2).setMinWidth(0);
		setTable.getColumnModel().getColumn(2).setMaxWidth(0);
		setTable.getColumnModel().getColumn(1).setMinWidth(0);
		setTable.getColumnModel().getColumn(1).setMaxWidth(0);
		setTable.getColumnModel().getColumn(0).setMinWidth(0);
		setTable.getColumnModel().getColumn(0).setMaxWidth(0);
		itemTable.getColumnModel().getColumn(3).setMaxWidth(temp);
		
		// placing setTable into a scrollPane
		JScrollPane setScroll = new JScrollPane(setTable);
		setScroll.setPreferredSize(new Dimension(temp, 400));

		setLayout(new BorderLayout(10, 10));
		JPanel pnlTable = new JPanel();
		pnlTable.setLayout(new BorderLayout(10, 10));
		pnlTable.add(itemScroll, BorderLayout.WEST);
		pnlTable.add(setScroll, BorderLayout.CENTER);
		add(pnlTable, BorderLayout.WEST);
		
		drawSelectTable(w);
	}

	public void drawSelectTable(int w) {
		String[] selectedItemColumnNames = {"Дугаар","Бүтээгдэхүүн", "Ширхэг", "Үнэ", "Багц эсэх", "Устгах" };
		selectedItemTableModel = new DefaultTableModel(null, selectedItemColumnNames);
		selectedItemTable = new JTable(selectedItemTableModel);
		
		// look like button
		selectedItemTable.getColumn("Устгах").setCellRenderer(new ButtonRenderer());
		
		//click button
		selectedItemTable.getColumn("Устгах").setCellEditor(new ButtonEditor(new JCheckBox())); 
		
		// hide id and set column
		selectedItemTable.getColumnModel().getColumn(0).setMinWidth(0);
		selectedItemTable.getColumnModel().getColumn(0).setMaxWidth(0); 
		selectedItemTable.getColumnModel().getColumn(4).setMinWidth(0);
		selectedItemTable.getColumnModel().getColumn(4).setMaxWidth(0);
		selectedItemTable.setRowHeight(30);
		
		// place selected item and set table in a scrollPane
		int temp = (int)((w-150)*0.31);
		JScrollPane selectedItemScroll = new JScrollPane(selectedItemTable);
		selectedItemScroll.setPreferredSize(new Dimension(temp, 550));
		JPanel pnlSelected = new JPanel();
		pnlSelected.setLayout(new BorderLayout(10, 10));
		pnlSelected.add(selectedItemScroll, BorderLayout.NORTH);

		// total price label add to the panel
		JPanel pnlTotalValue = new JPanel();
		pnlTotalValue.setLayout(new FlowLayout());
		pnlTotalValue.add(new JLabel("Нийт дүн:"));

		// total the amount, price, save, reset and print button add to the panel
		pnlTotalValue.add(lblTotalValue);
		pnlTotalValue.add(jbtnSave);
		pnlTotalValue.add(jbtnReset);
		pnlTotalValue.add(jbtnPrint);
		pnlSelected.add(pnlTotalValue, BorderLayout.CENTER);
		add(pnlSelected, BorderLayout.EAST);
		settingsButton();
	}

	public void settingsButton() {
		jbtnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection connection = DriverManager.getConnection(url, user, pass);
					Statement st = connection.createStatement();
					st.executeUpdate("insert into iorder(date, total_price, status) values(CURDATE(), "+lblTotalValue.getText()+", 'батлагдсан')");

					ResultSet res = st.executeQuery("select max(id)as id from iorder;");
					res.next();
					int maxid = res.getInt("id");
					int rowCount = selectedItemTableModel.getRowCount();
//					DataBaseKFC db = new DataBaseKFC();
					for (int i = 0; i < rowCount; i++) {
						int id = (int) selectedItemTable.getValueAt(i, 0);
						String name = selectedItemTable.getValueAt(i, 1).toString();
						int quantity = (int) selectedItemTable.getValueAt(i, 2);
						int price = (int) selectedItemTable.getValueAt(i, 3);

						boolean isSet = (boolean) selectedItemTable.getValueAt(i, 4);
						
						if (isSet == false) {
							st.executeUpdate("insert into order_item(order_id, item_id, quantity, price)"
									+ " values(" + maxid + "," + id + "," + quantity + "," + price + ")");
						} else {
							st.executeUpdate("insert into order_item(order_id, set_id, quantity, price)"
										+ " values(" + maxid + "," + id + "," + quantity + "," + price + ")");
						}

					}
					System.out.println("Захиалга батлагдлаа");
//					Alert alert = new Alert(AlertType.CONFIRMATION);
//					alert.setContentText("Захиалга батлагдлаа!");
//					alert.setTitle("Амжилттай!");
//					alert.setHeaderText(null);
//					alert.showAndWait();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				finally{
//					selectedItemTableModel.setRowCount(0);
//				}
			}
		});

		jbtnReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedItemTableModel.setRowCount(0);
			}
		});

		jbtnPrint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection connection = DriverManager.getConnection(url, user, pass);
					Statement st = connection.createStatement();
			        ResultSet res = st.executeQuery("select id from iorder where id=(select max(id) as id from iorder)");
			        if (res.next()) {
			        	PrintReport report = new PrintReport(res.getInt("id"));
			        }
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable theTable = (JTable) e.getSource();
				if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
					int index = theTable.getSelectedRow();
					
					// for(int i=0; i<3; i++)
					// 	System.out.println("--> "+theTable.getModel().getValueAt(index, i));
					
					int id = (int) theTable.getModel().getValueAt(index, 0);
					String name = theTable.getModel().getValueAt(index, 1).toString();
					int price = (int) theTable.getModel().getValueAt(index, 2);

					int rowCount = selectedItemTableModel.getRowCount();
//					System.out.println("---> "+id+", "+name+", "+price+", "+rowCount);

					boolean b = false; // don't find
					for (int i = 0; i < rowCount; i++) {
						String itemName = selectedItemTableModel.getValueAt(i, 1).toString();
						if (itemName.equals(name)) {
							selectedItemTableModel.setValueAt(name, i, 1);

							int itemQuantity = (int) selectedItemTableModel.getValueAt(i, 2);
							selectedItemTableModel.setValueAt(++itemQuantity, i, 2);

							selectedItemTableModel.setValueAt(price * itemQuantity, i, 3);

							b = true; // find
							break;
						}

					}
					if (e.getSource() == itemTable && b == false)
						selectedItemTableModel.addRow(new Object[] { id, name, 1, price, false, "Устгах" });
					if (e.getSource() == setTable && b == false)
						selectedItemTableModel.addRow(new Object[] { id, name, 1, price, true, "Устгах" });

					calculateTotalValue();
				}
			}
		};
		itemTable.addMouseListener(mouseListener);
		setTable.addMouseListener(mouseListener);
	}

	// Calculate and showTotalValue on lblTotalValue
	public void calculateTotalValue() {
		int s = 0;
		int rowCount = selectedItemTableModel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			s += Integer.parseInt(selectedItemTableModel.getValueAt(i, 3).toString());
		}
		lblTotalValue.setText(String.valueOf(s));
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText("X");
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton("Устгах");
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("button clicked");
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = ("Устгах");

			isPushed = true;
			System.out.println(row);

			// when pushed it deletes the row or decrease the quantity
			int selectedItemPrice = (int) table.getModel().getValueAt(row, 3);
			int selectedQuantity = (int) table.getModel().getValueAt(row, 2);
			int price = selectedItemPrice / selectedQuantity;
			selectedItemTableModel.setValueAt(--selectedQuantity, row, 2);
			selectedItemTableModel.setValueAt(price * selectedQuantity, row, 3);
			if (selectedQuantity == 0) {
				((DefaultTableModel) table.getModel()).removeRow(row);
			}

			calculateTotalValue();
			return button;
		}
	}
	
	public static void main(String[] args) throws Exception {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int)screenSize.getWidth();
		int h = (int)screenSize.getHeight();
		NewOrderClass frame = new NewOrderClass(w, h);
		frame.setSize(w, h);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Шинэ Захиалга");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}