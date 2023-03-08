package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.autonomous.MobilityDockWide;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

public class Autonomous {
    
    private SendableChooser<AutoMode> chooser; 

    public enum AutoMode {
        NONE("None"),
        MOBILITY_DOCK_WIDE("Mobility Dock Wide");

        String autoName; 

        AutoMode(String autoName) {
            this.autoName = autoName; 
        }
    }

    public SendableChooser<AutoMode> getChooser() {
        return this.chooser; 
    }

    public Command getAuto(AutoMode mode, Drivetrain drivetrain, Elevator elevator, Arm arm, Claw claw) {
        switch(mode) {
            case NONE: 
                return null; 
            case MOBILITY_DOCK_WIDE:
                return MobilityDockWide.getCommand(drivetrain, elevator, arm, claw);
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
