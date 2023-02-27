package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    private CANSparkMax rightMaster;
    private CANSparkMax leftMaster;
    private CANSparkMax rightFollower;
    private CANSparkMax leftFollower;

    public Drivetrain(int rightMaster, int leftMaster, int rightFollower, int leftFollower) {
        this.rightMaster = new CANSparkMax(rightMaster, MotorType.kBrushless);
        this.leftMaster = new CANSparkMax(leftMaster, MotorType.kBrushless);
        this.rightFollower = new CANSparkMax(rightFollower, MotorType.kBrushless);
        this.leftFollower = new CANSparkMax(leftFollower, MotorType.kBrushless);

        this.rightFollower.follow(this.rightMaster);
        this.leftFollower.follow(this.leftMaster);

        this.rightMaster.setInverted(true);
    }

    public void arcadeDrive(double translation, double rotation, double scalingFactor) {
        double left = (translation + rotation) * scalingFactor;
        double right = (translation - rotation) * scalingFactor;

        setMotors(left, right);
    }

    private void setMotors(double left, double right) {
        this.leftMaster.set(left);
        this.rightMaster.set(right);
    }
}
