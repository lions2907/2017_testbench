package org.usfirst.frc.team2907.robot.commands;

import org.usfirst.frc.team2907.robot.Robot;
import org.usfirst.frc.team2907.robot.subsystems.Camera;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignWithCamera extends Command
{
	private static final double DEGREES_PER_PIXEL = 75 / 640; // 640x400 default at 75 FOV
	private double offset;
	private boolean inRange;
	
	public AlignWithCamera()
	{
		super("AlignWithCamera");
		requires(Robot.driveTrain);
		requires(Robot.camera);
		Camera.PixyBlock[] blocks = Robot.camera.read();
		if (blocks != null && blocks.length > 0)
		{
			offset = (blocks[0].centerX - 320) * DEGREES_PER_PIXEL;
			inRange = true;
		}
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize()
	{
		if (inRange)
		{
			new RotateToAngle(Robot.driveTrain.sensorBoard.getAngle() + offset);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute()
	{
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished()
	{
		return false;
	}

	// Called once after isFinished returns true
	protected void end()
	{
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted()
	{
	}
}
