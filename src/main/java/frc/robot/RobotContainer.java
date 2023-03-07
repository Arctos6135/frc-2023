// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import com.arctos6135.robotlib.newcommands.triggers.AnalogTrigger;

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
import frc.robot.commands.claw.CloseClaw;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.driving.DriveForward;
import frc.robot.commands.driving.TeleopDrive;
import frc.robot.commands.driving.Turn;
import frc.robot.commands.elevator.AutoRotate;
import frc.robot.commands.elevator.Extend;
import frc.robot.commands.elevator.Rotate;
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
  private final Drivetrain drivetrain;
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

  public SimpleWidget transWidget;
  public SimpleWidget rotWidget;


  // Network Tables

  private Autonomous autonomous;

  public RobotContainer() {
    autonomous = new Autonomous();

    configureDashboard();

    this.drivetrain = new Drivetrain(DriveConstants.RIGHT_MASTER, DriveConstants.LEFT_MASTER,
      DriveConstants.RIGHT_FOLLOWER, DriveConstants.LEFT_FOLLOWER, driveTab);
    this.drivetrain.setDefaultCommand(new DriveForward(0.1, 1, drivetrain));
    //this.drivetrain.setDefaultCommand(new Turn(drivetrain, 90));
    /*
    this.drivetrain.setDefaultCommand(new TeleopDrive(
      drivetrain, driverController, DriveConstants.DRIVE_FWD_REV, DriveConstants.DRIVE_LEFT_RIGHT, transWidget, rotWidget)
    );
    */
    
    this.claw = null;
    this.elevator = null;
    this.arm = null;
    /*
    this.arm = new Arm(ElevatorConstants.ROTATE_CONTROL, ElevatorConstants.HEX_ENCODER_PORT, kPWidget, kIWidget, kDWidget);
    this.arm.setDefaultCommand(new Rotate(arm, operatorController, ElevatorConstants.ROTATE_CONTROL)); // has to happen after so the widgets are defined

    this.claw = new Claw(ClawConstants.CLAW_MOTOR);

    this.elevator = new Elevator(ElevatorConstants.ELEVATOR_MOTOR);
    this.elevator.setDefaultCommand(new Extend(elevator, operatorController, ElevatorConstants.ELEVATOR_CONTROL)); 
*/
    this.visionSystem = new VisionSystem();
    


    //configureBindings();
  }

  private void configureDashboard() {
    prematchTab = Shuffleboard.getTab("Prematch"); 
    driveTab = Shuffleboard.getTab("Drive"); 

    prematchTab.add("Autonomous Mode", autonomous.getChooser()).withPosition(0, 0).withSize(10, 5);

    transWidget = driveTab.add("translation speed", 1).withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0.0, "max", 1.0));
    rotWidget = driveTab.add("rotation speed", 1).withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0.0, "max", 1.0));
  }

  private void configureBindings() {
    Trigger precisionDriveButton = new JoystickButton(driverController, DriveConstants.PRECISION_DRIVE_TOGGLE);
    AnalogTrigger precisionDriveTrigger = new AnalogTrigger(driverController, DriveConstants.BOOST_DRIVE_HOLD, 0.5);

    Trigger tapeAutoAlign = new JoystickButton(driverController, DriveConstants.TAPE_AUTO_ALIGN); 
    Trigger aprilTagAutoAlign = new JoystickButton(driverController, DriveConstants.APRIL_TAG_AUTO_ALIGN); 

    Trigger openClawButton = new JoystickButton(operatorController, ClawConstants.OPEN_CLAW_BUTTON);
    Trigger closeClawButton = new JoystickButton(operatorController, ClawConstants.CLOSE_CLAW_BUTTON);

    Trigger autoRotateMiddleCone = new JoystickButton(operatorController, ElevatorConstants.AUTO_ROTATE_MIDDLE_CONE);
    Trigger autoRotateMiddleCube = new JoystickButton(operatorController, ElevatorConstants.AUTO_ROTATE_MIDDLE_CUBE); 

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

    tapeAutoAlign.whileTrue(new AutoAlign(this.drivetrain, this.visionSystem, true)); 

    aprilTagAutoAlign.whileTrue(new AutoAlign(this.drivetrain, this.visionSystem, false)); 

    openClawButton.onTrue(new OpenClaw(this.claw, 1.0)); 
    closeClawButton.onTrue(new CloseClaw(this.claw, 1.0)); 

    autoRotateMiddleCone.whileTrue(new AutoRotate(this.arm, ElevatorConstants.ROTATION_MIDDLE_LEVEL_CONE))
      .onFalse(new AutoRotate(this.arm, -ElevatorConstants.ROTATION_MIDDLE_LEVEL_CONE)); 

    autoRotateMiddleCube.whileTrue(new AutoRotate(this.arm, ElevatorConstants.ROTATION_MIDDLE_LEVEL_CUBE))
      .onFalse(new AutoRotate(this.arm, -ElevatorConstants.ROTATION_MIDDLE_LEVEL_CUBE)); 
  }

  public Command getAutonomousCommand() {
    return autonomous.getAuto(autonomous.getChooser().getSelected(), drivetrain);
  }
}
