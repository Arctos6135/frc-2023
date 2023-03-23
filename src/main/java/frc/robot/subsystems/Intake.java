package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CANBus;

public class Intake extends SubsystemBase {
    
    private final TalonSRX intakeMotor;

    public Intake(int intakeMotor) {
        this.intakeMotor = new TalonSRX(CANBus.INTAKE_MOTOR);
    }

    public void setIntakeMotor(double speed) {
        this.intakeMotor.set(ControlMode.PercentOutput, speed);
    }
}
