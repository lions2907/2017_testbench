package org.usfirst.frc.team2907.robot.subsystems;

import org.usfirst.frc.team2907.robot.RobotMap;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem
{
	public static final int MAX_BLOCKS = 20;
	public static final int BLOCK_SIZE = 14;
	private I2C port;

	public Camera()
	{
		try {
			port = new I2C(edu.wpi.first.wpilibj.I2C.Port.kOnboard, 0x54);
		} catch (Exception e)
		{
			System.out.println("e : " + e.getLocalizedMessage());
		}
	}

	public void initDefaultCommand()
	{
	}

	public PixyBlock[] read()
	{
		PixyBlock[] pixyBlocks = new PixyBlock[MAX_BLOCKS];
		int index = 0;
		byte[] bytes = new byte[BLOCK_SIZE * MAX_BLOCKS];
		port.read(0x54, BLOCK_SIZE * MAX_BLOCKS, bytes);
		System.out.println("Bytes read : " + bytes);
		for (int byteOffset = 0; byteOffset < bytes.length - BLOCK_SIZE - 1;)
		{
			// checking for sync block
			int b1 = bytes[byteOffset];
			if (b1 < 0)
				b1 += 256;
			
			int b2 = bytes[byteOffset + 1];
			if (b2 < 0)
				b2 += 256;
			
			if (b1 == 0x55 && b2 == 0xaa)
			{
				// copy block into temp buffer
				byte[] temp = new byte[BLOCK_SIZE];
				for (int tempOffset = 0; tempOffset < BLOCK_SIZE; ++tempOffset)
					temp[tempOffset] = bytes[byteOffset + tempOffset];

				PixyBlock block = bytesToBlock(temp);
				if (block != null)
				{
					pixyBlocks[index++] = block;
					byteOffset += BLOCK_SIZE - 1;
				} else 
					++byteOffset;
			}
		}
		return pixyBlocks;
	}
	
	public PixyBlock bytesToBlock(byte[] bytes)
	{
		PixyBlock pixyBlock = new PixyBlock();
		pixyBlock.sync = bytesToInt(bytes[1], bytes[0]);
		pixyBlock.checksum = bytesToInt(bytes[3], bytes[2]);
		
		// if the checksum is 0 or the checksum is a sync byte, then there
        // are no more frames.
		if (pixyBlock.checksum == 0 || pixyBlock.checksum == 0xaa55)
			return null;
		
		pixyBlock.signature = bytesToInt(bytes[5], bytes[4]);
		pixyBlock.centerX = bytesToInt(bytes[7], bytes[6]);
		pixyBlock.centerY = bytesToInt(bytes[9], bytes[8]);
		pixyBlock.width = bytesToInt(bytes[11], bytes[10]);
		pixyBlock.height = bytesToInt(bytes[13], bytes[12]);
		return pixyBlock;
	}
	
	public int bytesToInt(int b1, int b2)
	{
		if (b1 < 0)
			b1 += 256;
		
		if (b2 < 0)
			b2 += 256;
		
		int intValue = b1 * 256;
		intValue += b2;
		return intValue;
	}

	public class PixyBlock
	{
		// 0, 1 0 sync (0xaa55)
		// 2, 3 1 checksum (sum of all 16-bit words 2-6)
		// 4, 5 2 signature number
		// 6, 7 3 x center of object
		// 8, 9 4 y center of object
		// 10, 11 5 width of object
		// 12, 13 6 height of object

		public int sync;
		public int checksum;
		public int signature;
		public int centerX;
		public int centerY;
		public int width;
		public int height;
	}
}
