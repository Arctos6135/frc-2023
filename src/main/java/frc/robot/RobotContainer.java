// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.arctos6135.robotlib.oi.XboxControllerButtons;
import com.arctos6135.robotlib.oi.buttons.AnalogButton;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.autonomous.MidCubeBalanceAuto;
import frc.robot.commands.claw.TeleopClaw;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.driving.DriveTowardsCube;
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
  private Claw backupClaw;

  public RobotContainer() {
    this.drivetrain = new Drivetrain(drivetrainTab);
    this.drivetrain.setDefaultCommand(new TeleopDrive(
        drivetrain, driverController, Controllers.DRIVE_FWD_REV, Controllers.DRIVE_LEFT_RIGHT, drivetrainTab));
    

    this.arm = new Arm(armTab);
    this.arm.setDefaultCommand(new TeleopRotate(arm, operatorController, Controllers.ROTATE_CONTROL));
      
    this.elevator = new Elevator(armTab);
    this.elevator.setDefaultCommand(new TeleopExtend(elevator, operatorController, Controllers.ELEVATOR_CONTROL));
    this.wheelClaw = new WheelClaw();
    this.wheelClaw.setDefaultCommand(new TeleopIntake(wheelClaw, operatorController)); 
    this.backupClaw = null;

    //this.wheelClaw = null;
    //this.backupClaw = new Claw();
    //backupClaw.setDefaultCommand(new TeleopClaw(backupClaw, operatorController));

    this.visionSystem = new VisionSystem();

    configureDashboard();
    
    configureBindings();
  }

  private void configureDashboard() {
    SmartDashboard.putData("Autonomous Mode", autonomous.getChooser());
    SmartDashboard.putData("Drivetrain", drivetrain);
    SmartDashboard.putData("Elevator", elevator);
    SmartDashboard.putData("Arm", arm);
  }

  private void configureBindings() {
    Trigger outtake = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
    Trigger intake = new JoystickButton(operatorController, XboxController.Button.kRightBumper.value);

    Trigger scoreMidCube = new JoystickButton(operatorController, XboxController.Button.kY.value);
    Trigger scoreLowCube = new JoystickButton(operatorController, XboxController.Button.kB.value);
    scoreMidCube.whileTrue(Score.scoreMidCube(arm));
    scoreLowCube.whileTrue(Score.scoreLowCube(arm));

    Trigger intakeSubstation = new JoystickButton(operatorController, XboxController.Button.kX.value);
    intakeSubstation.whileTrue(Intake.intakeSubstation(arm, wheelClaw));
    Trigger intakeGround = new JoystickButton(operatorController, XboxController.Button.kA.value);
    intakeGround.whileTrue(Intake.intakeGround(arm, wheelClaw));

    Trigger driveIntakeCube = new JoystickButton(driverController, XboxController.Button.kX.value);
    driveIntakeCube.whileTrue(Intake.autoIntakeGround(arm, wheelClaw, visionSystem, drivetrain));
  }

  public Command getAutonomousCommand() {
    return autonomous.getAuto(autonomous.getChooser().getSelected(), drivetrain, elevator, arm, wheelClaw, drivetrainTab);
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

  public void resetSensors() {
    drivetrain.resetGyro();
  }

  public void whileDisabled() {
    arm.disableBrake();
  }

  public void whenEnabled() {
    arm.enableBrake();
  }
}
