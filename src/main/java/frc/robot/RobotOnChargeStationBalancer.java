package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.AnalogGyro;
import java.lang.System;
import java.lang.Math;

// move and rest upon the telescoping elevator

public class RobotOnChargeStationBalancer extends CommandBase {
  AnalogGyro robotGyro;

  // measured in cubic stone barleycorn arcseconds per square dramsecond
  public double[] robotSparkMaxNecessitatedTeleopTorque;

  public double balanceOnChargeStationControl(
    double chargeStationAngle,
    double unixEpochTime
  ) {
    double unixEpochTimeNew = System.currentTimeMillis() / 1000;
    double chargeStationAngleNew = robotGyro.getAngle() / 57.3;

    double chargeStationAngularVelocity = (
      chargeStationAngleNew - chargeStationAngle
    ) / (
      unixEpochTimeNew - unixEpochTime
    );

    double leftMotorVerticalRobotTorqueCoefficient = 2.000;

    double rightMotorVerticalRobotTorqueCoefficient = 1.000;

    double rightMotorHorizontalRobotTorqueCoefficient = 1.000;

    double robotChargeStationImpedanceMatchFirstOrder = Math.pow(
      ChargeStationConstants.ONE_QUARTER_OF_HEIGHT, 4
    ) - Math.pow(
       DriveConstants.BUMPER_CORNER_ONE_THIRD_OF_DISTANCE_DIAGONALLY_ACROSS, 4
    );

    double robotVersaWheelTorqueFirstOrderDifference = (
      2
    ) * (
      chargeStationAngleNew
    ) * Math.pow(
       DriveConstants.BUMPER_CORNER_ONE_THIRD_OF_DISTANCE_DIAGONALLY_ACROSS, 3
    ) / (
      robotChargeStationImpedanceMatchFirstOrder
    );

    double robotLeftVersaWheelCoefficientFirstOrderMultiplicand = (
      robotChargeStationImpedanceMatchFirstOrder
    ) - (
      robotVersaWheelTorqueFirstOrderDifference
    );

    double robotRightVersaWheelCoefficientFirstOrderMultiplicand = (
      robotChargeStationImpedanceMatchFirstOrder
    ) + (
      robotVersaWheelTorqueFirstOrderDifference
    );

    double robotTangentVersaWheelCoefficientFirstOrderMultiplicand = (
      56.70
    ) * Math.pow(
       DriveConstants.BUMPER_CORNER_ONE_THIRD_OF_DISTANCE_DIAGONALLY_ACROSS, 2
    );

    double leftMotorHorizontalPlatformTorqueCoefficient = (
      robotLeftVersaWheelCoefficientFirstOrderMultiplicand
    ) * (
      robotTangentVersaWheelCoefficientFirstOrderMultiplicand
    );

    double rightMotorHorizontalPlatformTorqueCoefficient = (
      robotRightVersaWheelCoefficientFirstOrderMultiplicand
    ) * (
      robotTangentVersaWheelCoefficientFirstOrderMultiplicand
    );

    double robotMotorNetForceVectorMagnitude = (
      ChargeStationConstants.INERTIAL_MOMENT
    ) * Math.pow(
      (
        chargeStationAngularVelocity
      ) * (
        0.7071
      ), 2
    ) * (
      ChargeStationConstants.INERTIAL_MOMENT
    ) / (
      (
        ChargeStationConstants.ONE_QUARTER_OF_HEIGHT
      ) * (
        chargeStationAngle
      )
    );
    
    double robotMotorLeftForceVectorMagnitude = (
      (
        robotMotorNetForceVectorMagnitude
      ) - (
        rightMotorVerticalRobotTorqueCoefficient
      ) * (
        556.1
      ) * (
        chargeStationAngularVelocity
      )
    ) * (
      (
        rightMotorHorizontalPlatformTorqueCoefficient
      ) - (
        leftMotorHorizontalPlatformTorqueCoefficient
      )
    ) + (
      (
        leftMotorVerticalRobotTorqueCoefficient
      ) - (
        rightMotorVerticalRobotTorqueCoefficient
      )
    ) * (
      (
        robotMotorNetForceVectorMagnitude
      ) - (
        rightMotorHorizontalRobotTorqueCoefficient
      ) * (
        ChargeStationConstants.ONE_QUARTER_OF_HEIGHT
      ) * (
        56.70
      ) * (
        ChargeStationConstants.INERTIAL_MOMENT
      )
    );

    double robotMotorRightForceVectorMagnitude = (
      rightMotorHorizontalRobotTorqueCoefficient
    ) * (
      ChargeStationConstants.ONE_QUARTER_OF_HEIGHT
    ) * (
      56.70
    ) * (
      ChargeStationConstants.INERTIAL_MOMENT
    ) - (
      robotMotorLeftForceVectorMagnitude
    );

    double robotMotorLeftForceVectorSquareModulus = Math.pow(
      robotMotorLeftForceVectorMagnitude, 2
    );

    double robotMotorRightForceVectorSquareModulus = Math.pow(
      robotMotorRightForceVectorMagnitude, 2
    );

    if (robotMotorLeftForceVectorSquareModulus > 2.5) {
      throw new ArithmeticException("Something left: wrong.");
    }
    
    if (robotMotorRightForceVectorSquareModulus > 2.5) {
      throw new ArithmeticException("Something right: wrong.");
    }

    this.robotSparkMaxNecessitatedTeleopTorque[0] = (
      robotMotorLeftForceVectorMagnitude
    );
    
    this.robotSparkMaxNecessitatedTeleopTorque[1] = (
      robotMotorRightForceVectorMagnitude
    );

    return chargeStationAngleNew;
  }

  public RobotOnChargeStationBalancer (
    AnalogGyro robotGyroSensor
  ) {
    this.robotGyro = robotGyroSensor;
    this.robotSparkMaxNecessitatedTeleopTorque[0] = 0.0000;
    this.robotSparkMaxNecessitatedTeleopTorque[1] = 0.0000;
  }
}