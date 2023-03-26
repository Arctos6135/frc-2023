package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CANBus;

public class WheelClaw extends SubsystemBase {
    private final CANSparkMax motor = new CANSparkMax(CANBus.CLAW_MOTOR, MotorType.kBrushless);

    public void setMotorSpeed(double speed) {
        this.motor.set(speed);
    }

    public void outtake() {
        setMotorSpeed(0.5);
    }
    
    public void intake() {
        setMotorSpeed(-0.5);
    }
}
