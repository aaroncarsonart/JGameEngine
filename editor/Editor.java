package editor;

import game.graphics.GameFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * The goal of Editor is to edit tiles and tile maps, for fun.
 * <p>
 * So, Two modes are present:
 * <ul>
 * 1. Tile mode - Edit and save individual tiles to TileMaps.
 * 2. Map mode - Create and save a Map, to be played in the game.
 * @author Aaron Carson
 * @version Jul 8, 2015
 */
public class Editor implements KeyListener
{	
	public static final String NAME = "Editor";
	public static final String TILE_MODE_NAME = " Tile Mode";
	public static final String MAP_MODE_NAME  = " Map Mode";
	public static final int TILE_MODE = 1;
	public static final int MAP_MODE  = 2;
	
	public static final int DEFAULT_TILE_SIZE = 16;
	
	// swing components
	private JFrame frame;
	private Container contentPane;
	private JPanel editorPanel;
	private JPanel eastPanel;
	private JPanel southPanel;
	private JPanel westPanel;
	private JPanel previewPanel;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	
	private JColorChooser chooser;
	private Border releaseBorder;
	private Border pressBorder;
	private boolean mousePressed;
	
	// tile image editor components
	private BufferedImage tileImage;
	private int[] pixels;
	private JPanel[][] panels;
	
	private int mode;
	private Random random;
	

	/**
	 * Create new Editor running in TILE_MODE.
	 */
	public Editor(){
		this(TILE_MODE);
	}
	
	/**
	 * Create new Editor.
	 * @param mode The initial Editor mode to run in 
	 */
	public Editor(int mode){
		this.mode = mode;
		random = new Random();
		Color highlight = new Color(255, 255, 255, 120);
		Color shadow    = new Color(0, 0, 0, 120);
		releaseBorder = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, highlight, shadow);
		pressBorder = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED, highlight, shadow);
		mousePressed = false;
	}
	
	/**
	 * Get the full name of the Editor, based off of the mode.
	 * @return
	 */
	public String getName(){
		return NAME + (mode == TILE_MODE ? TILE_MODE_NAME : MAP_MODE_NAME);
	}
	
	/**
	 * Setup all the J
	 */
	private void setup(){
		frame = new JFrame(getName());
		frame.addKeyListener(Editor.this);
		contentPane = frame.getContentPane();	
		
		// ********************************************
		// setup editorPanel
		// ********************************************
		createTileEditorPanel(DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
		contentPane.add(editorPanel, BorderLayout.CENTER);
		
		// ********************************************
		// setup tool bar areas
		// ********************************************
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		contentPane.add(eastPanel, BorderLayout.EAST);
		
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		contentPane.add(westPanel, BorderLayout.WEST);
		
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		// ********************************************
		// setup image preview
		// ********************************************		
		JLabel label = new JLabel("Image Preview");
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		eastPanel.add(label);	
		
		previewPanel = new JPanel(){
			public void paintComponent(Graphics g){
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.drawImage(tileImage, 0, 0, getWidth(), getHeight(), null);
			}
		};
		
		Dimension d = new Dimension(tileImage.getWidth() * 4, tileImage.getHeight() * 4);
		previewPanel.setPreferredSize(d);
		previewPanel.setMaximumSize(d);
		previewPanel.setMinimumSize(d);
		//JPanel previewContainer = new JPanel(new BorderLayout());
		//previewContainer.add(new JLabel("Image Preview"), BorderLayout.NORTH);
		//previewContainer.add(previewPanel, BorderLayout.CENTER);
		//previewContainer.add(new JPanel(), BorderLayout.EAST);
		previewPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		previewPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		eastPanel.add(previewPanel);
		
		
		// ********************************************
		// setup color chooser
		// ********************************************
		chooser = new JColorChooser();
		chooser.setPreviewPanel(new JPanel());
		chooser.setAlignmentX(Component.LEFT_ALIGNMENT);
		eastPanel.add(chooser);
		
		// ********************************************
		// File menu
		// ********************************************
		menuBar = new JMenuBar(); 
		frame.setJMenuBar(menuBar);
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		// quit menu item
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.VK_CONTROL));
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(quitItem);
		
		// ********************************************
		// Edit menu
		// ********************************************		
		editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
	
		// ********************************************
		// View menu
		// ********************************************
		viewMenu = new JMenu("View");
		menuBar.add(viewMenu);
		
		// show color picker menu item
		final JCheckBoxMenuItem showColorPicker = new JCheckBoxMenuItem("Show Color Picker", true);
		showColorPicker.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.VK_CONTROL));
		showColorPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(showColorPicker.isSelected()){
					editorPanel.setPreferredSize(new Dimension(editorPanel.getWidth(), editorPanel.getHeight()));
					eastPanel.add(chooser);
					frame.pack();
				}
				else{
					editorPanel.setPreferredSize(new Dimension(editorPanel.getWidth(), editorPanel.getHeight()));
					eastPanel.remove(chooser);
					frame.pack();
				}
			}
		});
		viewMenu.add(showColorPicker);		
	}
	
	/**
	 * Create the editor panel used for editing and displaying individual tiles.
	 * @return
	 */
	private void createTileEditorPanel(int width, int height){
		// create editor
		editorPanel = new JPanel(new GridLayout(height, width)){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
			}
		};
		//editorPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		editorPanel.setBackground(Color.BLACK);
		panels = new JPanel[width][height];
		
		// create hardware-accelerated image
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		// initialize game graphics layers
		tileImage = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		pixels = ((DataBufferInt)tileImage.getRaster().getDataBuffer()).getData();
		
		// get preferred display sizes
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int size = (int)Math.floor((0.75 * Math.min(screen.width, screen.height)) / (Math.max(width,  height) ));	
		Dimension preferredSize = new Dimension(size, size);
		
		// add panels for each pixel.
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				@SuppressWarnings("serial")
				EditPanel p = new EditPanel(x, y);
				p.setPreferredSize(preferredSize);
				
				// setup the color of the pixel.
				//int rgb = random.nextInt();
				//int rgb = pixels[x + y * width];
				//pixels[x + y * width] = rgb;
				p.setBackground(new Color(0,0,0,0));
				//p.setBackground(new Color(rgb));
				
				p.addMouseListener(new MouseListener(){
					private boolean mouseIsOverPanel;
					@Override
					public void mouseClicked(MouseEvent e) {					
					}
					@Override
					public void mousePressed(MouseEvent e) {
						mousePressed = true;
						if (mouseIsOverPanel){
							p.setBorder(pressBorder);
							p.setBackground(chooser.getColor());
							pixels[p.y + p.x * width] = chooser.getColor().getRGB();

						}
						editorPanel.repaint();
						previewPanel.repaint();
					}
					@Override
					public void mouseReleased(MouseEvent e) {
						mousePressed = false;
						if (mouseIsOverPanel) p.setBorder(releaseBorder);
						editorPanel.repaint();
						previewPanel.repaint();
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						mouseIsOverPanel = true;
						if(mousePressed){
							p.setBorder(pressBorder);
							p.setBackground(chooser.getColor());
							pixels[p.y + p.x * width] = chooser.getColor().getRGB();				
						}
						else p.setBorder(releaseBorder);
						editorPanel.repaint();
						previewPanel.repaint();
					}
					@Override
					public void mouseExited(MouseEvent e) {
						mouseIsOverPanel = false;
						p.setBorder(null);
						editorPanel.repaint();
					}
					
				});
				
				editorPanel.add(p);
				panels[x][y] = p;
			}
		}
	}

	@SuppressWarnings("serial")
	public class EditPanel extends JPanel {
		
		/** The x Position in the grid. */ 
		public final int x;
		/** The y Position in the grid. */ 
		public final int y;
		
		/**
		 * Create a new EditPanel.
		 * @param x The x position.
		 * @param y The y position.
		 */
		public EditPanel(int x, int y){
			super();
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void paintComponent(Graphics g){
			Color c1 = new Color(0x666666);
			Color c2 = new Color(0x999999);
			int w = getWidth();
			int h = getHeight();
			int hw = w / 2;
			int hh = h / 2;
			g.setColor(c1);
			g.fillRect(0, 0, w, h);
			g.setColor(c2);
			g.fillRect(0, 0, hw, hh);
			g.fillRect(hw, hh, hw, hh);
			super.paintComponent(g);
		}
	};
	
	
	/**
	 * Create the editor panel used for editing and displaying maps.
	 * @return
	 */
	private JPanel createMapEditorPanel(){
		JPanel panel = new JPanel();
		
		return panel;
	}

	/**
	 * Start the Game, initializing all graphics and windows.
	 */
	public void start() {
		setup();	
		try {
			GameFrame.tryAppleLookAndFeel();
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {				
					frame.pack();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			});			
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args){
		Editor e = new Editor();
		e.start();
		
	}
}
