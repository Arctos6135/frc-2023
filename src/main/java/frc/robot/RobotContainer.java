// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.arctos6135.robotlib.newcommands.triggers.AnalogTrigger;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.autonomous.AutoAlign;
import frc.robot.commands.autonomous.Engage;
import frc.robot.commands.claw.CloseClaw;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.claw.TeleopClaw;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.driving.TeleopDrive;
import frc.robot.commands.driving.TimedTurn;
import frc.robot.commands.driving.TurnEncoded;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.commands.elevator.Extend;
import frc.robot.commands.elevator.HoldRotate;
import frc.robot.commands.elevator.TelopRotate;
import frc.robot.commands.elevator.TimedRotate;
import frc.robot.commands.scoring.ScoreMiddleRow;
import frc.robot.commands.scoring.SubstationExit;
import frc.robot.commands.scoring.SubstationIntake;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.constants.ClawConstants;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.VisionSystem;

public class RobotContainer {
  // Robot Subsystems
  public final Drivetrain drivetrain;
  private final Claw claw;
  private final Arm arm;
  private final Elevator elevator;
  private final VisionSystem visionSystem;

  // Controllers
  private final XboxController driverController = new XboxController(DriveConstants.DRIVER_CONTROLLER);
  private final XboxController operatorController = new XboxController(DriveConstants.OPERATOR_CONTROLLER);

  // Controller Rumbling

  // Shuffleboard Tabs
  public ShuffleboardTab prematchTab;
  public ShuffleboardTab driveTab;

  public ShuffleboardTab drivetrainTab;
  public ShuffleboardTab armTab;
  public ShuffleboardTab pidControlTab;

  public ShuffleboardTab visionTab;

  public SimpleWidget transWidget;
  public SimpleWidget rotWidget;

  public GenericEntry kPWidgetArm;
  public GenericEntry kIWidgetArm;
  public GenericEntry kDWidgetArm;

  // Network Tables
  public GenericEntry hexEncoderEntry;

  private Autonomous autonomous;

  public RobotContainer() {
    prematchTab = Shuffleboard.getTab("Prematch");
    driveTab = Shuffleboard.getTab("Drive");
    drivetrainTab = Shuffleboard.getTab("Drivetrain");
    armTab = Shuffleboard.getTab("Arm");
    pidControlTab = Shuffleboard.getTab("PID Control Tab");
    visionTab = Shuffleboard.getTab("Vision Tab");

    autonomous = new Autonomous();

    this.drivetrain = new Drivetrain(DriveConstants.RIGHT_MASTER, DriveConstants.LEFT_MASTER,
        DriveConstants.RIGHT_FOLLOWER, DriveConstants.LEFT_FOLLOWER, driveTab);

    this.drivetrain.setDefaultCommand(new TeleopDrive(
        drivetrain, driverController, DriveConstants.DRIVE_FWD_REV, DriveConstants.DRIVE_LEFT_RIGHT, driveTab));

    this.arm = new Arm(ElevatorConstants.ROTATE_CONTROL, ElevatorConstants.HEX_ENCODER_PORT, armTab);
    this.arm.setDefaultCommand(
        new TelopRotate(arm, operatorController, ElevatorConstants.ROTATE_CONTROL, ElevatorConstants.HOLD_ROTATION));

    this.claw = new Claw(ClawConstants.CLAW_MOTOR, armTab);
    this.claw.setDefaultCommand(
        new TeleopClaw(claw, operatorController, ClawConstants.OPEN_CLAW_BUTTON, ClawConstants.CLOSE_CLAW_BUTTON));

    this.elevator = new Elevator(ElevatorConstants.ELEVATOR_MOTOR, armTab);
    this.elevator.setDefaultCommand(new Extend(elevator, operatorController, ElevatorConstants.ELEVATOR_CONTROL));

    this.visionSystem = new VisionSystem();

    configureDashboard();

    configureBindings();
  }

  private void configureDashboard() {
    prematchTab.add("Autonomous Mode", autonomous.getChooser()).withPosition(0, 0).withSize(10, 5);

    drivetrainTab.add("PID Translation", drivetrain.getTranslationalController())
        .withWidget(BuiltInWidgets.kPIDController)
        .withPosition(0, 0).withSize(4, 4);

    drivetrainTab.add("PID Rotation", drivetrain.getRotationController()).withWidget(BuiltInWidgets.kPIDController)
        .withPosition(4, 0).withSize(4, 4);

    armTab.add("PID Controller", arm.getPIDController()).withWidget(BuiltInWidgets.kPIDController)
        .withPosition(0, 0).withSize(1, 3);

    kPWidgetArm = pidControlTab.add("Arm kP", 0).withWidget(BuiltInWidgets.kNumberSlider)
        .withPosition(0, 0).withSize(2, 2).getEntry();

    kIWidgetArm = pidControlTab.add("Arm kI", 0).withWidget(BuiltInWidgets.kNumberSlider)
        .withPosition(2, 0).withSize(2, 2).getEntry();

    kDWidgetArm = pidControlTab.add("Arm kD", 0).withWidget(BuiltInWidgets.kNumberSlider)
        .withPosition(4, 0).withSize(2, 2).getEntry();

    visionTab.add("Limelight Stream", VisionSystem.LIMELIGHT_URL).withWidget(BuiltInWidgets.kCameraStream)
        .withPosition(0, 0).withSize(6, 8);

    hexEncoderEntry = armTab.add("Hex Encoder Distance", arm.getEncoder().getDistance())
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(4, 0).withSize(1, 1).getEntry();
  }

  public void updateDashboard() {
    arm.getPIDController().setP(kPWidgetArm.getDouble(0));
    arm.getPIDController().setI(kIWidgetArm.getDouble(0));
    arm.getPIDController().setD(kDWidgetArm.getDouble(0));

    hexEncoderEntry.setDouble(arm.getEncoder().getDistance());
  }

  private void configureBindings() {
    Trigger precisionDriveButton = new JoystickButton(driverController, DriveConstants.PRECISION_DRIVE_TOGGLE);
    AnalogTrigger precisionDriveTrigger = new AnalogTrigger(driverController, DriveConstants.BOOST_DRIVE_HOLD, 0.5);

    // Trigger tapeAutoAlign = new JoystickButton(driverController,
    // DriveConstants.TAPE_AUTO_ALIGN);
    Trigger aprilTagAutoAlign = new JoystickButton(driverController, DriveConstants.APRIL_TAG_AUTO_ALIGN);

    Trigger scoreMiddleCone = new JoystickButton(operatorController, ElevatorConstants.SCORE_MIDDLE_CONE);
    Trigger scoreMiddleCube = new JoystickButton(operatorController, ElevatorConstants.SCORE_MIDDLE_CUBE);

    Trigger substationIntakeTrigger = new JoystickButton(operatorController, ElevatorConstants.SUBSTATION_INTAKE);


    precisionDriveButton.onTrue(new FunctionalCommand(() -> {
      TeleopDrive.togglePrecisionDrive();
    }, () -> {
    }, (interrupted) -> {
    }, () -> false));

    precisionDriveTrigger.setMinTimeRequired(0.05);
    precisionDriveTrigger.whileTrue(new FunctionalCommand(() -> {
      TeleopDrive.togglePrecisionDrive();
    }, () -> {
    }, (interrupted) -> {
      TeleopDrive.togglePrecisionDrive();
    }, () -> false));

    // tapeAutoAlign.whileTrue(new AutoAlign(this.drivetrain, this.visionSystem,
    // true));

    aprilTagAutoAlign.whileTrue(new AutoAlign(this.drivetrain, this.visionSystem, false));

    scoreMiddleCone.whileTrue(new ScoreMiddleRow(elevator, arm, claw, false));

    scoreMiddleCube.whileTrue(new ScoreMiddleRow(elevator, arm, claw, true));

    substationIntakeTrigger.whileTrue(
        new SubstationIntake(elevator, arm, claw)).onFalse(
            new SubstationExit(elevator, arm, claw));

    /*
     * autoRotateMiddleCube.whileTrue(new AutoRotate(this.arm,
     * -ElevatorConstants.ROTATION_MIDDLE_LEVEL_CUBE))
     * .onFalse(new HoldRotate(this.arm, ElevatorConstants.ARM_HOLD_TIME, false));
     */

    /*
     * autoRotateMiddleCube.whileTrue(new TimedRotate(arm, 1.0, true))
     * .onFalse(new HoldRotate(arm, 2.0, false));
     */
  }

  public Command getAutonomousCommand() {
    // return new DriveForwardEncoded(drivetrain, 0.5, -6 * 3 * 12);
    // return new TurnEncoded(drivetrain, Math.PI, 0.25);
    // return new AutoRotate(arm, 0);
    // return new Engage(drivetrain);
    return autonomous.getAuto(autonomous.getChooser().getSelected(), drivetrain, elevator, arm, claw);
  }
}