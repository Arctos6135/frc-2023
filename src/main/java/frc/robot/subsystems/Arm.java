package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.ElevatorConstants;

//code for rotating arm controlled by redline motor, chain, and sprockets

public class Arm extends SubsystemBase {
    //This is our motor
    private final CANSparkMax motor = new CANSparkMax(ArmConstants.LEFT_MOTOR_PORT, MotorType.kBrushless);
    private final AbsoluteEncoder encoder;

    public static double kP = 0.00001;
    public static double kI = 0;
    public static double kD = 0;

    private PIDController rotationController;

    private final GenericEntry encoderOutputWidget;
    private final GenericEntry motorSpeedWidget;

    private final double initialAngle;

    /**
     * This is our constructor
     * 
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(ShuffleboardTab armTab) {
        this.motor.setIdleMode(IdleMode.kBrake);

        this.encoder = motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);
        encoder.setPositionConversionFactor(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS);

        this.rotationController = new PIDController(kP, kI, kD);

        encoderOutputWidget = armTab.add("Arm encoder angle", 0).withWidget(BuiltInWidgets.kTextView)
                .withPosition(3, 1).withSize(1, 1).getEntry();
        motorSpeedWidget = armTab.add("Arm speed", 0).withWidget(BuiltInWidgets.kNumberBar)
                .withPosition(3, 2).withSize(1, 1).getEntry();
        initialAngle = 0.5;
    }

    @Override
    public void periodic() {
        encoderOutputWidget.setDouble(getAngle());
    }

    // Sets speed of motor
    public void setMotor(double armSpeed) {
        this.motor.set(armSpeed);
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
