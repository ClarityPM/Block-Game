package com.physh_media.blogspot.terraingame.structure;

import com.physh_media.blogspot.terraingame.Block;

public class Structure
{
	String[][] buffer;
	
	// A structure represents a set of tiles that can be placed into the world.
	// Use a child class and override to make new structures.
	public Structure(int width, int height)
	{
		buffer = new String[height][width];
		setUp();
	}
	
	public void generate(int x, int y, Block[][] world)
	{
		for (int iy = 0; iy < buffer.length; iy++)
		{
			for (int ix = 0; ix < buffer[iy].length; ix++)
			{
				try {
					// Try not to overwrite anything other than air
					if (world[y+iy][x+ix].getType().equals("air"))
						world[y+iy][x+ix] = new Block(x+ix, y+iy, buffer[iy][ix]);
				} catch (ArrayIndexOutOfBoundsException error) {}
			}
		}
	}
	
	void setUp()
	{
		
	}
}
