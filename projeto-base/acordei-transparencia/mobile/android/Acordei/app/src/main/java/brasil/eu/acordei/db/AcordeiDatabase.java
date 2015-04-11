package brasil.eu.acordei.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import brasil.eu.acordei.R;
import brasil.eu.acordei.util.SharedPreferencesUtil;

public class AcordeiDatabase extends SQLiteAssetHelper {

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eleicoes.db";
    //27
    private static AcordeiDatabase instance;
    private static List<Candidato> candidatos;
    private Context context;
    private SQLiteDatabase db;
    private AcordeiDatabase(Context context) {
        super(context, DATABASE_NAME, null, SharedPreferencesUtil.getDatabaseVersion(context));
        this.context = context;
        this.db = getReadableDatabase();
    }

    private AcordeiDatabase(Context context, boolean upgrade) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
        this.context = context;
        this.db = getReadableDatabase();
    }

    public static AcordeiDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new AcordeiDatabase(context);
        }
        return instance;
    }


    public synchronized void forceClose() {
        super.close();
    }

    @Override
    public synchronized void close() {
        //super.close();

    }
    public synchronized Cursor getAllDeputadosByCargo(String cargo) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = createCandidateBaseQuery();
        String sqlTables = "politicos";
        qb.setTables(sqlTables);

        return qb.query(db, sqlSelect, "cargo = ?", new String[]{cargo},null, null, "nome_urna ASC");
    }

    private String[] createCandidateBaseQuery() {
        return new String[]{
                "_id",
                "nome",
                "nome_urna",
                "numero",
                "foto",
                "resultado",
                "partido",
                "data_nascimento",
                "instrucao",
                "ocupacao",
                "sites",
                "cargo",
                "estado_eleicao"};
    }


}
