package brasil.eu.acordei.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import brasil.eu.acordei.R;

public class SharedPreferencesUtil {

    public static final String REGION_KEY = "region_key";
    public static final String CARGO_KEY = "cargo_key";
    public static final String IS_FIRST_TIME_PERFIL = "is_first_time_perfil";
    public static final String IS_FIRST_TIME = "first_time_key";
    public static final String DATABASE_VERION = "database_version_key";
    public static final String CANDIDATE_PREFIX = "candidate";
    public static final String BIO = "bio";
    public static final String PROCESSOS = "processos";
    public static final String CANDIDAT_TIME = "candidate_time";
    public static final String CANDIDATE_PROPOST = "candidate_propost";
    public static final String HISTORY_KEY = "history";

    public static final String IS_LOGGED = "logged";
    public static final String LOGGED_USER = "logged_user";


    public static void saveLogged(Context context, String user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(IS_LOGGED, Context.MODE_PRIVATE).edit();
        editor.putString(LOGGED_USER, user);
        editor.commit();
    }

    public static String getLoggedUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(IS_LOGGED, Context.MODE_PRIVATE);
        return prefs.getString(LOGGED_USER, null);
    }

    public static Boolean isLogged(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(IS_LOGGED, Context.MODE_PRIVATE);
        return (prefs.getString(LOGGED_USER, null) == null ? false : true);
    }

    public static void saveRegion(Context context, String region) {
        SharedPreferences.Editor editor = context.getSharedPreferences(REGION_KEY, Context.MODE_PRIVATE).edit();
        editor.putString(REGION_KEY, region);
        editor.commit();
    }

    public static String getRegion(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(REGION_KEY, Context.MODE_PRIVATE);
        return prefs.getString(REGION_KEY, null);
    }



    public static boolean shouldTryUpdateCandidate(Context context, String candidate) {
        Log.d("CandidateService", "search for candidate = > " + candidate);
        long time = System.currentTimeMillis();
        long preferencesTime = context.getSharedPreferences(CANDIDATE_PREFIX + candidate, Context.MODE_PRIVATE).getLong(CANDIDAT_TIME, time);
        if (preferencesTime == time) {
            return true;
        }
        long diff = preferencesTime - time;
        long seconds = diff / 1000; // seconds is milliseconds / 1000
        long minutes = seconds / 60;
        if (minutes > 30) return true;

        return false;
    }

    public static void updateCandidate(Context context, String candidate, String miniBio, String processos) {
        Log.d("CandidateService", "update for candidate = > " + candidate);
        SharedPreferences.Editor editor = context.getSharedPreferences(CANDIDATE_PREFIX + candidate, Context.MODE_PRIVATE).edit();
        editor.putLong(CANDIDAT_TIME, System.currentTimeMillis());
        editor.putString(BIO, miniBio);
        editor.putString(PROCESSOS, processos);
        editor.commit();
        Log.d("Candidate", "Candidate updated =>" + candidate);
    }

    public static void saveHistoryWall(Context context, String json) {
        SharedPreferences.Editor editor = context.getSharedPreferences(HISTORY_KEY, Context.MODE_PRIVATE).edit();
        editor.putString(HISTORY_KEY, json);
        editor.commit();
    }

    public static String getHistoryWall(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(HISTORY_KEY, Context.MODE_PRIVATE);
        return prefs.getString(HISTORY_KEY, null);
    }

    public static void saveCargo(Context context, String cargo) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CARGO_KEY, Context.MODE_PRIVATE).edit();
        editor.putString(CARGO_KEY, cargo);
        editor.commit();
    }

    public static String getCargo(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CARGO_KEY, Context.MODE_PRIVATE);
        return prefs.getString(CARGO_KEY, null);
    }

    public static void setDatabaseVerion(Context context, int version) {
        SharedPreferences.Editor editor = context.getSharedPreferences(IS_FIRST_TIME, Context.MODE_PRIVATE).edit();
        editor.putInt(DATABASE_VERION, version);
        editor.commit();
    }

    public static int getDatabaseVersion(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(IS_FIRST_TIME, Context.MODE_PRIVATE);
        //return prefs.getInt(DATABASE_VERION, AcordeiDatabase.DATABASE_VERSION);
        return 1;
    }

    public static boolean isFirstTime(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(IS_FIRST_TIME, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_FIRST_TIME, true);
    }

    public static void noMoreTips(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(IS_FIRST_TIME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(IS_FIRST_TIME, false);
        editor.commit();
    }

    public static boolean isFirstTimePerfil(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(IS_FIRST_TIME_PERFIL, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_FIRST_TIME_PERFIL, true);
    }

    public static void noMorePerfilTips(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(IS_FIRST_TIME_PERFIL, Context.MODE_PRIVATE).edit();
        editor.putBoolean(IS_FIRST_TIME_PERFIL, false);
        editor.commit();
    }

    public static void tempRemoveDatabase(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(IS_FIRST_TIME, Context.MODE_PRIVATE).edit();
        editor.remove(DATABASE_VERION);
        editor.commit();
    }

    public static void saveProposta(Context context, String candidate, String proposta) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CANDIDATE_PREFIX + candidate, Context.MODE_PRIVATE).edit();
        editor.putString(CANDIDATE_PROPOST, proposta);
        editor.commit();
    }

    public String getProposta(Context context, String candidate) {
        SharedPreferences prefs = context.getSharedPreferences(CANDIDATE_PREFIX + candidate, Context.MODE_PRIVATE);
        return prefs.getString(CANDIDATE_PROPOST, "");
    }
}
