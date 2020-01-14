package com.physh_media.blogspot.terraingame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public class MessageBlock 
{
	String[] messages = {"", "", ""};
	
	TrueTypeFont font;
	
	String animState = "closed";
	int hideTimer;
	int yoffset = 315;
	int height;
	
	Color interior = new Color(48, 48, 48, 150); // A nice transparent dark grey
	Color border;
	
	/**
	 * A container for text based messages that's simple to write to. Automatically shows and hides
	 * @param border, should be the global border color
	 * @param font
	 */
	public MessageBlock(Color border, TrueTypeFont font)
	{
		height = 3*(font.getHeight()+5)+5;
		this.border = border;
		this.font = font;
	}
	
	/**
	 * Draw the message box to the screen
	 */
	public void render(Graphics graphics, GameContainer container)
	{
		AppGameContainer gc = (AppGameContainer) container;
		
		int height = 3*(font.getHeight()+5)+5;
		
		graphics.setColor(interior);
		graphics.fillRect(gc.getWidth()-510, gc.getHeight()+yoffset-height-8, 500, height);
		
		graphics.setColor(border);
		graphics.setLineWidth(3);
		graphics.drawRoundRect(gc.getWidth()-510, gc.getHeight()+yoffset-height-8, 500, height, 3);
		
		graphics.setFont(font);
		graphics.setColor(Color.white);
		graphics.drawString(messages[0], gc.getWidth()-500, gc.getHeight()+yoffset-8-height); // Line one
		graphics.drawString(messages[1], gc.getWidth()-500, gc.getHeight()+yoffset-8+(font.getHeight()+5)-height); // Line two
		graphics.drawString(messages[2], gc.getWidth()-500, gc.getHeight()+yoffset-8+2*(font.getHeight()+5)-height); // Line three
		
		animate();
	}
	
	/**
	 * Push a new message to the message box
	 * @param str
	 */
	public void push(String str)
	{
		hideTimer = 0;
		if (animState.equals("closed")) animState = "opening";
		
		if (messages[0].equals("")) // "", "", ""
		{
			messages[0] = str;
		} else if (messages[1].equals("")) { // Message 1, "", ""
			messages[1] = str;
		} else if (messages[2].equals("")){ // Message 1, Message 2, ""
			messages[2] = str;
		} else { // Message 1, Message 2, Message 3
			messages[0] = messages[1];
			messages[1] = messages[2];
			messages[2] = str;
		}
	}

	private void animate()
	{
		// Show/hide timer
		if (animState.equals("open") && hideTimer >= 300) 
		{
			animState = "closing";
		}
		if (animState.equals("open"))
		{
			hideTimer++;
		}
		
		// Animation
		if (animState.equals("opening"))
		{
			if (yoffset > 0) 
			{
				yoffset = yoffset - 5;
				if (yoffset <= 0)
				{
					animState = "open";
				}
			}
		}
		if (animState.equals("closing"))
		{
			if (yoffset < (height+(height%5)+10)) {
				yoffset = yoffset + 5;
			} else { // This fires once it's finished
				animState = "closed";
			}
		}
		if (animState.equals("closed"))
		{
			messages[0] = "";
			messages[1] = "";
			messages[2] = "";
		}
	}
	
	/**
	 * Change the color to something else
	 * @param color
	 */
	public void updateColor(Color color)
	{
		border = color;
	}
}
