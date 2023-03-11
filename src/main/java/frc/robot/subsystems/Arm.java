package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ElevatorConstants;

//code for rotating arm controlled by redline motor, chain, and sprockets


public class Arm extends SubsystemBase {
    
    //This is our motor
    private final TalonSRX armMotor;
    private final DutyCycleEncoder hexEncoder;

    public static double kP = 0.01;
    public static double kI = 0;
    public static double kD = 0; 

    private PIDController rotationController;
    
    /**
     * This is our constructor
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(int armMotor, int hexEncoderPort) {
        this.armMotor = new TalonSRX(armMotor);
        this.armMotor.setNeutralMode(NeutralMode.Brake);
        this.hexEncoder = new DutyCycleEncoder(hexEncoderPort);
        
        this.rotationController = new PIDController(kP, kI, kD); 
        
        this.hexEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS);
    }
 
    @Override
    public void periodic() {
        /* double p = kPW.getEntry().getDouble(0);
        double i = kIW.getEntry().getDouble(0);
        double d = kDW.getEntry().getDouble(0);
        rotationController.setP(p);
        rotationController.setI(i);
        rotationController.setD(d);

        double speed = MathUtil.clamp(rotationController.calculate(hexEncoder.getDistance()), -1, 1);

        setMotor(speed * 0.1);

        System.out.printf("p: %f, i: %f, d: %f\n", p, i, d);
        System.out.printf("p: %f, i: %f, d: %f\n", p, i, d); */ 
    }

    // Sets speed of motor
    public void setMotor(double armSpeed) {
        this.armMotor.set(ControlMode.PercentOutput, armSpeed);
    }

    /**
     * Sets the arm at an angle using PID Controller.
     * 
     * @param angle in radians, where positive values represent the arm moving up.
     */
    public void setAngle(double angle) {
       
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
