package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import teamcode.Objects.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;

@Disabled
@Autonomous(name ="StrafinRobo")
public class AutoStrafeTesting extends LinearOpMode {

    DriveTrain driveTrain;
    Intake intake;

    @Override
    public void runOpMode() throws InterruptedException {


        driveTrain = DriveTrain.initDriveTrain(hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
        intake = Intake.initGrabber(hardwareMap);

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();

        //ONLY MODIFY STUFF AFTER THIS
        //Grabbing the block

        //Sets the height to a safe height

        driveTrain.StrafeRightBy(telemetry, 48);
        Thread.sleep(1000);
        driveTrain.StrafeLeftBy(telemetry,48);

        //LETZ GOOOOOOOOOOOOOO!!!!

        //STILL REQUIRES TESTING
        //I GOT THIS!

    }
}