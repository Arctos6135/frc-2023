// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.AnalogGyro;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private AnalogGyro robotGyroscopicSensorMain = new AnalogGyro(null);

  private RobotOnChargeStationBalancer RobotBalancingOnChargeStation = new RobotOnChargeStationBalancer(167.3, 2.614, 0.7506);

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    robotGyroscopicSensorMain.initGyro();
    PortForwarder.add(5800, "photonvision.local", 5800);
    CameraServer.startAutomaticCapture("Claw Camera", 0);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    m_robotContainer.updateDashboard();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void disabledExit() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void autonomousExit() {
    m_robotContainer.drivetrain.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void teleopExit() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void advanceChargeStationBalancing() {
    double chargeStationAngle = robotGyroscopicSensorMain.getAngle();

    double unixEpochTime = System.currentTimeMillis() / 1000;

    RobotBalancingOnChargeStation.balanceOnChargeStationControl(
      chargeStationAngle,
      unixEpochTime,
      this.robotGyroscopicSensorMain
    );
  }
  
  public void testExit() {
    
  }
}
