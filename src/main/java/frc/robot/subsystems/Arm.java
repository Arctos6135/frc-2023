package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ArmConstants;

//code for rotating arm controlled by redline motor, chain, and sprockets

public class Arm extends SubsystemBase {
    //This is our motor
    private final TalonSRX leftMotor = new TalonSRX(ArmConstants.LEFT_MOTOR_PORT);
    private final TalonSRX rightMotor = new TalonSRX(ArmConstants.RIGHT_MOTOR_PORT);
    private final DutyCycleEncoder hexEncoder;

    public static double kP = 0.00001;
    public static double kI = 0;
    public static double kD = 0;
    public static Constraints kConstraints = new Constraints(0, 0);
    
    public static double kS = 0;
    public static double kG = 0;
    public static double kV = 0;
    public static double kA = 0;

    private ProfiledPIDController rotationController;
    private ArmFeedforward rotationFeedforward;

    private final GenericEntry encoderOutputWidget;
    private final GenericEntry motorSpeedWidget;

    private double speed;

    /**
     * This is our constructor
     * 
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(int hexEncoderPort, ShuffleboardTab armTab) {
        this.leftMotor.setNeutralMode(NeutralMode.Brake);
        this.rightMotor.setNeutralMode(NeutralMode.Brake);

        this.hexEncoder = new DutyCycleEncoder(hexEncoderPort);

        this.rotationController = new ProfiledPIDController(kP, kI, kD, kConstraints);
        this.rotationFeedforward = new ArmFeedforward(kS, kG, kV, kA);

        this.hexEncoder.setDistancePerRotation(ArmConstants.DISTANCE_PER_ROTATION_RADIANS);

        encoderOutputWidget = armTab.add("Arm encoder angle", 0).withWidget(BuiltInWidgets.kTextView)
                .withPosition(3, 1).withSize(1, 1).getEntry();
        motorSpeedWidget = armTab.add("Arm speed", 0).withWidget(BuiltInWidgets.kNumberBar)
                .withPosition(3, 2).withSize(1, 1).getEntry();
        rotationController.setGoal(ArmConstants.STOW_ANGLE);
    }

    @Override
    public void periodic() {
        double pid = rotationController.calculate(getAngle());
        double feedforward = rotationFeedforward.calculate(rotationController.getSetpoint().position, rotationController.getSetpoint().velocity);
        setMotor(feedforward + pid);

        if (getAngle() > 1 || getAngle() < 0) {
            System.out.printf("soft limit reached on arm, reversing\n");
            setMotor(-0.5 * speed);
        }

        encoderOutputWidget.setDouble(getAngle());
    }

    // Sets speed of motor
    public void setMotor(double armSpeed) {
        this.speed = armSpeed;
        this.leftMotor.set(ControlMode.PercentOutput, armSpeed);
        this.rightMotor.set(ControlMode.PercentOutput, armSpeed);
        this.motorSpeedWidget.setDouble(armSpeed);
    }

    public void setAngle(double angle) {
        rotationController.reset(getAngle());
        rotationController.setGoal(angle);
    }

    public double getAngle() {
        return hexEncoder.getDistance() + ArmConstants.STOW_ANGLE;
    }

    public void resetEncoder() {
        hexEncoder.reset();
    }

    public DutyCycleEncoder getEncoder() {
        return this.hexEncoder;
    }

    public boolean atSetpoint() {
        return rotationController.atSetpoint();
    }

    public ProfiledPIDController getPIDController() {
        return rotationController;
    }
}
