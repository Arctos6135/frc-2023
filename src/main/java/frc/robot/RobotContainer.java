// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.TeleopDrive;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private final Drivetrain drivetrain; 
  private final XboxController driverController = new XboxController(DriveConstants.DRIVER_CONTROLLER); 

  public RobotContainer() {
    this.drivetrain = new Drivetrain(DriveConstants.RIGHT_MASTER, DriveConstants.LEFT_MASTER, 
      DriveConstants.RIGHT_FOLLOWER, DriveConstants.LEFT_FOLLOWER); 
    this.drivetrain.setDefaultCommand(new TeleopDrive(
      drivetrain, driverController, DriveConstants.DRIVE_FWD_REV, DriveConstants.DRIVE_LEFT_RIGHT)
    );

    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
