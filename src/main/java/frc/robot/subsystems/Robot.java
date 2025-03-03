// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.kauailabs.navx.frc.AHRS;



/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
  private static final int FrontLeftChannel = 0;
  private static final int RearLeftChannel = 1;
  private static final int FrontRightChannel = 2;
  private static final int RearRightChannel = 3;

  private static final int JoystickChannel = 0;

  private MecanumDrive robotDrive;
  private Joystick stick;

  private AHRS gyro;

  @Override
  public void robotInit() {
    PWMSparkMax frontLeft = new PWMSparkMax(FrontLeftChannel);
    PWMSparkMax rearLeft = new PWMSparkMax(RearLeftChannel);
    PWMSparkMax frontRight = new PWMSparkMax(FrontRightChannel);
    PWMSparkMax rearRight = new PWMSparkMax(RearRightChannel);

    SendableRegistry.addChild(robotDrive, frontLeft);
    SendableRegistry.addChild(robotDrive, rearLeft);
    SendableRegistry.addChild(robotDrive, frontRight);
    SendableRegistry.addChild(robotDrive, rearRight);

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    frontRight.setInverted(true);
    rearRight.setInverted(true);

    robotDrive = new MecanumDrive(frontLeft::set, rearLeft::set, frontRight::set, rearRight::set);

    stick = new Joystick(JoystickChannel);
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick Y axis for forward movement, X axis for lateral
    // movement, and Z axis for rotation.
    robotDrive.driveCartesian(-stick.getY(), -stick.getX(), -stick.getZ());
  }
}
