package teamcode.OpModes.TeleOp.Yos;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;

@Disabled
@TeleOp(name ="WHAT IN THE WORLD DO WE NAME THIS STINKIN ROBOT!!!!!!!!!!!!!", group = "TeleOp")
public class SingleDriverTeleop extends OpMode {
    //Initializing the main objects:
    Intake intake;
    DriveTrain driveTrain;

    @Override
    public void init(){
        //Hardware mapping the servos:
        intake = Intake.initGrabber(hardwareMap);
        driveTrain = DriveTrain.initDriveTrain(hardwareMap, DcMotor.ZeroPowerBehavior.FLOAT);

        driveTrain.resetEncoders();
        driveTrain.setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override public void loop(){
        //Drive Train manual control system
        driveTrain.manualDrive(gamepad1);
        driveTrain.Strafin(gamepad1);
        driveTrain.checkToggleSpeed(gamepad1);
        DriveTrain.logTelemetry(telemetry, driveTrain);

        //Intake System (Servos)


        //DOUBLE FLYWHEEL ACTION (Launching with Motors)

        }
}