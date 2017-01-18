package org.usfirst.frc.team2907.robot.commands;

import org.usfirst.frc.team2907.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateToAngle extends Command {

	static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	static final double kToleranceDegrees = 2.0f;
	
	private PIDController pidController;
	private PIDOutput output;
	
	private double destDegrees;

	public RotateToAngle(double degrees) {
		super("RotateToAngle");
		requires(Robot.driveTrain);
		
		output = new PIDOutput();
		pidController = new PIDController(kP, kI, kD, Robot.driveTrain.sensorBoard, output);
		pidController.setInputRange(-180, 180);
		pidController.setOutputRange(-.5, .5);
		pidController.setAbsoluteTolerance(kToleranceDegrees);
		pidController.setContinuous(true);
		
		destDegrees = degrees;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		pidController.setSetpoint(destDegrees);
		pidController.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		pidController.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
	
	class PIDOutput implements edu.wpi.first.wpilibj.PIDOutput
	{

		public void pidWrite(double output) {
			System.out.println("output : " + output);
			System.out.println("angle : " + Robot.driveTrain.sensorBoard.getAngle());
			Robot.driveTrain.robotDrive.arcadeDrive(0, output);
			//Robot.driveTrain.mechDrive(Robot.oi.leftStick.getX(), Robot.oi.leftStick.getY(), output, Robot.driveTrain.sensorBoard.getAngle());
		}
		
	}
}
