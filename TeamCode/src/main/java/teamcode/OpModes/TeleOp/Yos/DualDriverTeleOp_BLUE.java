package teamcode.OpModes.TeleOp.Yos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;
import teamcode.Objects.Tool.AprilTag;


@TeleOp(name ="AFTER THE DEPRESSING CANCELLATION OF HOWDY, COMES RETRIBUTION IN ANOTHER. ALLEN!!!!!!!!!!! (named after the incredible and useful tool) MAKES HIS DEBUT. IN ORDER TO GAIN REVENGE FOR HIS PREDECESSOR, ALLEN SWEARS TO NEVER BUCKLE WHEN FACED WITH CHALLENGE. WHEN THE GOING GETS TOUGH, WILL ALLEN PREVAIL? LET'S FIND OUT... (T-op for Blue_Team)", group = "TeleOp")
public class DualDriverTeleOp_BLUE extends OpMode {
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
        AprilTagDetection goalTag = aprilTag.getSpecificTag(20);
        aprilTag.LaunchGauger(goalTag); //Basically it constantly searches for the goal tag



    }



}