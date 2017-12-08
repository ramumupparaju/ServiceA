package com.incon.service.database;


public interface TCDataBaseContract {

    interface SymptomsData {
        String TABLE_NAME = "symptomsData";

        String ID = "symptoms_data_id";
        String NOTES = "notes";
        String CREATED_DATE = "symptom_created_date";
        String SYMPTOM_SEVERITY_ID = "symptom_severity_id";
        String PATIENT_ID = "patient_id";
        String SYMPTOMS_ARRAY = "symptoms";
        String TUEO_SCORE = "tueo_score";
        String TUEO_SCORE_COLOR = "tueo_score_color";

        String SYNC_STATUS = "symptoms_data_sync_status";
    }

    interface Symptom {
        String TABLE_NAME = "symptom";

        String ID = "symptom_id";
        String LABEL = "label";
        String ORDER = "symptom_order";
        String NAME = "name";
        String LANGUAGE = "language";
        String CREATED_AT = "api_created_date";
    }

    interface Severities {
        String TABLE_NAME = "severities";

        String ID = "severities_id";
        String LABEL = "severities_label";
        String ORDER = "severities_order";
        String NAME = "severities_name";
        String LANGUAGE = "severities_language";
        String CREATED_AT = "severities_api_created_date";
        String COLOR = "severities_color";
        String ALERT = "severities_alert";
    }

    interface TueoScoreSensorData {
        String TABLE_NAME = "tueo_score_sensor_data";

        String TIME = "tueo_score_sensor_data_time";
        String HR = "tueo_score_sensor_data_hr";
        String RR = "tueo_score_sensor_data_rr";
        String SCORE = "tueo_score_sensor_data_score";
        String SENSOR_DATA_TIMEZONE = "tueo_score_sensor_data_timezone";
    }

    interface ModuleLearning {
        String TABLE_NAME = "moduleLearningTable";

        String MODULE_ID = "moduleLearningModuleId";
        String READ_DATE = "readDate";
    }

    interface ContactNumbersData {
        String TABLE_NAME = "contact_numbers_data";

        String ACCOUNT_ID = "account_id";
        String CONTACT_ID = "contact_number_id";
        String NUMBER = "contact_number";
        String TYPE_ID = "contact_type_id";
    }

    interface PatientData {
        String TABLE_NAME = "patient_data";

        String PATIENT_ID = "id";
        String FIRST_NAME = "firstName";
        String LAST_NAME = "lastName";
        String HEIGHT = "height";
        String WEIGHT = "weight";
        String DOB = "dob";
        String EXTERNAL_ID = "externalId";
        String OTHER_MEDS = "otherMeds";
        String KNOWN_TRIGGERS = "knownTriggers";
        String ACTIVITIES = "activities";
        String CONTROLLER_MEDS = "controllerMeds";
        String RESCUE_MEDS = "rescueMeds";
        String CREATED_AT = "created_at";
        String UPDATED_AT = "udpated_at";
        String DELETED_AT = "deleted_at";
        String GENDER_ID = "gender_id";
        String DOCTOR_ID = "doctor_id";
    }

    interface PatientGender {
        String TABLE_NAME = "patient_gender_data";

        String PATIENT_GENDER_ID = "id";
        String NAME = "name";
        String CREATED_AT = "created_at";
        String UPDATED_AT = "udpated_at";
        String DELETED_AT = "deleted_at";
    }

    interface DoctorData {
        String TABLE_NAME = "physician_data";

        String PHYSICIAN_ID = "id";
        String FIRST_NAME = "firstName";
        String LAST_NAME = "lastName";
        String EMAIL = "email";
        String PRACTICE = "practice";
        String ADDRESS = "address";
        String CITY = "city";
        String STATE = "state";
        String POSTAL_CODE = "postalCode";
        String CREATED_AT = "created_at";
        String UPDATED_AT = "updated_at";
        String DELETED_AT = "deleted_at";
    }

    interface DoctorContactNumbersData {
        String TABLE_NAME = "doctor_contact_numbers_data";

        String ACCOUNT_ID = "account_id";
        String CONTACT_ID = "contact_id";
        String NUMBER = "contact_number";
        String TYPE_ID = "contact_type_id";
        String DOCTOR_ID = "doctor_id";
    }

    interface SensorData {
        String TABLE_NAME = "SensorData";
        String SENSOR_USERNAME = "sensor_username";
        String DEVICE_ID = "device_id";
        String IDENTIFIER = "identifier";
        String MODE = "mode";
    }
}
