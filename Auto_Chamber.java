package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Auto_Chamber", group = "Mi bestie y yo", preselectTeleOp = "York_2024")
public class Auto_Chamber extends LinearOpMode {

    // Garra y Brazo
    private Servo Garra;
    private Servo Arm;

    // Elevador
    private DcMotor elevadorLeft;
    private DcMotor elevadorRight;

    // Obtener instancia de la clase "Robot"
    private Odometry robot = new Odometry(this);

    private void inicializar() {
        // Configuración de frenos en los motores del elevador
        elevadorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevadorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        // Reset de encoders en los motores del elevador
        elevadorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevadorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        Arm.setPosition(1);
        Garra.setPosition(0);
    }

    private void elevadorSubir() {
        int EncoderIzq = -1451;
        int EncoderDer = 1450;
        
        elevadorLeft.setTargetPosition(EncoderIzq);
        elevadorRight.setTargetPosition(EncoderDer);
        
        elevadorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevadorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        elevadorLeft.setPower(1);
        elevadorRight.setPower(1);
        
        while (opModeIsActive() && (elevadorRight.isBusy() || elevadorLeft.isBusy())) {}
    }

    private void elevadorBajar() {
        elevadorLeft.setTargetPosition(0);
        elevadorRight.setTargetPosition(0);
        
        elevadorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevadorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        elevadorLeft.setPower(1);
        elevadorRight.setPower(1);
        
        while (opModeIsActive() && (elevadorRight.isBusy() || elevadorLeft.isBusy())) {}
    }

    private void brazoSubir() {
        Arm.setPosition(1);
    }

    private void brazoMedio() {
        Arm.setPosition(0.82);
    }

    private void brazoBajar() {
        Arm.setPosition(0.63);
    }

    private void agarrar() {
        Garra.setPosition(0.15);
    }

    private void soltar() {
        Garra.setPosition(0.35);
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
        // Configuración del hardware
        elevadorRight = hardwareMap.get(DcMotor.class, "elevadorRight");
        elevadorLeft = hardwareMap.get(DcMotor.class, "elevadorLeft");
        elevadorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevadorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        Garra = hardwareMap.get(Servo.class, "Garra");
        Arm = hardwareMap.get(Servo.class, "Arm");
        
        robot.initialize(true);
        inicializar();

        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading();

        if (opModeIsActive()) {
            elevadorSubir();
            sleep(1000);
            robot.drive(28, 1, 0.1);
            elevadorBajar();
            sleep(1000);
            robot.drive(-28, 1, 0.1);
            robot.turnTo(-90, 1, 0.1);
            robot.drive(45, 1, 0.1);
        }
    }
}
