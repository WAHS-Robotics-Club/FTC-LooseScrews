package teamcode.Objects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import teamcode.Objects.Tool.Toggle;

public class Intake {
    public CRServo RubaRolla;
    public Servo Pusher;
    public CRServo Vlift;
    public DcMotor Lwheel;
    public DcMotor Rwheel;
    private Toggle toggleRolla;
    public Servo Stopper;
    private Toggle toggleTransfer;
    private Toggle toggleDoobleLuncher;

    public static Intake initGrabber(HardwareMap hardwareMap) {
        //Creates and hardware maps the grabber element
        Intake grabber = new Intake();
        grabber.RubaRolla = hardwareMap.crservo.get("RubaRolla");
        grabber.Pusher = hardwareMap.servo.get("Pusher");
        grabber.Vlift = hardwareMap.crservo.get("Vlift");
        grabber.Stopper = hardwareMap.servo.get("Stopper");

        grabber.Lwheel = hardwareMap.dcMotor.get("TLflywheel");
        grabber.Lwheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabber.Lwheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        grabber.Rwheel = hardwareMap.dcMotor.get("TRflywheel");
        grabber.Rwheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabber.Rwheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        grabber.toggleRolla = new Toggle();
        grabber.toggleTransfer = new Toggle();
        grabber.toggleDoobleLuncher = new Toggle();


        return grabber;
    }

    //Main methods go BELOW HERE!!!!!


    public void RubberbandDoom (Gamepad gamepad) {
        if (gamepad.a) {
            toggleRolla.toggle();
        }

        if (toggleRolla.isToggled()) {
            RubaRolla.setPower(1.0);
        }
    }

    public void TerrificTransfer (Gamepad gamepad){
        if (gamepad.b) {
            toggleTransfer.toggle();
        }

        if (toggleTransfer.isToggled()) {
            Stopper.setPosition(0.5);
            Pusher.setPosition(0.6);

        } else {
            Stopper.setPosition(0);
            Pusher.setPosition(0);
        }
    }

    public void Lunching (Gamepad gamepad) {
        if (gamepad.right_stick_y >= 1.0){
            Vlift.setPower(gamepad.left_stick_y);
        } else if (gamepad.right_stick_y <= 1.0) {
            Vlift.setPower(gamepad.left_stick_y);
        }
    }

    public void Luncher (Gamepad gamepad) {
        if(gamepad.right_bumper){
            toggleDoobleLuncher.toggle();
        }

        if (toggleDoobleLuncher.isToggled()){
            Lwheel.setPower(1.0);
            Rwheel.setPower(1.0);
        }
    }



}

























