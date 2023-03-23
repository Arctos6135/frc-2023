// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.claw.TeleopClaw;
import frc.robot.commands.driving.TeleopDrive;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.commands.elevator.TeleopExtend;
import frc.robot.commands.elevator.TeleopRotate;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.constants.CANBus;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.Controllers;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.VisionSystem;
import frc.robot.subsystems.WheelClaw;

public class RobotContainer {
  // Robot Subsystems
  public final Drivetrain drivetrain;
  //private final Claw claw;
  private final Arm arm;
  private final Elevator elevator;
  //private final WheelClaw wheelClaw; 
  private final VisionSystem visionSystem;

  // Controllers
  private final XboxController driverController = new XboxController(DriveConstants.DRIVER_CONTROLLER);
  private final XboxController operatorController = new XboxController(DriveConstants.OPERATOR_CONTROLLER);

  // Controller Rumbling

  // Shuffleboard Tabs
  public ShuffleboardTab prematchTab = Shuffleboard.getTab("Prematch");
  public ShuffleboardTab drivetrainTab = Shuffleboard.getTab("Drivetrain");
  public ShuffleboardTab armTab = Shuffleboard.getTab("Arm");
  public ShuffleboardTab visionTab = Shuffleboard.getTab("Vision Tab");

  public GenericEntry armSetpointWidget;

  // Autonomous mode selection
  private Autonomous autonomous = new Autonomous();

  public RobotContainer() {
    this.drivetrain = new Drivetrain(drivetrainTab);

    this.drivetrain.setDefaultCommand(new TeleopDrive(
        drivetrain, driverController, Controllers.DRIVE_FWD_REV, Controllers.DRIVE_LEFT_RIGHT, drivetrainTab));

    this.arm = new Arm(armTab);
    this.arm.setDefaultCommand(
        new TeleopRotate(arm, operatorController, Controllers.ROTATE_CONTROL, Controllers.HOLD_ROTATION));

    this.elevator = new Elevator(armTab);
    this.elevator.setDefaultCommand(new TeleopExtend(elevator, operatorController, Controllers.ELEVATOR_CONTROL));
/*
    this.claw = new Claw(CANBus.CLAW_MOTOR, armTab);
    this.claw.setDefaultCommand(
        new TeleopClaw(claw, operatorController, ClawConstants.OPEN_CLAW_BUTTON, ClawConstants.CLOSE_CLAW_BUTTON)); 

    this.wheelClaw = new WheelClaw(CANBus.CLAW_MOTOR, CANBus.CLAW_MOTOR);
 */
    this.visionSystem = new VisionSystem();

    configureDashboard();
    
    configureBindings();
  }

  private void configureDashboard() {
    prematchTab.add("Autonomous Mode", autonomous.getChooser()).withPosition(0, 0).withSize(10, 5);

    drivetrainTab.add("PID Translation", drivetrain.translationalController)
        .withWidget(BuiltInWidgets.kPIDController)
        .withPosition(0, 0).withSize(1, 2);

    drivetrainTab.add("PID Rotation", drivetrain.rotationController).withWidget(BuiltInWidgets.kPIDController)
        .withPosition(1, 0).withSize(1, 2);

    armTab.add("PID Controller", arm.getPIDController()).withWidget(BuiltInWidgets.kPIDController)
        .withPosition(0, 0).withSize(1, 2);

    armSetpointWidget = armTab.add("Setpoint", 0).withWidget(BuiltInWidgets.kTextView)
        .withPosition(3, 3).withSize(1, 1).getEntry();

    visionTab.add("Limelight Stream", VisionSystem.LIMELIGHT_URL).withWidget(BuiltInWidgets.kCameraStream)
        .withPosition(0, 0).withSize(6, 8);
  }

  private void configureBindings() {
   Trigger precisionDrive = new JoystickButton(driverController, XboxController.Button.kRightBumper.value);
   Trigger moveArm = new JoystickButton(operatorController, XboxController.Button.kA.value);

   precisionDrive.whileTrue(new FunctionalCommand(() -> {
    TeleopDrive.setPrecisionDrive(true);
   }, () -> {}, (interrupted) -> {
    TeleopDrive.setPrecisionDrive(false);
   }, () -> false));
   moveArm.onTrue(new PidRotate(arm, armSetpointWidget));
  }

  public Command getAutonomousCommand() {
    // return new DriveForwardEncoded(drivetrain, 0.5, -6 * 3 * 12);
    // return new TurnEncoded(drivetrain, Math.PI, 0.25);
    // return new AutoRotate(arm, 0);
    // return new Engage(drivetrain);
    return autonomous.getAuto(autonomous.getChooser().getSelected(), drivetrain, elevator, arm, null);
  }
}