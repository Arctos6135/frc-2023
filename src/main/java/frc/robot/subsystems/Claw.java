package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CANBus;
import frc.robot.constants.ClawConstants;

public class Claw extends SubsystemBase {
    private VictorSPX clawMotor = new VictorSPX(CANBus.CLAW_MOTOR);

    public Claw() {
        clawMotor.setNeutralMode(NeutralMode.Brake);
    }
    
    // It is important we call this 
    public void stop() {
        setMotors(0);
    }

    // Starts moving the motor to gather anything ahead of it
    public void gather() {
        setMotors(0.4);
    }

    // Moves the motor in inverse to release anything that it may have gathered
    public void release() {
        setMotors(-0.4);
    }

    public void setMotors(double speed) {
        clawMotor.set(ControlMode.PercentOutput, speed);
    }
}