package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.constants.ClawConstants;

public class Claw extends SubsystemBase {
    private final WPI_VictorSPX clawMotor;
    private final DigitalInput limitSwitchOpen = new DigitalInput(ClawConstants.LIMIT_SWITCH_OPEN);
    private final DigitalInput limitSwitchClose = new DigitalInput(ClawConstants.LIMIT_SWITCH_CLOSE);
    private ClawState state = ClawState.Open; // if the claw is not open at the start of the match BAD THINGS HAPPEN

    public enum ClawState {
        Open("open"),
        Opening("opening"),
        Closed("closed"),
        Closing("closing");

        String name;

        ClawState(String name) {
            this.name = name; 
        }
    }

    /**
     * The claw MUST be open at the start of the match. If it isn't, things will break and I will be mad at you.
     */
    public Claw(int clawMotorId) {
        this.clawMotor = new WPI_VictorSPX(clawMotorId);
        this.clawMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setSpeed(double speed) {
        this.clawMotor.set(ControlMode.PercentOutput, speed);
    } 

    @Override
    public void periodic() {
        if (limitSwitchOpen.get()) {
            this.state = ClawState.Open;
            this.clawMotor.set(ControlMode.PercentOutput, 0);
        }
        if (limitSwitchClose.get()) {
            this.state = ClawState.Closed;
            this.clawMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public ClawState getState() {
        return state;
    }

    public void startClosing() {
        if (state == ClawState.Open) {
            clawMotor.set(ControlMode.PercentOutput, ClawConstants.CLOSE_PERCENT_OUTPUT);
            state = ClawState.Closing;
        } else if (state == ClawState.Opening || state == ClawState.Closed) {
            DriverStation.reportWarning("Claw trying to close while " + state, false);
        }
    }

    public void stopClosing() {
        if (state == ClawState.Closing) {
            state = ClawState.Closed;
            clawMotor.set(ControlMode.PercentOutput, 0);
        } else {
            DriverStation.reportWarning("Claw cannot stop closing because it is " + state, false);
        }
    }

    public void startOpening() {
        if (state == ClawState.Closed) {
            clawMotor.set(ControlMode.PercentOutput, ClawConstants.OPEN_PERCENT_OUTPUT);
        } else if (state == ClawState.Closing || state == ClawState.Open) {
            DriverStation.reportWarning("Claw trying to open while " + state, false);
        }
    }

    public void stopOpening() {
        if (state == ClawState.Opening) {
            state = ClawState.Open;
            clawMotor.set(ControlMode.PercentOutput, 0);
        } else {
            DriverStation.reportWarning("Claw cannot stop opening because it is " + state, false);
        }
    }
}
