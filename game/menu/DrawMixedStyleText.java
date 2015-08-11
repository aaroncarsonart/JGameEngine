package game.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.JFrame;

public class DrawMixedStyleText
{
	
	/**
	 * Test stuff.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		testStringParsing();
		//testFrame();
	}
	
	public static void testFrame() {
		// Create a frame
		JFrame frame = new JFrame();
		
		// Add a component with a custom paint method
		frame.add(new CustomPaintComponent());
		
		// Display the frame
		int frameWidth = 300;
		int frameHeight = 300;
		
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public static void testStringParsing() {
		String s = "<bold>This <green>is</bold> a</green> test String";
		System.out.printf("Initial String: %s\n", s);
		StringBuilder sb = new StringBuilder(s.length());
		Map<TextAttribute, Object> m = new WeakHashMap<>();
		
		
		String open = "<";
		String openEnd = "</";
		String close = ">";
		
		
		// currentIndex to search from.
		int prevIndex = 0;
		
		// the total offsets to be applied to further formats.
		int offset = 0;
		
		while(prevIndex != -1) {
			// step 1: check for opening bracket.
			int openIndex = s.indexOf(open, prevIndex);
			while (openIndex != -1 && openIndex < s.length() - 1 && s.charAt(openIndex + 1) == '/'){
				System.out.println("found closing tag, skipping");
				prevIndex = openIndex +2;
				openIndex = s.indexOf(open, prevIndex);
			}
			
			if (openIndex != -1) {
				System.out.printf("open: %d\n", openIndex);
				int endIndex = s.indexOf(close, openIndex);
				
				// step 2: check for closing bracket of opening tag
				if (endIndex != -1) {
					System.out.printf("end: %d\n", endIndex);
					
					String format = s.substring(openIndex + 1, endIndex);
					System.out.printf("format: %s\n", format);
					
					String openingTag = open + format + close;
					String closingTag = openEnd + format + close;
					prevIndex = openIndex + closingTag.length();
					System.out.printf("openingTag: %s\n", openingTag);
					System.out.printf("closingTag: %s\n", closingTag);
					int closeIndex = s.indexOf(closingTag);
					
					
					
					// step 3: check for closing tag.
					if (closeIndex != -1) {
						System.out.printf("close: %d\n", closeIndex);
						int start = openIndex + format.length() + 2;
						int end = closeIndex;
						System.out.printf("start: %d end: %d\n", start, end);
						String enclosed = s.substring(start, end);
						System.out.printf("enclosed: %s\n", enclosed);
					}
					
					// closing tag not found.
					else {
						System.out.printf("Closing tag %s not found\n",
								closingTag);
					}
					
				}
				
				// closing bracket of opening tag not found.
				else {
					System.out.printf("Closing bracket %s not found\n", close);
					
					// step forward one character.
					prevIndex = openIndex + 1;
				}
				
			}
			
			// opening bracket not found.
			else {
				System.out.printf("Opening bracket %s tag not found\n", open);
				prevIndex = -1;
				
			}
		}		
	}
	
	/**
	 * To draw on the screen, it is first necessary to subclass a Component and
	 * override its paint() method. The paint() method is automatically calle by
	 * the windowing system whenever component's area needs to be repainted.
	 */
	static class CustomPaintComponent extends Component
	{
		
		public void paint(Graphics g) {
			
			// Retrieve the graphics context; this object is used to paint
			// shapes
			
			Graphics2D g2d = (Graphics2D) g;
			
			/**
			 * The coordinate system of a graphics context is such that the
			 * origin is at the northwest corner and x-axis increases toward the
			 * right while the y-axis increases toward the bottom.
			 */
			
			int x = 0;
			int y = 20;
			
			// Set the desired font if different from default font
			Font font = new Font("ProFont", Font.BOLD, 10);
			
			// Apply styles to text
			String s = "This is a test String.";
			
			AttributedString astr = new AttributedString(s);
			
			astr.addAttribute(TextAttribute.FONT, font, 5, 15);
			astr.addAttribute(TextAttribute.FOREGROUND, Color.RED, 0, 7);
			astr.addAttribute(TextAttribute.BACKGROUND, Color.CYAN, 13, 21);
			
			// Draw mixed-style text such that its base line is at x, y
			TextLayout tl = new TextLayout(astr.getIterator(),
					g2d.getFontRenderContext());
			tl.draw(g2d, x, y);
			
		}
		
		public static final String	C_GREEN	= "{g}";
		
		public static AttributedString getAttributedStringFor(String original) {
			
			AttributedString a = new AttributedString("This is a test string");
			return a;
			
		}
		
	}
	
}