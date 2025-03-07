package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase
{
    private final PWMSparkMax motor;
    private final XboxController controller;
    private final DigitalInput limitswitch;

    public Shooter()
    {
        motor = new PWMSparkMax(5); //subject to change
        motor.setSafetyEnabled(true);

        controller = new XboxController(0); //subject to change
        limitswitch = new DigitalInput(0); //subject to change
    }

    public void shoot(double speed)
    {
        if (controller.getRightBumperPressed()) motor.set(speed);   
    }

    public void preventEject()
    {
        if (limitswitch.get() && !controller.getRightBumperPressed()) motor.set(0);
    }

    public void stop()
    {
        motor.set(0);
    }  
}
