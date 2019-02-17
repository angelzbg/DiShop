package shop;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class Main {
	
	private static int width, height, contentWidth, contentHeight;
	// �������������� �� ��������� � ����������, ��� �� �����, ������ �� �� �������� ������ �� ���������� �� ������ � �� �� �������� ������������ + �� �� ��������� ������ ������
	
	private static JFrame mainFrame; // ������� �����, � ����� �� �� ������ ����
	
	/* ----- ��� ����� ----- */
	private static JPanel categoryPanel;
	
	//����
	private static JScrollPane menuScroll;
	
	
	//�������
	private static JPanel cartPanel;
	private static JLabel cartLabel;
	
	/* ----- ����� ����� �� ���������� -----*/
	private static JPanel contentPanel; // ������ ������ visible
	
	// ������������:
	private static ShoppingCart cart; // �� ���������� � ������������ �� � ���������
	private static ArrayList<Category> shop; // ���� � ����������� (��� �� �� ������ ��������� ������ �� txt �����)

	public static void main(String[] args) {
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = gd.getDisplayMode().getWidth();
		height = gd.getDisplayMode().getHeight();
		contentHeight = (int) (height/1.2); // � ����������� ������� � ���� � ���� ��������, �.�. ����������� �� ���� �������
		contentWidth = contentHeight; // � ����������� ������� � ���� � ���� ��������, �.�. ����������� �� ���� �������
		
		//mainFrame
		mainFrame = new JFrame();
		mainFrame.setTitle("DiShop");
		mainFrame.setVisible(true); //������ ��������� �����
		mainFrame.setSize(contentWidth+6, contentHeight+29); // +6 � +29 �� ������� (�������� � ���������, ������ � �������� �� ���������� �� ������... �� ������ swing)
		mainFrame.setLocation((width-mainFrame.getWidth())/2, (height-mainFrame.getHeight())/2); //�� �� ��������� ��������� � ������� �� ������
		mainFrame.setResizable(false); //�� ������ �� �� ���������� (� ��������� ����, �� ��� �� �������� ��� �� ��������)
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ������� ���������� � ��������� �� X
		mainFrame.setLayout(null); //�� ������ ������, �� ���������� ����� ������ (���� ��� ����� � ���)
		
		/* ----- ��� ����� ----- */
		categoryPanel = new JPanel();
		mainFrame.add(categoryPanel); //�������� �� ������ � ���������
		categoryPanel.setSize(contentWidth/4, contentHeight);
		categoryPanel.setLocation(0,  0); // ������ ����� � �������� (���� � ����) � �������
		//categoryPanel.setBackground(Color.decode("#28303F"));
		categoryPanel.setVisible(true);
		categoryPanel.setLayout(null);
		
		
		//�������
		cartPanel = new JPanel();
		categoryPanel.add(cartPanel);
		cartPanel.setSize(contentWidth/4, contentHeight/5);
		cartPanel.setLocation(0, (contentHeight/5)*4); // �� �� ����� ��� ������
		cartPanel.setBackground(Color.decode("#5193ff"));
		cartPanel.setVisible(true);
		cartPanel.setLayout(null);
		
	    cartLabel = new JLabel(new ImageIcon("src/cart.png"));
	    cartLabel.setOpaque(false);
	    cartPanel.add(cartLabel);
	    cartLabel.setSize(contentWidth/4, contentHeight/5);
	    cartLabel.setLocation(0, 0);
	    cartLabel.setText( "<html><center>Wow,<br>such empty!</center></html>" );
	    cartLabel.setHorizontalTextPosition(JLabel.CENTER);
	    cartLabel.setVerticalTextPosition(JLabel.CENTER);
	    cartLabel.setHorizontalAlignment(JLabel.CENTER);
	    cartLabel.setVerticalAlignment(JLabel.CENTER);
	    cartLabel.setFont(new Font(cartLabel.getFont().getName(), Font.BOLD, height/64));
	    cartLabel.setVisible(true);
		
		
		/* ----- ����� ����� �� ���������� -----*/
		contentPanel = new JPanel();
		mainFrame.add(contentPanel);
		contentPanel.setSize(contentWidth-contentWidth/4, contentHeight);
		contentPanel.setLocation(contentWidth/4, 0);
		contentPanel.setBackground(Color.decode("#151B27"));
		contentPanel.setLayout(null);
		contentPanel.setVisible(true);
		
		//Welcome Panel (������� � ���, ������ ���� ��������� �� ����� � ��������� ������ �� contentPanel �� ���� ���������� � ������ garbage collectora �� �� �� ������� �� �������, �� ���� �� �� ������ � �����)
		JPanel welcomePanel = new JPanel();
		contentPanel.add(welcomePanel);
		welcomePanel.setSize(contentWidth-contentWidth/4, contentHeight);
		welcomePanel.setLocation(0, 0);
		welcomePanel.setBackground(Color.decode("#151B27"));
		welcomePanel.setLayout(null);
		welcomePanel.setVisible(true);
		
		JLabel welcomeLabel = new JLabel();
		welcomePanel.add(welcomeLabel);
		welcomeLabel.setSize( contentWidth-contentWidth/4, (contentWidth-contentWidth/4)/2 );
		welcomeLabel.setLocation(0,0);
		//welcomeLabel.setIcon(new ImageIcon(Main.class.getClass().getResource("/welcome.gif")));
		welcomeLabel.setIcon(new ImageIcon(new ImageIcon(Main.class.getClass().getResource("/welcome.gif")).getImage().getScaledInstance(welcomeLabel.getWidth(), welcomeLabel.getHeight(), Image.SCALE_DEFAULT)));
		welcomeLabel.setOpaque(true);
		welcomeLabel.setVisible(true);
		
		JLabel welcomeLabel2 = new JLabel("Please, select a category.", SwingConstants.CENTER);
		welcomePanel.add(welcomeLabel2);
		welcomeLabel2.setSize( contentWidth-contentWidth/4, contentHeight/32 );
		welcomeLabel2.setFont(new Font(welcomeLabel2.getFont().getName(), Font.BOLD, height/64));
		welcomeLabel2.setForeground(Color.decode("#00ccff")); //������ ��� ���� �� ������
		welcomeLabel2.setLocation(0, welcomeLabel.getHeight()); // ����� ��� ���������� (welcomeLabel)
		welcomeLabel2.setVisible(true);

		loadShop(); // ��������� ������� ���� �� ����� � �������������� ������� ������� � �.�.
		
	}//end of main()
	
	private static void loadShop() {
		
		cart = new ShoppingCart();
		shop = new ArrayList<Category>();

		File file = new File("src/shoplist.txt");
        Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		/*
		 * @ = ���������
		 * * = ������������
		 *   = ������� 
		 * $ = ���������� �/� ��� �� ��������, ������ � ����� �� ����������
		 */
		
		JPanel menu = new JPanel(); // ��� �� �� ������� ���������� �� ��������� � ������������
		
        while(sc.hasNextLine()){
        	String line = sc.nextLine();
            //System.out.println(line);
           
        	if(line.charAt(0) == '@') { // ��� ����� ���������
        		shop.add(new Category()); //�������� �����������
        		shop.get(shop.size()-1).setTitle(line.substring(1)); // ���������� ����� ����� ��� ��� ������ ������
        		
        		JLabel jLabel = new JLabel(shop.get(shop.size()-1).getTitle());
        		jLabel.setForeground(Color.decode("#00ccff"));
        		jLabel.setFont(new Font(jLabel.getFont().getName(), Font.BOLD, height/64));
                menu.add(jLabel);
                menu.add(Box.createRigidArea(new Dimension(contentHeight/60, contentHeight/120)));
        	}
        	else if(line.charAt(0) == '*') { // ��� ����� ������������
        		shop.get(shop.size()-1).subcategories.add(new Subcategory());
        		shop.get(shop.size()-1).subcategories.get(shop.get(shop.size()-1).subcategories.size()-1).setTitle(line.substring(1));
        	
        		JLabel jLabel = new JLabel(shop.get(shop.size()-1).subcategories.get(shop.get(shop.size()-1).subcategories.size()-1).getTitle());
        		jLabel.setForeground(Color.decode("#ffffff"));
                menu.add(jLabel);
                menu.add(Box.createRigidArea(new Dimension(contentHeight/120, contentHeight/120)));
                
                jLabel.addMouseListener(new MouseAdapter() {
                	@Override
                    public void mouseClicked(MouseEvent e) {
                		loadProductsFromSubcategory(jLabel.getText());
                    }
                	@Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        jLabel.setForeground(Color.decode("#66ffff")); // ������ ��� ������ ������� � �/� ����������� � ����-��
                        jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e); 
                        jLabel.setForeground(Color.decode("#ffffff")); // ���� ���� ���� ������� � ������� �� ����������� � ����-��
                        jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                });
        	}
        	else { //����� �������
        		String[] parts = line.split("\\$"); //��������� ������� ��� ������� $ � ���������� 3 �����
        		shop.get( shop.size()-1 ).subcategories.get(shop.get(shop.size()-1).subcategories.size()-1).products.add( new Product(parts[0], Double.parseDouble(parts[1]), parts[2]) );
        	}
        }

        BoxLayout boxLayout = new BoxLayout(menu, BoxLayout.Y_AXIS);
		menu.setLayout(boxLayout);
		menu.setBackground(Color.decode("#28303F"));
		menuScroll = new JScrollPane(menu);
		categoryPanel.add(menuScroll);
		menuScroll.setSize(contentWidth/4, (contentHeight/5)*4);
		menuScroll.setLocation(0, 0);
		menuScroll.getViewport().setBackground(Color.decode("#28303F"));
		menuScroll.setVisible(true);
		menuScroll.revalidate();
		menuScroll.repaint();
        
		// ���� ���� ������ � �������� ��������
        /*for(int i=0; i<shop.size(); i++) {
        	
        	System.out.println("----------\nCategory: " + shop.get(i).getTitle());
        	
        	for(int j=0; j<shop.get(i).subcategories.size(); j++) {
        		
        		System.out.println("\tSubcategory " + shop.get(i).subcategories.get(j).getTitle() + ":");
        		
        		for(int k=0; k<shop.get(i).subcategories.get(j).products.size(); k++)
        		{
        			System.out.println("\t\t" + shop.get(i).subcategories.get(j).products.get(k).getTitle() + " = " + shop.get(i).subcategories.get(j).products.get(k).getPrice() + "$");
        		}
        	}
        }*/
        
	}// end of loadShop()
	
	private static void loadProductsFromSubcategory(String subcategoryName){
		contentPanel.removeAll(); // ������ ������ �� ������
		
		//ShopPanel
		JPanel shopPanel = new JPanel();
		contentPanel.add(shopPanel);
		shopPanel.setSize(contentWidth-contentWidth/4, contentHeight);
		shopPanel.setLocation(0, 0);
		shopPanel.setBackground(Color.decode("#151B27"));
		shopPanel.setLayout(null);
		shopPanel.setVisible(true);
		
		int boxWidth = shopPanel.getWidth()/3, boxHeight = (shopPanel.getWidth()/3)*2; //���������, ����� ������ �� ������ �� ����� ������� �������
		
		for(int i=0; i<shop.size(); i++) {
			for(int j=0; j<shop.get(i).subcategories.size(); j++) {
				if(shop.get(i).subcategories.get(j).getTitle().equals(subcategoryName)) { // �������� � ��������������
					
					JPanel productsPanel = new JPanel();
					int panelHeight=0; // ���������� �� ������ ������ �� � boxHeight �������� �� (���� �������� � �������������� ������ �� 3) �� �� ��� �� 3 �������� �� ���
					if(shop.get(i).subcategories.get(j).products.size()%3==0) panelHeight = boxHeight*(shop.get(i).subcategories.get(j).products.size()/3);
					else panelHeight = boxHeight*(shop.get(i).subcategories.get(j).products.size()/3)+boxHeight; // ����� ��� ���� ����.�. + boxHeight
					productsPanel.setSize(shopPanel.getWidth(), panelHeight);
					productsPanel.setBackground(Color.decode("#151B27"));
					productsPanel.setLayout(null);
					
					int stepX=0, stepY=0, count=1;
					DecimalFormat df = new DecimalFormat("#.00"); // ����������� �� ������ �� ��� ����� ���� ����������� �������
					
					for(int k=0; k<shop.get(i).subcategories.get(j).products.size(); k++) { // ��������� �� ���������� � ������
						
						if(count%3==1 && count!=1) {
							stepX = 0;
							stepY += boxHeight;
						} else if(count!=1) {
							stepX+=boxWidth;
						}
						
						// ������ �� ��������
						JPanel box = new JPanel();
						productsPanel.add(box);
						box.setSize(boxWidth, boxHeight);
						box.setLocation(stepX, stepY);
						box.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#151B27")));
						box.setBackground(Color.decode("#434956"));
						box.setLayout(null);
						count++;
						
						//�������� �� ������
						JLabel titleLabel = new JLabel("<html><center>" + shop.get(i).subcategories.get(j).products.get(k).getTitle() + "</center></html>", SwingConstants.CENTER);
						titleLabel.setSize(boxWidth, (boxHeight-boxWidth)/3);
						titleLabel.setForeground(Color.WHITE);
						box.add(titleLabel);
						titleLabel.setLocation(0,0);
						
						JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon("src/" + shop.get(i).subcategories.get(j).products.get(k).getImgPath()).getImage().getScaledInstance(boxWidth, boxWidth, Image.SCALE_DEFAULT)));
						imageLabel.setSize(boxWidth, boxWidth);
						box.add(imageLabel);
						imageLabel.setLocation(0, titleLabel.getHeight()); // ��� titleLabel
						imageLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.decode("#151B27")));
						
						JLabel priceLabel = new JLabel(df.format(shop.get(i).subcategories.get(j).products.get(k).getPrice()) + "$", SwingConstants.CENTER);
						priceLabel.setSize(boxWidth, (boxHeight-boxWidth)/3);
						priceLabel.setForeground(Color.WHITE);
						box.add(priceLabel);
						priceLabel.setLocation(0,titleLabel.getHeight()+imageLabel.getHeight()); // ��� imageLabel
						
						AddToCartPanel addPanel = new AddToCartPanel(i, j, k, boxWidth, (boxHeight-boxWidth)/3);
						box.add(addPanel);
						addPanel.setLocation(0, titleLabel.getHeight()+imageLabel.getHeight()+priceLabel.getHeight());
						
						addPanel.addLabel.addMouseListener(new MouseAdapter() { // ����� �/� ���������� �� ��������
			                @Override
			                public void mouseClicked(MouseEvent e) {
			                	Integer currentValue = (Integer) addPanel.priceSpinner.getValue();
			                	
			                	for(int p=0; p<cart.products.size(); p++) { // ������ �� �� ������� ���� �������� ���� �� ��� � ���������
			                		if(cart.products.get(p).equals(shop.get(addPanel.categoryID).subcategories.get(addPanel.subcategoryID).products.get(addPanel.productID))) { // ��� �������� ����������
			                			cart.quantity.set(p, cart.quantity.get(p)+currentValue); // �������� ������������ ��� ���� �������������� ������
			                			updateBasket();
			                			return; // �������� � ������������, ����� �� ������� �� ��������� ��� �� ������������ ���������� ������
			                		}
			                	}
			                	
			                	//��� �� ��� ������� �� ������, ����� ������ ������ �� ������� ��������:
			                	cart.products.add(shop.get(addPanel.categoryID).subcategories.get(addPanel.subcategoryID).products.get(addPanel.productID));
			                	cart.quantity.add(currentValue);
			                	updateBasket();
			                }
			                @Override
			                public void mouseEntered(MouseEvent e) {
			                    super.mouseEntered(e);
			                    addPanel.addLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			                }
			                @Override
			                public void mouseExited(MouseEvent e) {
			                    super.mouseExited(e); 
			                    addPanel.addLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			                }
			            });
					}
					
					// ������ layout-� �� ������ � null, ������ �������� ��������� 0 � ��������� ���� �� ����� � �� ������ ������ fix
					productsPanel.setBounds(0, 0, productsPanel.getWidth(), productsPanel.getHeight());
					productsPanel.setPreferredSize(new Dimension(productsPanel.getWidth(), productsPanel.getHeight()));
					
					JScrollPane scroll = new JScrollPane(productsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					scroll.setSize(contentWidth-contentWidth/4, contentHeight);
					scroll.setLocation(0, 0);
					shopPanel.add(scroll);
					scroll.setVisible(true);
					scroll.revalidate();
					scroll.repaint();
					
					break; // �� �� �������� �� ����� �� ������ xD
				}
			}
		}
	}// end of loadProductsFromSubcategory()
	
	private static ModalFrame modalFrame;
	// ��� ������� �������� �� ����� � JDialog, �� �� ��� ������ �� �� � ������
	// ���� JFrame �� ����� ��������� ������ ������ � ���� �� ���� �� �� ������ �/� �������� ������ �� ���������� ��������� � ���������� �������
	
	private static boolean isBasketUpdated = false; // �� �� ����� ���� �� ������ mouseListener �� ������� � ���������
	private static void updateBasket() {
		
		int quantity=0;
		for(int i=0; i<cart.quantity.size(); i++) {
			quantity+=cart.quantity.get(i);
		}
		
		if(quantity==0) { // ��� ���� �������� ������� �����, �� � ������ � ���������� �������� (���� �� � ������� �� �������� �������)
			cartLabel.setText( "<html><center>Wow,<br>such empty!</center></html>" );
			cartLabel.removeMouseListener(cartListener);
		}
		else { // ��� ��� ��������
			cartLabel.setText("<html><center>" + cart.products.size() + " products (" + quantity + ") <br>in the basket.<br> Go To ></center></html>");
			if(!isBasketUpdated) {
				isBasketUpdated = true;
				cartLabel.addMouseListener(cartListener); // ����� �/� ��������� �� ��������
			}
		}
	}
	
	private static MouseAdapter cartListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(modalFrame == null) // ��� ���������� �� � ������� �� ���������
			{
				mainFrame.disable(); //������ ��� � ��������� modalFrame, ���������� � �������� �� ���� �� �� ��������
				modalFrame = new ModalFrame(cart, mainFrame);
				modalFrame.addWindowListener(new WindowAdapter() { // ��� ��������� �� ��������� ������ �� ����� null / ��-���� ����� ������ �� �� �� ������ ������ �� ���� �������� � ��������
					@Override
					public void windowClosing(WindowEvent e) {
						modalFrame = null;
						mainFrame.enable(); // ��� ��������� �� ��������� ����� ������ �� ���������� ��������
						cart.clearCart();
						updateBasket();
						isBasketUpdated = false;
					}
				});
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			cartLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e); 
			cartLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	};

}// end of Main{}