package utils;

import java.awt.Font;

public class AppFont {

	private static Font platinum;
	private static Font ka1;
	
	static {
	    try {
	    	ka1 = Font.createFont(
	                Font.TRUETYPE_FONT,
	                AppFont.class.getResourceAsStream("/fonts/ka1.ttf"));

	        platinum = Font.createFont(
	                Font.TRUETYPE_FONT,
	                AppFont.class.getResourceAsStream("/fonts/PlatinumSign.ttf"));
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        ka1 = new Font("SansSerif", Font.PLAIN, 14);
	        platinum = new Font("Serif", Font.BOLD, 30);
	    }
	}
	
    public static Font title() {
        return platinum.deriveFont(Font.BOLD, 30f);
    }
	
	public static Font subtitle() {
		return ka1.deriveFont(Font.BOLD, 22f);
	}
	
    public static Font big() {
        return ka1.deriveFont(Font.BOLD, 18f);
    }

    public static Font normal() {
        return ka1.deriveFont(Font.BOLD, 15f);
    }    
}
