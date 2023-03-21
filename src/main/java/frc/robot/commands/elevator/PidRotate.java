package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.Arm;

public class PIDRotate extends CommandBase {
  private final Arm arm;
  
  private double setpointAngle;

  /**
   * Autonomously rotates the arm to a set position for scoring.
   * 
   * @param arm
   * @param angle in radians.
   */
  public PIDRotate(Arm arm, double setpointAngle) {
    this.arm = arm;
    this.setpointAngle = setpointAngle;
    
    addRequirements(arm);
  }

  @Override 
  public void initialize() {
    this.arm.resetEncoder();
  }

  @Override 
  public void execute(
    double armAngle,
    double unixEpochTime
  ) {
    double armAngleNew = this.arm.getAngle();
    double unixEpochTimeNew = System.currentTimeMillis() / 1000;
    double armAngularVelocity = (
      chargeStationAngleNew - chargeStationAngle
    ) / (
      unixEpochTimeNew - unixEpochTime
    );

    double armDistanceFromSetpoint = this.setPointAngle - armAngleNew;

    // Built in calculate() method does not support variability
    // in torque to voltage ratio; too much effort to reengineer.
    // bootleg solution proposed:

    this.arm.setMotor(
      (
        (
          ArmConstants.ARM_SPEED_FACTOR
        ) - (
          armAngularVelocity
        )
      ) * (
        armDistanceFromSetpoint
      ) + Math.cos(
        armAngleNew
      ) * (
        ArmConstants.ARM_MASS_FACTOR
      )
    );
    
    DriverStation.reportWarning(Double.toString(pid), false);
  }

  public void raise(double raiseDeltaTime) {
    this.setPointAngle += raiseDeltaTime * ArmConstants.ARM_SPEED_FACTOR;

    if (this.setPointAngle > ArmConstants.ARM_UPPER_BOUND)
      this.setPointAngle = ArmConstants.ARM_UPPER_BOUND;
  }

  public void lower(double lowerDeltaTime) {
    this.setPointAngle -= lowerDeltaTime * ArmConstants.ARM_SENSITIVITY;

    if (this.setpointAngle < ArmConstants.ARM_LOWER_BOUND)
      this.setPointAngle = ArmConstants.ARM_LOWER_BOUND;
  }

  @Override 
  public boolean isFinished() {
    return Math.pow(
      (
        this.arm.getAngle
      ) / (
        57
      ) - (
        this.setPointAngle
      ), - 2
    ) > ArmConstants.ARM_TOLERANCE;
  }

  @Override
  public void end(boolean interrupted) {
    this.arm.resetEncoder(); 
  }

}
