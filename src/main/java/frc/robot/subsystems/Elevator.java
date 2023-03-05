package frc.robot.subsystems;

import frc.robot.constants.ElevatorConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevator extends SubsystemBase {
    private final TalonSRX elevatorMotor;

    public Elevator(int elevatorMotorId) {
        this.elevatorMotor = new TalonSRX(elevatorMotorId);
        this.elevatorMotor.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * @param armSpeed the power of the motor, in the range [-1, 1]
     */
    public void setMotor(double elevatorSpeed) {
        this.elevatorMotor.set(ControlMode.PercentOutput, elevatorSpeed * ElevatorConstants.SPEED_FACTOR);
    }

    public void stopMotor() {
        this.elevatorMotor.set(ControlMode.PercentOutput, 0);
    }
}