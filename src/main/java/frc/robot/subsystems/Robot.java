// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.controls.BindingControls;

public class Robot extends TimedRobot {

  private Command auto;
  private BindingControls binding;

  private static final int FrontLeftChannel = 0;
  private static final int RearLeftChannel = 1;
  private static final int FrontRightChannel = 2;
  private static final int RearRightChannel = 3;

  private static final int JoystickChannel = 0;

  private ADXRS450_Gyro gyro;
  private MecanumDrive robotDrive;
  private Joystick stick;

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

    gyro = new ADXRS450_Gyro();

    stick = new Joystick(JoystickChannel);

    gyro.calibrate();

    binding =  new BindingControls();

  }

  @Override 
  public void teleopInit(){
    if(auto != null){
      auto.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    //Get joystick inputs X, Y and rotation
    double x = stick.getX(); //Lateral movement (left/right)
    double y = stick.getY(); //Forward/backward (foward/backwards)
    double rotation = stick.getTwist(); //Rotation (clockwise/couterclockwise)

    //Gyro angle for field orientation correction
    double gyroAngle = gyro.getAngle(); //Gyro will retuwn robot's angle in degrees

    //Convert joystick inputs to field-oriented control
    //Convert joystick input (x, y) relative to field using yro angle
    double fieldOrientedX = x*Math.cos(Math.toRadians(gyroAngle)) + y*Math.sin(Math.toRadians(gyroAngle));
    double fieldOrientedY = -x*Math.sin(Math.toRadians(gyroAngle)) + y*Math.cos(Math.toRadians(gyroAngle));

    //Drive the robot with field-oriented control
    robotDrive.driveCartesian(fieldOrientedX, fieldOrientedY, rotation);

    System.out.println("Gyro Angle: " + gyroAngle); //Logging and debugging

    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit(){
  //Reset gyro angle when disabled
    gyro.reset();

  }

  @Override 
  public void autonomousInit(){
    auto = binding.getAutonomousCommand();

    if(auto != null){
      auto.schedule();
    }
  }

  @Override
  public void  autonomousPeriodic(){
    CommandScheduler.getInstance().run();
  }

}
