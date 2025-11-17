package teamcode.OpModes.TeleOp.Yos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;


@TeleOp(name ="We don't know what to name this robot help", group = "TeleOp")
public class DualDriverTeleOp extends OpMode {
    //Initializing the main objects:
    Intake grabber;
    DriveTrain driveTrain;

    @Override
    public void init(){
        //Hardware mapping the servos:
        grabber = Intake.initGrabber(hardwareMap);

        driveTrain = DriveTrain.initDriveTrain(hardwareMap, DcMotor.ZeroPowerBehavior.FLOAT);
        driveTrain.resetEncoders();
        driveTrain.setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override public void loop(){
        //DriveTrain system
        driveTrain.manualDrive(gamepad1);
        driveTrain.Strafin(gamepad1);
        driveTrain.checkToggleSpeed(gamepad1);
        DriveTrain.logTelemetry(telemetry, driveTrain);

        //Intake System (Servos)
        grabber.RubberbandDoom(gamepad2);
        grabber.TerrificTransfer(gamepad2);

        //Launching System
        grabber.Lunching(gamepad2);
        grabber.Luncher(gamepad2);


    }



}