package www.seotoolzz.com.dts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Database.Models.Office;
import www.seotoolzz.com.dts.Helpers.SharedPref;
import www.seotoolzz.com.dts.Repositories.OfficeRepository;

public class SplashScreen extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    String[] office_codes = {
            "10001",
            "10002",
            "10003",
            "10004",
            "10005",
            "10006",
            "10007",
            "10008",
            "10009",
            "10010",
            "10011",
            "10012",
            "10013",
            "10014",
            "10015",
            "10016",
            "10017",
            "10018",
            "10019",
            "10020",
            "10021",
            "10022",
            "10023",
            "10024",
            "10025",
            "10026",
            "10027",
            "10028",
            "10029",
            "10030",
            "10031",
            "10032",
            "10033",
            "10034",
            "10035",
            "10036",
            "10037",
            "10038",
            "10039",
            "10040",
            "10041",
            "10042",
            "10043",
            "10044",
            "10045",
            "10046",
            "10050",
            "10055",
            "10056",
    };

    String[] office_names = {
            "PROVINCIAL GOVERNOR'S OFFICE",
            "PROVINCIAL VICE GOVERNOR'S OFFICE",
            "TANGGAPAN NG SANGGUNIANG PANLALAWIGAN - 1",
            "PROVINCIAL ADMINISTRATOR'S OFFICE",
            "PROVINCIAL ACCOUNTANT'S OFFICE",
            "PROVINCIAL TREASURER'S OFFICE",
            "PROVINCIAL ASSESSOR'S OFFICE",
            "PROVINCIAL PLANNING AND DEVELOPMENT OFFICE",
            "PROVINCIAL BUDGET OFFICE",
            "PROVINCIAL GENERAL SERVICES OFFICE - ADMIN",
            "PROVINCIAL LEGAL OFFICE",
            "PROVINCIAL PROSECUTOR'S OFFICE",
            "PROVINCIAL ENGINEER'S OFFICE - ADMIN DIVISION",
            "PROVINCIAL AGRICULTURIST'S OFFICE",
            "PROVINCIAL VETERINARY OFFICE",
            "PROVINCIAL FISHERIES AND AQUATIC RESOURCES OFFICE",
            "PROVINCIAL ENVIRONMENT AND NATURAL RESOURCES OFFICE",
            "PROVINCIAL SOCIAL WELFARE AND DEVELOPMENT OFFICE",
            "PROVINCIAL HEALTH OFFICE",
            "BISLIG DISTRICT HOSPITAL",
            "HINATUAN DISTRICT HOSPITAL",
            "LIANGA DISTRICT HOSPITAL",
            "MADRID DISTRICT HOSPITAL",
            "MARIHATAG DISTRICT HOSPITAL",
            "LINGIG MEDICARE COMMUNITY HOSPITAL",
            "CORTES MUNICIPAL HOSPITAL",
            "SAN MIGUEL COMMUNITY HOSPITAL",
            "PROVINCIAL TOURISM OFFICE",
            "PGO - NUTRITION DIVISION",
            "PGO - POPULATION MANAGEMENT DIVISION",
            "PGO - WARDEN TANDAG",
            "PGO - WARDEN LIANGA",
            "PGO - WARDEN BISLIG",
            "PGO - WARDEN CANTILAN",
            "PEO - CONSTRUCTION AND MAINTENANCE DIVISION 1",
            "PEO - MOTORPOOL DIVISION",
            "TSP - COTERMINOUS",
            "TSP - ELECTED",
            "PROVINCIAL SCHOOL BOARD",
            "PROVINCIAL GENERAL SERVICES OFFICE - SECURITY",
            "PROVINCIAL GENERAL SERVICES OFFICE - DETAILED",
            "PEO - CONSTRUCTION AND MAINTENANCE DIVISION 2",
            "DEPARTMENT OF EDUCATION",
            "ITU - INFORMATION TECHNOLOGY UNIT",
            "Provincial Vice Governor's Office - VM",
            "PROVINCIAL DISASTER RISK REDUCTION & MNGT. OFFICE",
            "PROVINCIAL HUMAN RESOURCE MANAGEMENT OFFICE",
            "TANGGAPAN NG SANGGUNIANG PANLALAWIGAN - 2",
            "TSP - Elected(ATM)",
    };

    String[] office_short_names = {
            "PGO",
            "PVGO",
            "TSP - 1",
            "PADMO",
            "PAO",
            "PTO",
            "PASSO",
            "PPDO",
            "PBO",
            "PGSO",
            "PLO",
            "PPO",
            "PEOADM",
            "PAGO",
            "PVO",
            "PFARO",
            "PENRO",
            "PSWDO",
            "PHO",
            "BDH",
            "HDH",
            "LDH",
            "MDH",
            "MARDH",
            "LMCH",
            "CMH",
            "SMCH",
            "PPTO",
            "PND",
            "POM",
            "PWT",
            "PWL",
            "PWB",
            "PWC",
            "PEOCMD",
            "PEOMPD",
            "TSP-CO",
            "TSP-EL",
            "PSB",
            "PGSO",
            "PGSO",
            "PEOCMD",
            "DEPED",
            "ITU",
            "PVGO-VM",
            "PDRRMO",
            "PHRMO",
            "TSP - 2",
            "TSP-EL",
    };

    @Override
    protected void onResume() {
        if(SharedPref.getSharedPreferenceBoolean(this, "SPLASH_SCREEN", false)) {
            SharedPref.setSharedPreferenceBoolean(this, "SPLASH_SCREEN", true);
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.askPermissionForCamera();

        if(SharedPref.getSharedPreferenceBoolean(this, "SPLASH_SCREEN", false)) {
            SharedPref.setSharedPreferenceBoolean(this, "SPLASH_SCREEN", true);
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        if(DB.getInstance(getApplicationContext()).officeDao().getOffices().size() == 0) {
            // Insert Offices.
            for(int i = 0; i<office_codes.length; i++) {
                Office office = OfficeRepository.make(office_codes[i], office_names[i], office_short_names[i]);
                DB.getInstance(getApplicationContext()).officeDao().create(office);
            }
        }


        setContentView(R.layout.activity_splash_screen);
    }

    private void askPermissionForCamera() {
        // Have access for camera to run the QR Code Scanner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        SharedPref.setSharedPreferenceBoolean(this, "SPLASH_SCREEN", true);
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        new AlertDialog.Builder(SplashScreen.this)
                .setTitle("Important Message")
                .setMessage("Document Tracking will not run properly if you not allow the requesting permission go to setting of your phone to manually allow the requesting permission")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
    }
}