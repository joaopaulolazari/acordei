package brasil.eu.acordei.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import brasil.eu.acordei.R;
import brasil.eu.acordei.db.Candidato;

public class CandidateListAdapter extends ArrayAdapter<Candidato> {

    public CandidateListAdapter(Context context, List<Candidato> objects) {
        super(context, R.layout.candidate_row_layout, objects);
    }


}
