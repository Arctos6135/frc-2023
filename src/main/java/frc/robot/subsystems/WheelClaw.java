package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WheelClaw extends SubsystemBase {
    private final VictorSPX intakeMotor; 

    public WheelClaw(int intakeMotor) {
        this.intakeMotor = new VictorSPX(intakeMotor); 
    }

    public void setMotorSpeed(double speed) {
        this.intakeMotor.set(ControlMode.PercentOutput, speed);
    }
}
