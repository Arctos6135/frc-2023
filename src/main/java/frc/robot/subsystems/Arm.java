package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ElevatorConstants;

//code for rotating arm controlled by redline motor, chain, and sprockets


public class Arm extends SubsystemBase {
    
    //This is our motor
    private final TalonSRX armMotor;
    private final DutyCycleEncoder hexEncoder;
    private final double initialAngle;

    public static double kP = 0.00001;
    public static double kI = 0;
    public static double kD = 0; 

    private PIDController rotationController;

    private final GenericEntry angleEntry;
    private final GenericEntry percentHoldEntry;
    private final GenericEntry feedforwardEntry;
    private final GenericEntry feedbackEntry;
    private final GenericEntry controlEntry;
    
    /**
     * This is our constructor
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(int armMotor, int hexEncoderPort, ShuffleboardTab armTab) {
        this.armMotor = new TalonSRX(armMotor);
        this.armMotor.setNeutralMode(NeutralMode.Brake);
        this.hexEncoder = new DutyCycleEncoder(hexEncoderPort);
        
        this.rotationController = new PIDController(kP, kI, kD); 
        
        this.hexEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS);

        this.initialAngle = Math.PI / 2;

        armTab.add("Pid Controller", this.rotationController).withWidget(BuiltInWidgets.kPIDController);
        this.angleEntry = armTab.add("Angle", 0).getEntry();
        this.percentHoldEntry = armTab.add("Percent Hold", 0).getEntry();
        this.feedforwardEntry = armTab.add("Feedforward", 0).getEntry();
        this.feedbackEntry = armTab.add("Feedback", 0).getEntry();
        this.controlEntry = armTab.add("Control", 0).getEntry();
    }
 
    @Override
    public void periodic() {
        double angle = getAngle();
        double percentHold = Math.sin(initialAngle + hexEncoder.getDistance());
        double feedforward = percentHold * ElevatorConstants.HOLD_FACTOR;
        double feedback = rotationController.calculate(hexEncoder.getDistance());
        double control = feedforward + feedback;

        this.angleEntry.setDouble(angle);
        this.percentHoldEntry.setDouble(percentHold);
        this.feedforwardEntry.setDouble(feedforward);
        this.feedbackEntry.setDouble(feedback);
        this.controlEntry.setDouble(control);

        //setMotor(control);
    }

    // Sets speed of motor
    private void setMotor(double armSpeed) {
        this.armMotor.set(ControlMode.PercentOutput, armSpeed);
    }

    /**
     * Sets the arm at an angle using PID Controller.
     * 
     * @param angle in radians, where positive values represent the arm moving up.
     */
    public void setAngle(double angle) {
        this.rotationController.setSetpoint(angle);
    }

    public double getAngle() {
        return initialAngle + hexEncoder.getDistance();
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