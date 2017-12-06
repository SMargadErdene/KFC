package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class PrintReport {
	public int reportNo = 0;
	private String FILE_PATH = "/home/margad/workspace/KfcInventory/report/Order Report";
	private String url = "jdbc:mysql://localhost/kfc";
	private String user = "root";
	private String pass = "mysql2017";
	private String reportCode = "CSH";
	private Font bigParFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private Font smallParFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private Font headerParFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
	
	private Paragraph reportNO;
	
	public PrintReport(int order_id) {

        Document document = new Document(PageSize.A4);
		try {
			PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH+" "+reportNo+".pdf"));
			document.open();
			createPDF(document, order_id);
		} catch (FileNotFoundException | DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			System.out.println("Баримт хэвлэгдлээ");
//			Alert alert = new Alert(AlertType.CONFIRMATION);
//			alert.setContentText("Order Report"+reportNo+".pdf баримт хэвлэгдлээ!");
//			alert.setTitle("Амжиллттай!");
//			alert.setHeaderText(null);
//			alert.showAndWait();
			++reportNo;
			document.close();
		}
	}
	
	private void createPDF(Document document, int order_id) throws DocumentException {
		Calendar now = Calendar.getInstance();   // Gets the current date and time
		int year = now.get(Calendar.YEAR);
		reportNO = new Paragraph(reportCode+"/"+year+"/"+reportNo, bigParFont);
		reportNO.setPaddingTop(50);
		
		Chunk chunk = new Chunk(new VerticalPositionMark());
		Paragraph header = new Paragraph("Template Form MF-1",headerParFont); 
		header.add(new Chunk(chunk));
		header.setPaddingTop(20);
		header.add("Minister of Finance and Economy,");
		document.add(header);
		Paragraph headerCon = new Paragraph("Chairman of the National Statistical\nOffice attachment to Order\nNo.171/111 of June 18, 2002", headerParFont);
		headerCon.setAlignment(Element.ALIGN_RIGHT);
		document.add(headerCon);
		document.add(reportNO);
		addEmptyLine(document, 2);
        try {
			int total = createTable(document, order_id);
			Paragraph totalVal = new Paragraph("Niit dun: "+total, smallParFont);
			totalVal.setAlignment(Element.ALIGN_RIGHT);
			totalVal.setPaddingTop(30);
			totalVal.setSpacingAfter(30);
			document.add(totalVal);
//			createTotalValueLine(document);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int createTable(Document document, int order_id)
            throws DocumentException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        PdfPTable table = new PdfPTable(4);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
//        try { 
//        	Font sans = FontFactory.getFont("sanskrit", "UTF-8", true); 
//        	Phrase phrase = new Phrase("Бүтээгдэхүүн");
//        	phrase.setFont(sans); 
//        	PdfPCell c1 = new PdfPCell();
        	
//        	String value = new String("PPPP".getBytes("UTF-8"));
//        	String value = "ХХХХЛЛЛ";
//        	byte[] byteText = value.getBytes(Charset.forName("UTF-8"));
//        	//To get original string from byte.
//        	String originalString= new String(byteText , "UTF-8");
//        	System.out.println(originalString);
        	
        	PdfPCell c1 = new PdfPCell(new Phrase("Buteegdehuun"));
        	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Negj une"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Too shirheg"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
        
      
        c1 = new PdfPCell(new Phrase("Niit une"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
      
        table.setHeaderRows(1);
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(url, user, pass);
		Statement st = connection.createStatement();
        ResultSet res = st.executeQuery("select total_price from iorder where id="+order_id);
		res.next();
		int total = res.getInt("total_price");
		res = st.executeQuery("select * from order_item where order_id="+order_id+" ");
		int set_id, item_id, qty;
		try {
			while(res.next()) {
				set_id = res.getInt("set_id");
				item_id = res.getInt("item_id");
				qty = res.getInt("quantity");
				ResultSet tempRs;
				Statement tempSt = connection.createStatement();
				if (set_id != 0) {
					tempRs = tempSt.executeQuery("select * from iset where id="+set_id+";");
				}else{
					tempRs = tempSt.executeQuery("select * from item where id="+item_id+";");
				}
				String name;
				int price;
				if(tempRs.next()) {
					name = tempRs.getString("name");
					price = tempRs.getInt("price");
					System.out.println("Name: "+name);
					table.addCell(name);
			        table.addCell(String.valueOf(price));
			        table.addCell(String.valueOf(qty));
			        table.addCell(String.valueOf(qty * price));
				}
				tempRs.close();
				tempSt.close();
			}
			res.close();
			st.close();
	        connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setWidthPercentage(100);
		document.add(table);
		return total;
    }
	
	private void addEmptyLine(Document document, int number) throws DocumentException {
        for (int i = 0; i < number; i++) {
        	document.add(new Paragraph(" "));
        }
    }
}	
