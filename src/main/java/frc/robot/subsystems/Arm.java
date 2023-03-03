package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//code for rotating arm controlled by redline motor, chain, and sprockets


public class Arm extends SubsystemBase{
    
    //This is our motor
    private final TalonSRX armMotor;

    /*
     * This is our constructor
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(int armMotor) {
        this.armMotor = new TalonSRX(armMotor);
        this.armMotor.setNeutralMode(NeutralMode.Brake);
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
}
