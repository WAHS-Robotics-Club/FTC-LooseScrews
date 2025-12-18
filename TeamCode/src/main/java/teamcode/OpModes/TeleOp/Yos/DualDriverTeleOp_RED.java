package teamcode.OpModes.TeleOp.Yos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;
import teamcode.Objects.Tool.AprilTag;


@TeleOp(name ="FIRST CAME CRAB-BOT, THEN THE 2CHAINZ 2 222 CHAINZ, THEN HOWDY-BOT, AND NOW... DRUMROLL PLEASE! TttTtTttTttttTttTTttttt! INTRODUCING! THE ONE! THE ONLY! THE GROOVY! THE NEVER GLOOBY! ALLEN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! (T-OP for Red Team)", group = "TeleOp")
public class DualDriverTeleOp_RED extends OpMode {
    //Initializing the main objects:
    Intake intake;
    DriveTrain driveTrain;
    AprilTag aprilTag = new AprilTag();

    @Override
    public void init(){
        //Hardware mapping the servos:
        intake = Intake.initGrabber(hardwareMap);

        driveTrain = DriveTrain.initDriveTrain(hardwareMap, DcMotor.ZeroPowerBehavior.FLOAT, telemetry);
        driveTrain.resetEncoders();
        driveTrain.setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        aprilTag.initAprilTag(hardwareMap, telemetry);
    }

    @Override public void loop(){
        //DriveTrain system
        driveTrain.manualDrive(gamepad1);
        driveTrain.checkToggleSpeed(gamepad1);
        DriveTrain.logTelemetry(telemetry, driveTrain);

        //Intake & Transfer System (Servos)
        intake.rubberbandDoom(gamepad2);
        intake.terrificTransfer(gamepad2);
        intake.STOPIT(gamepad2);
        intake.logStopIt(telemetry);

        //Launching System
        intake.lunching(gamepad2);
        intake.luncher(gamepad2);

        //AprilTag Telemetry System??!?!??!??!?!?!!?!?!
        aprilTag.update();
        AprilTagDetection goalTag = aprilTag.getSpecificTag(24);
        aprilTag.LaunchGauger(goalTag); //Basically it constantly searches for the goal tag



    }



}