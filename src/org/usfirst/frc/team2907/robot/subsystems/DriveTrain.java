package org.usfirst.frc.team2907.robot.subsystems;

import org.usfirst.frc.team2907.robot.Robot;
import org.usfirst.frc.team2907.robot.RobotMap;
import org.usfirst.frc.team2907.robot.commands.MechDrive;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem 
{
	private CANTalon left1 = new CANTalon(RobotMap.TALON_LEFT_1);
	private CANTalon left2 = new CANTalon(RobotMap.TALON_LEFT_2);
	private CANTalon right1 = new CANTalon(RobotMap.TALON_RIGHT_1);
	private CANTalon right2 = new CANTalon(RobotMap.TALON_RIGHT_2);
	
	public RobotDrive robotDrive;
	
	public AHRS sensorBoard;
    // Put methods for controlling this subsystem
	
    // here. Call these from Commands.
	
	public DriveTrain()
	{
		robotDrive = new RobotDrive(left1, left2, right1, right2);
		try
		{
			sensorBoard = new AHRS(RobotMap.USB_NAVX);
		} catch (Exception e)
		{
			System.out.println("Error instantating sensorBoard : " + e.getMessage());
		}
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new MechDrive());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void mechDrive(double x, double y, double twist, double gyroAngle)
    {
    	robotDrive.mecanumDrive_Cartesian(x, y, twist, gyroAngle);
    }
}

