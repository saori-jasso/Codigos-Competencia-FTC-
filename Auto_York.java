package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Auto_York", group = "Mi bestie y yo", preselectTeleOp = "York_2024")
public class Auto_York extends LinearOpMode {

    // Garra y Brazo
    private Servo Garra;
    private Servo Arm;

    // Elevador
    private DcMotor elevadorLeft;
    private DcMotor elevadorRight;

    private Odometry robot = new Odometry(this);

    private void inicializar() {
        // Brakes motores elevador
        elevadorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevadorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        // Reset motores elevador
        elevadorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevadorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        Arm.setPosition(1);
        Garra.setPosition(0);
    }

    private void elevadorSubir() {
        int EncoderIzq = -4157;
        int EncoderDer = 4173;

        elevadorLeft.setTargetPosition(EncoderIzq);
        elevadorRight.setTargetPosition(EncoderDer);

        elevadorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevadorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevadorLeft.setPower(1);
        elevadorRight.setPower(1);

        while (opModeIsActive() && (elevadorRight.isBusy() || elevadorLeft.isBusy())) {
        }
    }

    private void elevadorBajar() {
        int EncoderIzq = 0;
        int EncoderDer = 0;

        elevadorLeft.setTargetPosition(EncoderIzq);
        elevadorRight.setTargetPosition(EncoderDer);

        elevadorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevadorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevadorLeft.setPower(1);
        elevadorRight.setPower(1);

        while (opModeIsActive() && (elevadorRight.isBusy() || elevadorLeft.isBusy())) {
        }
    }

    private void brazoSubir() {
        Arm.setPosition(1);
    }

    private void brazoMedio() {
        Arm.setPosition(0.70);
    }

    private void brazoBajar() {
        Arm.setPosition(0.58);
    }

    private void agarrar() {
        Garra.setPosition(0);
    }

    private void soltar() {
        Garra.setPosition(0.45);
    }

    private void canasta() {
        elevadorSubir();
        sleep(2500);
        brazoMedio();
        sleep(250);
        soltar();
        sleep(350);
        brazoSubir();
        sleep(250);
        elevadorBajar();
    }

    @Override
    public void runOpMode() {
        // Elevador
        elevadorRight = hardwareMap.get(DcMotor.class, "elevadorRight");
        elevadorLeft = hardwareMap.get(DcMotor.class, "elevadorLeft");
        elevadorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevadorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Brazo y garra
        Garra = hardwareMap.get(Servo.class, "Garra");
        Arm = hardwareMap.get(Servo.class, "Arm");

        // Initialize the robot hardware & Turn on telemetry
        robot.initialize(true);
        inicializar();

        // Wait for driver to press start
        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading(); // Reset heading to set a baseline for Auto

        // Run Auto if stop was not pressed.
        if (opModeIsActive()) {
            robot.drive(33, 1, 0.1);
            canasta();
            robot.turnTo(-90, 1, 0.1);
            robot.drive(18, 1, 0.1);
            sleep(250);
            brazoBajar();
            sleep(400);
            agarrar();
            sleep(250);
            brazoSubir();
            sleep(250);
            robot.turnTo(45, 1, 0.1);
            robot.drive(15.5, 1, 0.1);
            canasta();
        }
    }
}
