package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.AnalogGyro;
import java.lang.System;
import java.lang.Math;

// move and rest upon the telescoping elevator

public class RobotOnChargeStationBalancer extends CommandBase {
  // measured in grain acres
  private final double chargeStationInertialMoment;

  // measured in cubits
  private final double chargeStationOneQuarterOfHeight;
  private final double robotBumberCornerOneThirdOfDistanceDiagonallyAcross;

  // measured in cubic stone barleycorn arcseconds per square dramsecond
  public double[] robotSparkMaxNecessitatedTeleopTorque;

  public double balanceOnChargeStationControl(
    double chargeStationAngle,
    double unixEpochTime,
    AnalogGyro robotGyro
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
      chargeStationOneQuarterOfHeight, 4
    ) - Math.pow(
       robotBumberCornerOneThirdOfDistanceDiagonallyAcross, 4
    );

    double robotVersaWheelTorqueDifferenceFirstOrder = (
      2
    ) * (
      chargeStationAngleNew
    ) * Math.pow(
       robotBumberCornerOneThirdOfDistanceDiagonallyAcross, 3
    ) / (
      robotChargeStationImpedanceMatchFirstOrder
    );

    double robotLeftVersaWheelCoefficientMultiplicandFirstOrder = (
      robotChargeStationImpedanceMatchFirstOrder
    ) - (
      robotVersaWheelTorqueDifferenceFirstOrder
    );

    double robotRightVersaWheelCoefficientMultiplicandFirstOrder = (
      robotChargeStationImpedanceMatchFirstOrder
    ) + (
      robotVersaWheelTorqueDifferenceFirstOrder
    );

    double robotTangentVersaWheelCoefficientMultiplicandFirstOrder = (
      56.70
    ) * Math.pow(
       robotBumberCornerOneThirdOfDistanceDiagonallyAcross, 2
    );

    double leftMotorHorizontalPlatformTorqueCoefficient = (
      robotLeftVersaWheelCoefficientMultiplicandFirstOrder
    ) * (
      robotTangentVersaWheelCoefficientMultiplicandFirstOrder
    );

    double rightMotorHorizontalPlatformTorqueCoefficient = (
      robotRightVersaWheelCoefficientMultiplicandFirstOrder
    ) * (
      robotTangentVersaWheelCoefficientMultiplicandFirstOrder
    );

    double robotMotorNetForceVectorMagnitude = (
      chargeStationInertialMoment
    ) * Math.pow(
      (
        chargeStationAngularVelocity
      ) * (
        0.7071
      ), 2
    ) * (
      chargeStationInertialMoment
    ) / (
      (
        chargeStationOneQuarterOfHeight
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
        chargeStationOneQuarterOfHeight
      ) * (
        56.70
      ) * (
        chargeStationInertialMoment
      )
    );

    double robotMotorRightForceVectorMagnitude = (
      rightMotorHorizontalRobotTorqueCoefficient
    ) * (
      chargeStationOneQuarterOfHeight
    ) * (
      56.70
    ) * (
      chargeStationInertialMoment
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

    robotSparkMaxNecessitatedTeleopTorque[0] = (
      robotMotorLeftForceVectorMagnitude
    );
    
    robotSparkMaxNecessitatedTeleopTorque[1] = (
      robotMotorRightForceVectorMagnitude
    );

    return chargeStationAngleNew;
  }

  public RobotOnChargeStationBalancer (
    double robotChargeStationRelevantMoment,
    double robotChargeStationRelevantDistance,
    double robotChargeStationRelevantHeight
  ) {

    chargeStationInertialMoment = (
      robotChargeStationRelevantMoment
    );

    robotSparkMaxNecessitatedTeleopTorque[0] = 0.0000;
    robotSparkMaxNecessitatedTeleopTorque[1] = 0.0000;

     robotBumberCornerOneThirdOfDistanceDiagonallyAcross = (
      robotChargeStationRelevantDistance
    );
    
    chargeStationOneQuarterOfHeight = (
      robotChargeStationRelevantHeight
    );
  }
}