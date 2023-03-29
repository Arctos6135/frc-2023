package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.autonomous.MidCubeAuto;
import frc.robot.commands.autonomous.MidCubeBalanceAuto;
import frc.robot.commands.autonomous.MobilityAuto;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.WheelClaw;

public class Autonomous {
    
    private SendableChooser<AutoMode> chooser; 

    public enum AutoMode {
        NONE("None"),
        MOBILITY("Mobility"),
        MidCube("MidCube"),
        MidCubeBalance("MidCubeBalance");

        String autoName; 

        AutoMode(String autoName) {
            this.autoName = autoName; 
        }
    }

    public SendableChooser<AutoMode> getChooser() {
        return this.chooser; 
    }

    public Command getAuto(AutoMode mode, Drivetrain drivetrain, Elevator elevator, Arm arm, WheelClaw claw) {
        switch(mode) {
            case NONE:
                return new DriveForwardEncoded(drivetrain, 0, 0);
            case MOBILITY:
                return MobilityAuto.mobility(drivetrain);
            case MidCube:
                return MidCubeAuto.midCubeAuto(drivetrain, arm, elevator, claw);
            case MidCubeBalance:
                return MidCubeBalanceAuto.midCubeBalanceAuto(drivetrain, arm, elevator, claw);
            default: 
                return null; 
        }
    }

    public Autonomous() {
        chooser = new SendableChooser<>(); 

        for (AutoMode mode : AutoMode.class.getEnumConstants()) {
            chooser.addOption(mode.autoName, mode); 
        }

        chooser.setDefaultOption(AutoMode.NONE.autoName, AutoMode.NONE); 
    }
}
