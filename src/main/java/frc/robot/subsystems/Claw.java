package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.constants.ClawConstants;

public class Claw extends SubsystemBase {
    private CANSparkMax clawMotor;

    // Let's stop gettomg the motor ID from an argument...
    public Claw() { 
        this.clawMotor = new CANSparkMax(ClawConstants.CLAW_MOTOR, MotorType.kBrushless);
    }
    
    // It is important we call this 
    public void stop() {
        setMotors(0);
    }

    // Starts moving the motor to gather anything ahead of it
    public void gather() {
        setMotors(1);
    }

    // Moves the motor in inverse to release anything that it may have gathered
    public void release() {
        setMotors(-1);
    }

    public void setMotors(double speed) {
        this.clawMotor.set(speed)
    }
}