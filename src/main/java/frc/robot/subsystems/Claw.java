package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Claw {
    private boolean isOpen;
    private WPI_VictorSPX clawMotor;

    public Claw(int deviceNumber) {
        // need motor id (and name of motor would be helpful)
        this.clawMotor = new WPI_VictorSPX(deviceNumber);
        isOpen = false;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public WPI_VictorSPX getClawMotor() {
        return clawMotor;
    }

    public void close() {
        if (isOpen) {
            clawMotor.disable(); // Not sure what this does but it probably cuts voltage to motor.
            // Other options are clawMotor.set(0.0) or clawMotor.set(ControlMode.PercentOutput, 0.0)
            // The documentation is pretty unclear as to what each of these do, so testing should be done.

            isOpen = false;
        }
    }

    public void startOpening() {
        if (!isOpen) {
            clawMotor.set(ControlMode.PercentOutput, 0.5); // 0.5 is test value. Will (most likley) change after testing
            isOpen = true;
        }
    }

    public void stopOpening() {
        // Needs more code once able. Will interact with motor limiter part.(unfinished)
        clawMotor.stopMotor();
    }
}
