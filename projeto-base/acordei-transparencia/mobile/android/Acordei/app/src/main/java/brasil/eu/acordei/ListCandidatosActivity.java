package brasil.eu.acordei;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.SimpleAdapter;

import com.androidquery.AQuery;
import com.squareup.picasso.Picasso;

import brasil.eu.acordei.adapter.SimpleAdapterBuilder;
import brasil.eu.acordei.db.AcordeiDatabase;
import brasil.eu.acordei.db.Candidato;
import brasil.eu.acordei.util.ServiceTask;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by deivison on 12/24/14.
 */
public class ListCandidatosActivity extends ActionBarActivity {

    private AQuery query;
    private SimpleAdapter dataAdapter;
    private Cursor cursor;
    private String cargo;
    private SimpleAdapterBuilder builder;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("cargo", cargo);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.cargo = savedInstanceState.getString("cargo");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cargo = getIntent().getStringExtra("cargo");
        supportRequestWindowFeature(Window.FEATURE_PROGRESS);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_list_candidatos);
        query = new AQuery(this);

        getSupportActionBar().setTitle(cargo);


        //loadCandidatos();
    }

    private void loadCandidatos() {
        setSupportProgressBarIndeterminateVisibility(true);
        setSupportProgressBarIndeterminate(true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ServiceTask.getInstance(new ServiceTask.OnServiceBackground<SimpleAdapterBuilder>() {
                    @Override
                    public SimpleAdapterBuilder execute(ServiceTask serviceTask) throws Exception {
                        Cursor c = AcordeiDatabase.getInstance(getApplicationContext()).getAllDeputadosByCargo(cargo);

                        SimpleAdapterBuilder builder = SimpleAdapterBuilder.getInstance();
                        while (c.moveToNext()) {
                            builder.append(Candidato.CandidatoRows.NOME_URNA, c.getString(c.getColumnIndex(Candidato.CandidatoRows.NOME_URNA)), R.id.candidate_rown_name);
                            builder.append(Candidato.CandidatoRows.PARTIDO, c.getString(c.getColumnIndex(Candidato.CandidatoRows.PARTIDO)), R.id.candidate_rown_partido);
                            builder.append(Candidato.CandidatoRows.FOTO, c.getString(c.getColumnIndex(Candidato.CandidatoRows.FOTO)), R.id.candidate_rown_picture);
                            builder.append(Candidato.CandidatoRows.ESTADO, c.getString(c.getColumnIndex(Candidato.CandidatoRows.ESTADO)), R.id.candidate_rown_estado);
                        }
                        c.close();
                        return builder;
                    }

                    @Override
                    public void onSuccess(SimpleAdapterBuilder result) {
                        builder = result;
                        createCandidatesListAdapter();
                        setSupportProgressBarIndeterminateVisibility(false);
                        setSupportProgressBarIndeterminate(false);
                    }

                    @Override
                    public void onError(Exception e) {
                        setSupportProgressBarIndeterminateVisibility(false);
                        setSupportProgressBarIndeterminate(false);
                    }
                }).execute();
            }
        }, 500);
    }

    private void createCandidatesListAdapter() {
        this.dataAdapter = builder.getSimpleAdapter(getBaseContext(), R.layout.candidate_row_layout);
        query.id(R.id.candidate_list).adapter(dataAdapter);
        this.dataAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view.getId() == R.id.candidate_rown_picture) {
                    Picasso.with(ListCandidatosActivity.this).load(""+data).placeholder(R.drawable.profile_place_holder).fit().centerCrop().into(((CircleImageView) view));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
