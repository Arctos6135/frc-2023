package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//code for rotating arm controlled by redline motor, chain, and sprockets


public class Arm extends SubsystemBase {
    
    private final TalonSRX armMotor;

    public Arm(int armMotorId) {
        this.armMotor = new TalonSRX(armMotorId);
        this.armMotor.setNeutralMode(NeutralMode.Brake);
        this.stopMotor();
    }

    /**
     * @param armSpeed the power of the motor, in the range [-1, 1]
     */
    public void setMotor(double armSpeed) {
        this.armMotor.set(ControlMode.PercentOutput, armSpeed);
    }

    // This stops the motor :D
    public void stopMotor() {
        this.armMotor.set(ControlMode.PercentOutput, 0);
    }
}
