package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.ElevatorConstants;

//code for rotating arm controlled by redline motor, chain, and sprockets


public class Arm extends SubsystemBase {
    
    //This is our motor
    private final TalonSRX armMotor;
    private final DutyCycleEncoder hexEncoder;
    private final double initialAngle;

    /**
     * This is our constructor
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(int armMotor, int hexEncoderPort, ShuffleboardTab armTab) {
        this.armMotor = new TalonSRX(armMotor);
        this.armMotor.setNeutralMode(NeutralMode.Brake);
        this.hexEncoder = new DutyCycleEncoder(hexEncoderPort);
        
        this.hexEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS);

        this.initialAngle = Math.PI / 2;
    }

    // Sets speed of motor
    public void setMotor(double speed) {
        this.armMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getAngle() {
        return initialAngle + hexEncoder.getDistance();
    }

    public DutyCycleEncoder getEncoder() {
        return this.hexEncoder;
    }
}