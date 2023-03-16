package frc.robot.subsystems;

import frc.robot.constants.ElevatorConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevator extends SubsystemBase {
    private final TalonSRX elevatorMotor;
    private final DigitalInput limitSwitch = new DigitalInput(ElevatorConstants.ELEVATOR_LIMIT_SWITCH_PORT);
    private final SimpleWidget limitSwitchOutput;

    public Elevator(int elevatorMotorId) {
        this.elevatorMotor = new TalonSRX(elevatorMotorId);
        this.elevatorMotor.setNeutralMode(NeutralMode.Brake);
        limitSwitchOutput = Shuffleboard.getTab("Arm").add("Limit switch on arm", false);
    }

    @Override
    public void periodic() {
        limitSwitchOutput.getEntry().setBoolean(limitSwitch.get());
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