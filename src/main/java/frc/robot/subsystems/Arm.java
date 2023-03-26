package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.CANBus;
import frc.robot.constants.ElevatorConstants;

//code for rotating arm controlled by redline motor, chain, and sprockets

public class Arm extends SubsystemBase {
    //This is our motor
    private final CANSparkMax motor = new CANSparkMax(CANBus.ARM_MOTOR, MotorType.kBrushless);
    private final RelativeEncoder encoder;

    public static double kP = 1;
    public static double kI = 0.1;
    public static double kD = 0;

    private PIDController rotationController;

    private final GenericEntry encoderOutputWidget;
    private final GenericEntry motorSpeedWidget;

    private Double initialAngle = null;

    private double speed;

    /**
     * This is our constructor
     * 
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(ShuffleboardTab armTab) {
        motor.restoreFactoryDefaults();
        encoder = motor.getEncoder();
        motor.setIdleMode(IdleMode.kBrake);
        this.rotationController = new PIDController(kP, kI, kD);
        rotationController.setIntegratorRange(-0.5, 0.5);
        encoder.setPositionConversionFactor(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS);
        //this.hexEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS);

        encoderOutputWidget = armTab.add("Arm encoder angle", 0).withWidget(BuiltInWidgets.kTextView)
                .withPosition(3, 1).withSize(1, 1).getEntry();
        motorSpeedWidget = armTab.add("Arm speed", 0).withWidget(BuiltInWidgets.kNumberBar)
                .withPosition(3, 2).withSize(1, 1).getEntry();
    }

    @Override
    public void periodic() {
        if (initialAngle == null && Timer.getFPGATimestamp() > 1) {
            initialAngle = encoder.getPosition();
        }
        encoderOutputWidget.setDouble(getAngle());
    }

    // Sets speed of motor
    public void setMotor(double armSpeed) {
        motor.set(armSpeed);
        this.motorSpeedWidget.setDouble(armSpeed);
    }

    public double getAngle() {
        return initialAngle + encoder.getPosition();
    }

    public boolean atSetpoint() {
        return rotationController.atSetpoint();
    }

    public PIDController getPIDController() {
        return rotationController;
    }
}
