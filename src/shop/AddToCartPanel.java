package shop;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class AddToCartPanel extends JPanel {
	
	public int categoryID, subcategoryID, productID;
	public JLabel addLabel;
	public JSpinner priceSpinner;
	
	public AddToCartPanel(int categoryID, int subcategoryID, int productID, int width, int height) {
		
		this.setSize(width, height);
		this.categoryID = categoryID;
		this.subcategoryID = subcategoryID;
		this.productID = productID;
		
		this.setLayout(null);
		this.setBackground(Color.decode("#545860"));
		this.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.decode("#151B27")));
		
		addLabel = new JLabel(new ImageIcon(new ImageIcon("src/icon_add.png").getImage().getScaledInstance(height/2, height/2, Image.SCALE_DEFAULT)));
		addLabel.setSize(height/2, height/2);
		this.add(addLabel);
		addLabel.setLocation((width/2-addLabel.getWidth())/2, (height-addLabel.getHeight())/2);
		addLabel.setOpaque(true);
		
		priceSpinner = new JSpinner();
		priceSpinner.setModel(new SpinnerNumberModel(1,1,100,1));
		priceSpinner.setSize(width/4, (int) (height/2));
		this.add(priceSpinner);
		priceSpinner.setLocation(width/2+(width/2-priceSpinner.getWidth())/2, (height-priceSpinner.getHeight())/2);
		
		
		//Mouse Events
		
		
	}
	

}
