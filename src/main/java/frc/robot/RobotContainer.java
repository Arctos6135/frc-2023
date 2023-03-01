// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Set;

import com.arctos6135.robotlib.newcommands.triggers.AnalogTrigger;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CloseClaw;
import frc.robot.commands.OpenClaw;
import frc.robot.commands.TeleopDrive;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ClawConstants;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  // Robot Subsystems
  private final Drivetrain drivetrain;
  private final Claw claw;

  // Controllers
  private final XboxController driverController = new XboxController(DriveConstants.DRIVER_CONTROLLER);

  // Controller Rumbling

  // Shuffleboard Tabs
  public final ShuffleboardTab prematchTab;

  // Network Tables

  private Autonomous autonomous;

  public RobotContainer() {
    this.drivetrain = new Drivetrain(DriveConstants.RIGHT_MASTER, DriveConstants.LEFT_MASTER,
      DriveConstants.RIGHT_FOLLOWER, DriveConstants.LEFT_FOLLOWER);
    this.drivetrain.setDefaultCommand(new TeleopDrive(
      drivetrain, driverController, DriveConstants.DRIVE_FWD_REV, DriveConstants.DRIVE_LEFT_RIGHT)
    );

    this.claw = new Claw(ClawConstants.CLAW_MOTOR);
    this.claw.setDefaultCommand(new OpenClaw(this.claw, ClawConstants.OPEN_FROM_CONE_TIME));

    prematchTab = Shuffleboard.getTab("Prematch");

    configureDashboard();

    autonomous = new Autonomous();

    configureBindings();
  }

  private void configureDashboard() {
    prematchTab.add("Autonomous Mode", autonomous.getChooser()).withPosition(0, 0).withSize(10, 5);
  }

  private void configureBindings() {
    Trigger precisionDriveButton = new JoystickButton(driverController, DriveConstants.PRECISION_DRIVE_TOGGLE);
    AnalogTrigger precisionDriveTrigger = new AnalogTrigger(driverController, DriveConstants.BOOST_DRIVE_HOLD, 0.5);

    JoystickButton closeClawOnCubeButton = new JoystickButton(driverController, ClawConstants.CLOSE_ON_CUBE_BUTTON);
    JoystickButton closeClawOnConeButton = new JoystickButton(driverController, ClawConstants.CLOSE_ON_CONE_BUTTON);
    JoystickButton openClawFromCubeButton = new JoystickButton(driverController, ClawConstants.OPEN_FROM_CUBE_BUTTON);
    JoystickButton openClawFromConeButton = new JoystickButton(driverController, ClawConstants.OPEN_FROM_CONE_BUTTON);

    precisionDriveButton.onTrue(TeleopDrive.togglePrecisionDrive());

    precisionDriveTrigger.setMinTimeRequired(0.05);
    precisionDriveTrigger.whileTrue(new FunctionalCommand(() -> {
      TeleopDrive.togglePrecisionDrive();
    }, () -> {
    }, (interrupted) -> {
      TeleopDrive.togglePrecisionDrive();
    }, () -> false));

    closeClawOnCubeButton.onTrue(new CloseClaw(this.claw, ClawConstants.CLOSE_ON_CUBE_TIME));
    closeClawOnConeButton.onTrue(new CloseClaw(this.claw, ClawConstants.CLOSE_ON_CONE_TIME));
    openClawFromCubeButton.onTrue(new OpenClaw(this.claw, ClawConstants.OPEN_FROM_CUBE_TIME));
    openClawFromConeButton.onTrue(new OpenClaw(this.claw, ClawConstants.OPEN_FROM_CONE_TIME));
  }

  public Command getAutonomousCommand() {
    return autonomous.getAuto(autonomous.getChooser().getSelected(), drivetrain);
  }
}
