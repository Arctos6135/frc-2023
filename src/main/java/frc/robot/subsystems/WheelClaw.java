package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WheelClaw extends SubsystemBase {
    private final VictorSPX leftIntakeMotor; 
    private final VictorSPX rightIntakeMotor; 

    public WheelClaw(int leftIntakeMotor, int rightIntakeMotor) {
        this.leftIntakeMotor = new VictorSPX(leftIntakeMotor); 
        this.rightIntakeMotor = new VictorSPX(rightIntakeMotor);

        this.leftIntakeMotor.setInverted(true);
    }

    public void setMotorSpeed(double speed) {
        this.leftIntakeMotor.set(ControlMode.PercentOutput, speed);
        this.rightIntakeMotor.set(ControlMode.PercentOutput, speed);
    }
}
