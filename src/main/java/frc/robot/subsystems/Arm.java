package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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
    private final TalonSRX topMotor = new TalonSRX(ArmConstants.LEFT_MOTOR_PORT);
    private final TalonSRX bottomMotor = new TalonSRX(ArmConstants.RIGHT_MOTOR_PORT);
    private final DutyCycleEncoder hexEncoder;

    public static double kP = 0.00001;
    public static double kI = 0;
    public static double kD = 0;

    private PIDController rotationController;

    private final GenericEntry encoderOutputWidget;
    private final GenericEntry motorSpeedWidget;

    private final double initialAngle;

    private double speed;

    /**
     * This is our constructor
     * 
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(int topMotor, int bottomMotor, int hexEncoderPort, ShuffleboardTab armTab) {
        this.topMotor.setNeutralMode(NeutralMode.Brake);
        this.bottomMotor.setNeutralMode(NeutralMode.Brake);

        this.hexEncoder = null;//new DutyCycleEncoder(hexEncoderPort);

        this.rotationController = new PIDController(kP, kI, kD);

        //this.hexEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS);

        encoderOutputWidget = armTab.add("Arm encoder angle", 0).withWidget(BuiltInWidgets.kTextView)
                .withPosition(3, 1).withSize(1, 1).getEntry();
        motorSpeedWidget = armTab.add("Arm speed", 0).withWidget(BuiltInWidgets.kNumberBar)
                .withPosition(3, 2).withSize(1, 1).getEntry();
        initialAngle = 0;
    }

    @Override
    public void periodic() {
        //double pid = getPIDController().calculate(getAngle(), targetAngle);
        //setMotor(pid);
/*
        if (getAngle() > 1 || getAngle() < 0) {
            System.out.printf("soft limit reached on arm, reversing\n");
            setMotor(-0.5 * speed);
        }
         */
        encoderOutputWidget.setDouble(getAngle());
    }

    // Sets speed of motor
    public void setMotor(double armSpeed) {
        this.speed = armSpeed;
        this.topMotor.set(ControlMode.PercentOutput, armSpeed);
        this.bottomMotor.set(ControlMode.PercentOutput, armSpeed);
        this.motorSpeedWidget.setDouble(armSpeed);
    }

    public double getAngle() {
        return initialAngle + 0;//hexEncoder.getDistance();
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

    public PIDController getPIDController() {
        return rotationController;
    }
}
