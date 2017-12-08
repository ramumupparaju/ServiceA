package com.incon.service.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.incon.service.ConnectApplication;


public class TCDbHelper extends SQLiteOpenHelper {

    private static final String TAG = TCDbHelper.class.getName();

    private static final int DATABASE_VERSION = 1;
    /*private static final String DATABASE_NAME = Environment.getExternalStorageDirectory().
    getPath() + "/Tueo.db";//TODO: remove at the time of build generation*/
    private static final String DATABASE_NAME = "Tueo.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " symptomSelectedDate";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String UNIQUE = " UNIQUE";
    private static final String FOREIGN_KEY = " FOREIGN KEY";
    private static final String REFERENCES = " REFERENCES ";
    private static final String COMMA_SEP = ",";
    private static final String AUTO_INCREMENT = " AUTOINCREMENT ";
    private static TCDbHelper tueoDbHelper;

    private TCDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static TCDbHelper getDbInstance() {
        if (tueoDbHelper == null) {
            tueoDbHelper = new TCDbHelper(ConnectApplication.getAppContext());
        }

        return tueoDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*dropAllTables(db);
        onCreate(db);*/
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }

    private void createTables(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.SymptomsData.TABLE_NAME
                + " ("
                + TCDataBaseContract.SymptomsData.ID
                + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.SymptomsData.NOTES
                + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.SymptomsData.CREATED_DATE
                + DATE_TYPE + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.SymptomsData.SYMPTOM_SEVERITY_ID
                + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.SymptomsData.PATIENT_ID
                + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.SymptomsData.SYMPTOMS_ARRAY
                + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.SymptomsData.TUEO_SCORE
                + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.SymptomsData.TUEO_SCORE_COLOR
                + TEXT_TYPE + COMMA_SEP

                + TCDataBaseContract.SymptomsData.SYNC_STATUS
                + INTEGER_TYPE + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.Symptom.TABLE_NAME + " ("
                + TCDataBaseContract.Symptom.ID
                + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.Symptom.LABEL + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.Symptom.ORDER + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.Symptom.NAME + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.Symptom.LANGUAGE + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.Symptom.CREATED_AT
                + TEXT_TYPE + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.Severities.TABLE_NAME + " ("
                + TCDataBaseContract.Severities.ID
                + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.Severities.LABEL
                + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.Severities.ORDER
                + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.Severities.NAME
                + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.Severities.LANGUAGE
                + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.Severities.CREATED_AT
                + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.Severities.COLOR
                + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.Severities.ALERT
                + TEXT_TYPE + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.
                TueoScoreSensorData.TABLE_NAME + " ("
                + TCDataBaseContract.TueoScoreSensorData.TIME
                + DATE_TYPE + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.TueoScoreSensorData.HR + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.TueoScoreSensorData.RR + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.TueoScoreSensorData.SCORE + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.TueoScoreSensorData.
                SENSOR_DATA_TIMEZONE + TEXT_TYPE + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.
                ContactNumbersData.TABLE_NAME + " ("
                + TCDataBaseContract.ContactNumbersData.ACCOUNT_ID
                + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.ContactNumbersData.CONTACT_ID
                + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.ContactNumbersData.NUMBER + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.ContactNumbersData.TYPE_ID + INTEGER_TYPE
                + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.ModuleLearning.TABLE_NAME
                + " ("
                + TCDataBaseContract.ModuleLearning.MODULE_ID + TEXT_TYPE
                + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.ModuleLearning.READ_DATE + INTEGER_TYPE + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.
                PatientData.TABLE_NAME + " ("
                + TCDataBaseContract.PatientData.PATIENT_ID
                + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.PatientData.FIRST_NAME + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.LAST_NAME + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.HEIGHT + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.WEIGHT + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.DOB + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.EXTERNAL_ID + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.OTHER_MEDS + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.KNOWN_TRIGGERS + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.ACTIVITIES + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.CONTROLLER_MEDS + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.RESCUE_MEDS + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.CREATED_AT + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.UPDATED_AT + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.DELETED_AT + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.GENDER_ID + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientData.DOCTOR_ID + TEXT_TYPE
                + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.
                PatientGender.TABLE_NAME + " ("
                + TCDataBaseContract.PatientGender.PATIENT_GENDER_ID
                + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.PatientGender.NAME + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientGender.CREATED_AT + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientGender.UPDATED_AT + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.PatientGender.DELETED_AT + TEXT_TYPE
                + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.
                DoctorData.TABLE_NAME + " ("
                + TCDataBaseContract.DoctorData.PHYSICIAN_ID
                + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.DoctorData.FIRST_NAME + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorData.LAST_NAME + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorData.EMAIL + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorData.PRACTICE + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorData.ADDRESS + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorData.CITY + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorData.POSTAL_CODE + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorData.CREATED_AT + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorData.UPDATED_AT + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorData.DELETED_AT + TEXT_TYPE
                + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.
                DoctorContactNumbersData.TABLE_NAME + " ("
                + TCDataBaseContract.DoctorContactNumbersData.ACCOUNT_ID
                + INTEGER_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorContactNumbersData.CONTACT_ID
                + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.DoctorContactNumbersData.NUMBER + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.DoctorContactNumbersData.TYPE_ID + INTEGER_TYPE
                + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TCDataBaseContract.
                SensorData.TABLE_NAME + " ("
                + TCDataBaseContract.SensorData.DEVICE_ID + INTEGER_TYPE
                + PRIMARY_KEY + COMMA_SEP
                + TCDataBaseContract.SensorData.SENSOR_USERNAME + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.SensorData.IDENTIFIER + TEXT_TYPE + COMMA_SEP
                + TCDataBaseContract.SensorData.MODE + TEXT_TYPE
                + " )");

    }

    private void dropAllTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.SymptomsData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.Symptom.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.Severities.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.TueoScoreSensorData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.ContactNumbersData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.ModuleLearning.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.PatientData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.PatientGender.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.DoctorData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "
                + TCDataBaseContract.DoctorContactNumbersData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TCDataBaseContract.SensorData.TABLE_NAME);
    }

    public void clearTableRows(String tableName) {
        SQLiteDatabase sqlDb = getWritableDatabase();
        sqlDb.delete(tableName, null, null);
    }

    public void clearAllTables() {
        clearTableRows(TCDataBaseContract.SymptomsData.TABLE_NAME);
        clearTableRows(TCDataBaseContract.Symptom.TABLE_NAME);
        clearTableRows(TCDataBaseContract.Severities.TABLE_NAME);
        clearTableRows(TCDataBaseContract.TueoScoreSensorData.TABLE_NAME);
        clearTableRows(TCDataBaseContract.ContactNumbersData.TABLE_NAME);
        clearTableRows(TCDataBaseContract.ModuleLearning.TABLE_NAME);
        clearTableRows(TCDataBaseContract.PatientData.TABLE_NAME);
        clearTableRows(TCDataBaseContract.PatientGender.TABLE_NAME);
        clearTableRows(TCDataBaseContract.DoctorData.TABLE_NAME);
        clearTableRows(TCDataBaseContract.DoctorContactNumbersData.TABLE_NAME);
        clearTableRows(TCDataBaseContract.SensorData.TABLE_NAME);
    }
}