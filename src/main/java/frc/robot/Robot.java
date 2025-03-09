// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.controls.BindingControls;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;


public class Robot extends TimedRobot {

  private Command auto;
  private BindingControls binding;

  public BindingControls controls;

  //Subsystems
  public Drivetrain drivetrain;
  public Elevator elevator;
  public Shooter shooter;

  public Robot(){
    drivetrain = new Drivetrain();
    elevator = new Elevator();
    shooter = new Shooter();

    initialize();
  }

  private void initialize(){
    drivetrain.drive(0, 0, 0, false);
    elevator.stop();
    shooter.stop();
  }

  @Override
  public void robotInit() {

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

  }

  @Override
  public void disabledInit(){

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
