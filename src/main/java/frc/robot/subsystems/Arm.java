package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//code for rotating arm controlled by redline motor, chain, and sprockets


public class Arm extends SubsystemBase {
    
    //This is our motor
    private final TalonSRX armMotor;
    private final DutyCycleEncoder hexEncoder;

    private static double kP = 0;
    private static double kI = 0;
    private static double kD = 0; 

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
        this.stopMotor();
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
