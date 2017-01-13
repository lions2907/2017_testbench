package org.usfirst.frc.team2907.robot.commands;

import org.usfirst.frc.team2907.robot.Robot;
import org.usfirst.frc.team2907.robot.subsystems.Camera;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignWithPixy extends CommandGroup
{
	private static final double DEGREES_PER_PIXEL = 75 / 640; // 640x400 default at 75 FOV
	public AlignWithPixy()
	{
		requires(Robot.camera);
		Camera.PixyBlock[] blocks = Robot.camera.read();
		if (blocks != null && blocks.length > 0)
		{
			double offset = (blocks[0].centerX - 320) * DEGREES_PER_PIXEL;
			addSequential(new RotateToAngle(Robot.driveTrain.sensorBoard.getAngle() + offset));
		}
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
