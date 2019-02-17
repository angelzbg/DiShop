package shop;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ModalFrame extends JFrame {
	
	private static int width, height;
	
	public ModalFrame(ShoppingCart cart, JFrame mainFrame) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = gd.getDisplayMode().getWidth();
		height = gd.getDisplayMode().getHeight();
		
		this.setTitle("My Basket");
		this.setSize(height/2+6, height/2+29);
		this.setLocation((width-this.getWidth())/2, (height-this.getHeight())/2);
		this.setResizable(false);
		this.setLayout(null);
		this.setVisible(true);
		
		DecimalFormat df = new DecimalFormat("#.00");
		double sum = 0.0;
		
		String[] columnNames = {"Product name",
                "Quantity",
                "Price",
                "Overall"};
		
		Object[][] data = new Object[cart.products.size()][columnNames.length];
		for(int i=0; i<cart.products.size(); i++) {
			data[i][0] = cart.products.get(i).getTitle();
			data[i][1] = new Integer(cart.quantity.get(i));
			data[i][2] = df.format(new Double(cart.products.get(i).getPrice()));
			data[i][3] = df.format(new Double(cart.quantity.get(i)*cart.products.get(i).getPrice()));
			sum+=cart.quantity.get(i)*cart.products.get(i).getPrice();
		}
		final double sumFaktura = sum;
		
		JTable table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setSize( (int)((height/2)*0.8), (int)((height/2)*0.5) );
		this.add(scrollPane);
		scrollPane.setLocation((this.getWidth()-scrollPane.getWidth())/2, this.getHeight()/20);
		table.setFillsViewportHeight(true);
		
		JLabel overall = new JLabel("Overall: " + df.format(sum) + "$", SwingConstants.RIGHT);
		overall.setSize((int)((height/2)*0.8), this.getHeight()/24);
		this.add(overall);
		overall.setLocation((this.getWidth()-overall.getWidth())/2, this.getHeight()/20+scrollPane.getHeight());
		
		JTextField name = new JTextField("Name");
		name.setSize((int)((height/2)*0.8), this.getHeight()/24);
		this.add(name);
		name.setLocation((this.getWidth()-name.getWidth())/2, this.getHeight()/20+scrollPane.getHeight()+overall.getHeight());
		
		JTextField address = new JTextField("Adress");
		address.setSize((int)((height/2)*0.8), this.getHeight()/24);
		this.add(address);
		address.setLocation((this.getWidth()-address.getWidth())/2, this.getHeight()/20+scrollPane.getHeight()+overall.getHeight()+name.getHeight());
		
		JButton buttonViaCard = new JButton("Pay via Card");
		buttonViaCard.setSize(name.getWidth()/2, name.getHeight());
		this.add(buttonViaCard);
		buttonViaCard.setLocation(this.getWidth()/2-buttonViaCard.getWidth(), (this.getHeight()-(this.getHeight()/20+scrollPane.getHeight()+overall.getHeight()+name.getHeight()+address.getHeight()))/2+this.getHeight()/20+scrollPane.getHeight()+overall.getHeight()+name.getHeight()+address.getHeight());
		
		JButton buttonViaPayPal = new JButton("Pay via Card");
		buttonViaPayPal.setSize(buttonViaCard.getWidth(), buttonViaCard.getHeight());
		this.add(buttonViaPayPal);
		buttonViaPayPal.setLocation(this.getWidth()/2, (this.getHeight()-(this.getHeight()/20+scrollPane.getHeight()+overall.getHeight()+name.getHeight()+address.getHeight()))/2+this.getHeight()/20+scrollPane.getHeight()+overall.getHeight()+name.getHeight()+address.getHeight());

		MouseAdapter mAdapter = new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				PrintWriter writer = null;;
				try {
					writer = new PrintWriter("faktura.txt", "UTF-8"); // файлът отива в [eclipse-workspace/DiShop/]
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				}

				writer.println("Name: " + name.getText());
				writer.println("Adress: " + address.getText());
				writer.println("\n\nProducts:");
				writer.println("\nName\tQuantity\tPrice\tOverall");
				for(int i=0; i<cart.products.size(); i++) {
					writer.println(cart.products.get(i).getTitle() + "\t" + cart.quantity.get(i) + "\t" + df.format(cart.products.get(i).getPrice()) + "\t" + df.format(cart.quantity.get(i)*cart.products.get(i).getPrice()));
				}
				writer.println("\nOverall: " + df.format(sumFaktura) + "$");
				writer.close();
				
				ModalFrame.this.dispatchEvent(new WindowEvent(ModalFrame.this, WindowEvent.WINDOW_CLOSING));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                buttonViaCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttonViaPayPal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e); 
                buttonViaCard.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                buttonViaPayPal.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
		};
		
		buttonViaCard.addMouseListener(mAdapter);
		buttonViaPayPal.addMouseListener(mAdapter);
	}

}
