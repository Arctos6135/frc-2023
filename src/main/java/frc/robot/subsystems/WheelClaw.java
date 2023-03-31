package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CANBus;

public class WheelClaw extends SubsystemBase {
    private final VictorSPX motor = new VictorSPX(CANBus.CLAW_MOTOR);

    public void setMotorSpeed(double speed) {
        this.motor.set(ControlMode.PercentOutput, speed);
    }

    public void outtake() {
        setMotorSpeed(0.6);
    }
    
    public void intake() {
        setMotorSpeed(-0.6);
    }
}
