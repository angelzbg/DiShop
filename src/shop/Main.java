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
	// Оразмеряването на прозореца е премахнато, ако се пусне, трябва да се прихване ивента за рисайзване на фрейма и да се променят променливите + да се рипейнтне всичко наново
	
	private static JFrame mainFrame; // главния фрейм, в който ще се зареди шопа
	
	/* ----- Ляв панел ----- */
	private static JPanel categoryPanel;
	
	//Меню
	private static JScrollPane menuScroll;
	
	
	//Количка
	private static JPanel cartPanel;
	private static JLabel cartLabel;
	
	/* ----- Десен панел за съдържание -----*/
	private static JPanel contentPanel; // главен винаги visible
	
	// Допълнително:
	private static ShoppingCart cart; // за продуктите и количеството им в количката
	private static ArrayList<Category> shop; // лист с категориите (тук ще се зареди абсолютно всичко от txt файла)

	public static void main(String[] args) {
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = gd.getDisplayMode().getWidth();
		height = gd.getDisplayMode().getHeight();
		contentHeight = (int) (height/1.2); // в последствие станаха с една и съща стойност, т.е. програмката ще бъде квадрат
		contentWidth = contentHeight; // в последствие станаха с една и съща стойност, т.е. програмката ще бъде квадрат
		
		//mainFrame
		mainFrame = new JFrame();
		mainFrame.setTitle("DiShop");
		mainFrame.setVisible(true); //правим прозореца видим
		mainFrame.setSize(contentWidth+6, contentHeight+29); // +6 и +29 за уиндоус (проблеми с бордърите, влизат в сметките за рисайзване на фрейма... мн глупав swing)
		mainFrame.setLocation((width-mainFrame.getWidth())/2, (height-mainFrame.getHeight())/2); //за да преместим прозореца в средата на екрана
		mainFrame.setResizable(false); //не желаем да се оразмерява (в условието пише, че ние си избираме как да изглежда)
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // спираме програмата с натискане на X
		mainFrame.setLayout(null); //не искаме лейаут, ще хардкоднем целия дизайн (така или иначе е леш)
		
		/* ----- Ляв панел ----- */
		categoryPanel = new JPanel();
		mainFrame.add(categoryPanel); //добавяне на панела в прозореца
		categoryPanel.setSize(contentWidth/4, contentHeight);
		categoryPanel.setLocation(0,  0); // панела отива в началото (горе в ляво) в рамката
		//categoryPanel.setBackground(Color.decode("#28303F"));
		categoryPanel.setVisible(true);
		categoryPanel.setLayout(null);
		
		
		//Количка
		cartPanel = new JPanel();
		categoryPanel.add(cartPanel);
		cartPanel.setSize(contentWidth/4, contentHeight/5);
		cartPanel.setLocation(0, (contentHeight/5)*4); // за да отиде под менюто
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
		
		
		/* ----- Десен панел за съдържание -----*/
		contentPanel = new JPanel();
		mainFrame.add(contentPanel);
		contentPanel.setSize(contentWidth-contentWidth/4, contentHeight);
		contentPanel.setLocation(contentWidth/4, 0);
		contentPanel.setBackground(Color.decode("#151B27"));
		contentPanel.setLayout(null);
		contentPanel.setVisible(true);
		
		//Welcome Panel (наорчно е тук, понеже след натискане на бутон с категория всичко от contentPanel ще бъде премахнато и искаме garbage collectora да си го унищожи от паметта, за това не го държим в класа)
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
		welcomeLabel2.setForeground(Color.decode("#00ccff")); //светло син цвят на текста
		welcomeLabel2.setLocation(0, welcomeLabel.getHeight()); // отива под картинката (welcomeLabel)
		welcomeLabel2.setVisible(true);

		loadShop(); // зареждаме нужните неща от файла и инициализираме нужните листове и т.н.
		
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
		 * @ = категория
		 * * = подкатегория
		 *   = продукт 
		 * $ = разделител м/у име на продукта, цената и името на картинката
		 */
		
		JPanel menu = new JPanel(); // тук ще се заредят текстовете на категории и подкатегории
		
        while(sc.hasNextLine()){
        	String line = sc.nextLine();
            //System.out.println(line);
           
        	if(line.charAt(0) == '@') { // ако имаме категория
        		shop.add(new Category()); //добавяме категорията
        		shop.get(shop.size()-1).setTitle(line.substring(1)); // заглавието става целия ред без първия символ
        		
        		JLabel jLabel = new JLabel(shop.get(shop.size()-1).getTitle());
        		jLabel.setForeground(Color.decode("#00ccff"));
        		jLabel.setFont(new Font(jLabel.getFont().getName(), Font.BOLD, height/64));
                menu.add(jLabel);
                menu.add(Box.createRigidArea(new Dimension(contentHeight/60, contentHeight/120)));
        	}
        	else if(line.charAt(0) == '*') { // ако имаме подкатегория
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
                        jLabel.setForeground(Color.decode("#66ffff")); // светло син докато мишката е в/у категорията в меню-то
                        jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e); 
                        jLabel.setForeground(Color.decode("#ffffff")); // бяло след като мишката е махната от категорията в меню-то
                        jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                });
        	}
        	else { //имаме продукт
        		String[] parts = line.split("\\$"); //разделяме стринга при символа $ и получаваме 3 части
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
        
		// тест дали всичко е заредено правилно
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
		contentPanel.removeAll(); // чистим всичко от панела
		
		//ShopPanel
		JPanel shopPanel = new JPanel();
		contentPanel.add(shopPanel);
		shopPanel.setSize(contentWidth-contentWidth/4, contentHeight);
		shopPanel.setLocation(0, 0);
		shopPanel.setBackground(Color.decode("#151B27"));
		shopPanel.setLayout(null);
		shopPanel.setVisible(true);
		
		int boxWidth = shopPanel.getWidth()/3, boxHeight = (shopPanel.getWidth()/3)*2; //размерите, които искаме за панела за всеки отделен продукт
		
		for(int i=0; i<shop.size(); i++) {
			for(int j=0; j<shop.get(i).subcategories.size(); j++) {
				if(shop.get(i).subcategories.get(j).getTitle().equals(subcategoryName)) { // намерена е подкатегорията
					
					JPanel productsPanel = new JPanel();
					int panelHeight=0; // височината на панела трябва да е boxHeight умножена по (броя продукти в подкатегорията делено на 3) за да има по 3 продукта на ред
					if(shop.get(i).subcategories.get(j).products.size()%3==0) panelHeight = boxHeight*(shop.get(i).subcategories.get(j).products.size()/3);
					else panelHeight = boxHeight*(shop.get(i).subcategories.get(j).products.size()/3)+boxHeight; // имаме още един редт.е. + boxHeight
					productsPanel.setSize(shopPanel.getWidth(), panelHeight);
					productsPanel.setBackground(Color.decode("#151B27"));
					productsPanel.setLayout(null);
					
					int stepX=0, stepY=0, count=1;
					DecimalFormat df = new DecimalFormat("#.00"); // форматиране на цените до два знача след десетичната запетая
					
					for(int k=0; k<shop.get(i).subcategories.get(j).products.size(); k++) { // зареждане на продуктите в панела
						
						if(count%3==1 && count!=1) {
							stepX = 0;
							stepY += boxHeight;
						} else if(count!=1) {
							stepX+=boxWidth;
						}
						
						// панела за продукта
						JPanel box = new JPanel();
						productsPanel.add(box);
						box.setSize(boxWidth, boxHeight);
						box.setLocation(stepX, stepY);
						box.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#151B27")));
						box.setBackground(Color.decode("#434956"));
						box.setLayout(null);
						count++;
						
						//елементи за панела
						JLabel titleLabel = new JLabel("<html><center>" + shop.get(i).subcategories.get(j).products.get(k).getTitle() + "</center></html>", SwingConstants.CENTER);
						titleLabel.setSize(boxWidth, (boxHeight-boxWidth)/3);
						titleLabel.setForeground(Color.WHITE);
						box.add(titleLabel);
						titleLabel.setLocation(0,0);
						
						JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon("src/" + shop.get(i).subcategories.get(j).products.get(k).getImgPath()).getImage().getScaledInstance(boxWidth, boxWidth, Image.SCALE_DEFAULT)));
						imageLabel.setSize(boxWidth, boxWidth);
						box.add(imageLabel);
						imageLabel.setLocation(0, titleLabel.getHeight()); // под titleLabel
						imageLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.decode("#151B27")));
						
						JLabel priceLabel = new JLabel(df.format(shop.get(i).subcategories.get(j).products.get(k).getPrice()) + "$", SwingConstants.CENTER);
						priceLabel.setSize(boxWidth, (boxHeight-boxWidth)/3);
						priceLabel.setForeground(Color.WHITE);
						box.add(priceLabel);
						priceLabel.setLocation(0,titleLabel.getHeight()+imageLabel.getHeight()); // под imageLabel
						
						AddToCartPanel addPanel = new AddToCartPanel(i, j, k, boxWidth, (boxHeight-boxWidth)/3);
						box.add(addPanel);
						addPanel.setLocation(0, titleLabel.getHeight()+imageLabel.getHeight()+priceLabel.getHeight());
						
						addPanel.addLabel.addMouseListener(new MouseAdapter() { // клика в/у кошничката за добавяне
			                @Override
			                public void mouseClicked(MouseEvent e) {
			                	Integer currentValue = (Integer) addPanel.priceSpinner.getValue();
			                	
			                	for(int p=0; p<cart.products.size(); p++) { // трябва да се провери дали продукта вече го има в кошницата
			                		if(cart.products.get(p).equals(shop.get(addPanel.categoryID).subcategories.get(addPanel.subcategoryID).products.get(addPanel.productID))) { // ако продукта съществува
			                			cart.quantity.set(p, cart.quantity.get(p)+currentValue); // добавяме количеството към вече съществуващото такова
			                			updateBasket();
			                			return; // добавено е количеството, можем да излезем от функцията без да продължаваме изпълнение надолу
			                		}
			                	}
			                	
			                	//щом не сме излезли от цикъла, значи просто трябва да добавим продукта:
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
					
					// понеже layout-а на панела е null, скрола получава параметър 0 и съответно нищо не става и се налага долния fix
					productsPanel.setBounds(0, 0, productsPanel.getWidth(), productsPanel.getHeight());
					productsPanel.setPreferredSize(new Dimension(productsPanel.getWidth(), productsPanel.getHeight()));
					
					JScrollPane scroll = new JScrollPane(productsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					scroll.setSize(contentWidth-contentWidth/4, contentHeight);
					scroll.setLocation(0, 0);
					shopPanel.add(scroll);
					scroll.setVisible(true);
					scroll.revalidate();
					scroll.repaint();
					
					break; // да не забравим да спрем да търсим xD
				}
			}
		}
	}// end of loadProductsFromSubcategory()
	
	private static ModalFrame modalFrame;
	// ппц модален прозорец се прави с JDialog, но на мен въобще не ми е удобно
	// този JFrame ще върши абсолютно същата работа и няма да може да се работи в/у магазина докато се разглеждат покупките с възможност плащане
	
	private static boolean isBasketUpdated = false; // за да знаем дали да сложим mouseListener на лейбъла с кошницата
	private static void updateBasket() {
		
		int quantity=0;
		for(int i=0; i<cart.quantity.size(); i++) {
			quantity+=cart.quantity.get(i);
		}
		
		if(quantity==0) { // ако няма продукти слагаме текст, че е празна и премахваме лисънера (може да е останал от предишна поръчка)
			cartLabel.setText( "<html><center>Wow,<br>such empty!</center></html>" );
			cartLabel.removeMouseListener(cartListener);
		}
		else { // ако има продукти
			cartLabel.setText("<html><center>" + cart.products.size() + " products (" + quantity + ") <br>in the basket.<br> Go To ></center></html>");
			if(!isBasketUpdated) {
				isBasketUpdated = true;
				cartLabel.addMouseListener(cartListener); // клика в/у количката за добавяне
			}
		}
	}
	
	private static MouseAdapter cartListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(modalFrame == null) // ако прозорецът не е отворен го създаваме
			{
				mainFrame.disable(); //докато сме в прозореца modalFrame, прозорецът с магазина не може да се използва
				modalFrame = new ModalFrame(cart, mainFrame);
				modalFrame.addWindowListener(new WindowAdapter() { // при затваряне на прозореца обекта да става null / по-този начин следим да не се отваря повече от един прозорец с продукти
					@Override
					public void windowClosing(WindowEvent e) {
						modalFrame = null;
						mainFrame.enable(); // при затваряне на прозореца можем отново да използваме магазина
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