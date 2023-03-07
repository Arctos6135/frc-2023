package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ElevatorConstants;

//code for rotating arm controlled by redline motor, chain, and sprockets


public class Arm extends SubsystemBase {
    
    //This is our motor
    private final TalonSRX armMotor;
    private final DutyCycleEncoder hexEncoder;

    private SimpleWidget kPW;
    private SimpleWidget kIW;
    private SimpleWidget kDW; 

    private PIDController rotationController;
    
    /**
     * This is our constructor
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(int armMotor, int hexEncoderPort, SimpleWidget kPW, SimpleWidget kIW, SimpleWidget kDW) {
        this.armMotor = new TalonSRX(armMotor);
        this.armMotor.setNeutralMode(NeutralMode.Brake);
        this.hexEncoder = new DutyCycleEncoder(hexEncoderPort);
        this.kPW = kPW;
        this.kIW = kIW;
        this.kDW = kDW;
        this.rotationController = new PIDController(kPW.getEntry().getDouble(0), kIW.getEntry().getDouble(0), kDW.getEntry().getDouble(0)); 
        
        this.hexEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS);
    }

    @Override
    public void periodic() {
        double p = kPW.getEntry().getDouble(0);
        double i = kIW.getEntry().getDouble(0);
        double d = kDW.getEntry().getDouble(0);
        rotationController.setP(p);
        rotationController.setI(i);
        rotationController.setD(d);

        System.out.printf("p: %f, i: %f, d: %f\n", p, i, d);
    }

    // Sets speed of motor
    public void setMotor(double armSpeed) {
        this.armMotor.set(ControlMode.PercentOutput, armSpeed);
    }

    // This stops the motor :D
    public void stopMotor() {
        this.armMotor.set(ControlMode.PercentOutput, 0);
    }

    public PIDController getPIDController() {
        return this.rotationController; 
    }

    public DutyCycleEncoder getEncoder() {
        return this.hexEncoder; 
    }
}
