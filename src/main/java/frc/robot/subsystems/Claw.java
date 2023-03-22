package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ClawConstants;

public class Claw extends SubsystemBase {
    private CANSparkMax clawMotor = new CANSparkMax(ClawConstants.CLAW_MOTOR, MotorType.kBrushless);

    // Let's stop gettomg the motor ID from an argument...
    public Claw() { 

    }
    
    // It is important we call this 
    public void stop() {
        setMotors(0);
    }

    // Starts moving the motor to gather anything ahead of it
    public void gather() {
        setMotors(0.8);
    }

    // Moves the motor in inverse to release anything that it may have gathered
    public void release() {
        setMotors(-0.8);
    }

    public void setMotors(double speed) {
        this.clawMotor.set(speed);
    }
}