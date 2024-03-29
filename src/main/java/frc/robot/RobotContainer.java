// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.arctos6135.robotlib.oi.buttons.AnalogButton;
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
import frc.robot.commands.autonomous.MidCubeBalanceAuto;
import frc.robot.commands.claw.TeleopClaw;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.driving.TeleopDrive;
import frc.robot.commands.elevator.EncodedElevator;
import frc.robot.commands.elevator.PidExtend;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.commands.elevator.TeleopExtend;
import frc.robot.commands.elevator.TeleopRotate;
import frc.robot.commands.elevator.TimedElevator;
import frc.robot.commands.intake.Intake;
import frc.robot.commands.intake.RawIntake;
import frc.robot.commands.intake.RawOuttake;
import frc.robot.commands.scoring.Score;
import frc.robot.commands.wheelclaw.TeleopIntake;
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
  private final Arm arm;
  private final Elevator elevator;
  private final WheelClaw wheelClaw; 
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


  // Autonomous mode selection
  private Autonomous autonomous = new Autonomous();

  public RobotContainer() {
    this.drivetrain = new Drivetrain(drivetrainTab);
    this.drivetrain.setDefaultCommand(new TeleopDrive(
        drivetrain, driverController, Controllers.DRIVE_FWD_REV, Controllers.DRIVE_LEFT_RIGHT, drivetrainTab));
    
    this.arm = new Arm(armTab);
    this.arm.setDefaultCommand(//new PidRotate(arm, 0.5));
    new TeleopRotate(arm, operatorController, Controllers.ROTATE_CONTROL));
      
    this.elevator = new Elevator(armTab);
    this.elevator.setDefaultCommand(new TeleopExtend(elevator, operatorController, Controllers.ELEVATOR_CONTROL));
      //new PidExtend(elevator, 0));

    this.wheelClaw = new WheelClaw();
    this.wheelClaw.setDefaultCommand(new TeleopIntake(wheelClaw, operatorController)); 

    this.visionSystem = new VisionSystem();

    configureDashboard();
    
    configureBindings();
  }

  private void configureDashboard() {
    prematchTab.add("Autonomous Mode", autonomous.getChooser()).withPosition(0, 0).withSize(10, 5);

    drivetrainTab.add("PID Translation", drivetrain.translationalController)
        .withWidget(BuiltInWidgets.kPIDController)
        .withPosition(0, 0).withSize(1, 4);

    drivetrainTab.add("PID Rotation", drivetrain.rotationController).withWidget(BuiltInWidgets.kPIDController)
        .withPosition(1, 0).withSize(1, 4);

    //armTab.add("PID Controller", arm.getPIDController()).withWidget(BuiltInWidgets.kPIDController)
    //    .withPosition(0, 0).withSize(1, 4);

    visionTab.add("Limelight Stream", VisionSystem.LIMELIGHT_URL).withWidget(BuiltInWidgets.kCameraStream)
        .withPosition(0, 0).withSize(6, 8);
  }

  private void configureBindings() {
    Trigger outtake = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
    Trigger intake = new JoystickButton(operatorController, XboxController.Button.kRightBumper.value);
    /*
    Trigger intakeGround = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
    Trigger intakeSubstation = new JoystickButton(operatorController, XboxController.Button.kRightBumper.value);
    Trigger scoreLow = new JoystickButton(operatorController, XboxController.Button.kA.value); */ 
    Trigger scoreMidCube = new JoystickButton(operatorController, XboxController.Button.kA.value);
    Trigger scoreMidCone = new JoystickButton(operatorController, XboxController.Button.kX.value);
    scoreMidCube.whileTrue(Score.scoreMidCube(arm, elevator));

    //outtake.whileTrue(new RawOuttake(wheelClaw));
    //intake.whileTrue(new RawIntake(wheelClaw));
/*
    intakeGround.whileTrue(Intake.intakeGround(arm, elevator, wheelClaw));
    intakeSubstation.whileTrue(Intake.intakeSubstation(arm, elevator, wheelClaw));
    scoreLow.whileTrue(Score.scoreLow(arm, elevator));
    scoreMidCone.whileTrue(Score.scoreMidCone(arm, elevator));
    */

  }

  public Command getAutonomousCommand() {
    // return new DriveForwardEncoded(drivetrain, 0.5, -6 * 3 * 12);
    // return new TurnEncoded(drivetrain, Math.PI, 0.25);
    // return new AutoRotate(arm, 0);
    // return new Engage(drivetrain);
    return MidCubeBalanceAuto.midCubeBalanceAuto(drivetrain, arm, elevator, wheelClaw, drivetrainTab);
    //autonomous.getAuto(autonomous.getChooser().getSelected(), drivetrain, elevator, arm, null);
  }

  public void updateButtons() {
    if (driverController.getRightTriggerAxis() > 0.2) {
      TeleopDrive.setPrecisionDrive(true);
      drivetrain.setIdleMode(IdleMode.kBrake);
    } else {
      TeleopDrive.setPrecisionDrive(false); 
      drivetrain.setIdleMode(IdleMode.kBrake);
    }
  }
}