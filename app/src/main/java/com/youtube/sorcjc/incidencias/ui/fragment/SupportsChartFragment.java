package com.youtube.sorcjc.incidencias.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.youtube.sorcjc.incidencias.R;
import com.youtube.sorcjc.incidencias.io.IncidentApiAdapter;
import com.youtube.sorcjc.incidencias.io.response.IncidentsCountBySupport;
import com.youtube.sorcjc.incidencias.model.IncidentsBySupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportsChartFragment extends Fragment implements Callback<IncidentsCountBySupport> {

    private LineChart lineChart;

    public SupportsChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_supports_chart, container, false);
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Call<IncidentsCountBySupport> call = IncidentApiAdapter.getApiService().getIncidentsCountBySupport();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<IncidentsCountBySupport> call, Response<IncidentsCountBySupport> response) {
        if (response.isSuccessful()) {
            IncidentsCountBySupport incidentsCountBySupport = response.body();
            createLineChart(incidentsCountBySupport);
        } else {
            Toast.makeText(getContext(), "Error al obtener la data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<IncidentsCountBySupport> call, Throwable t) {
        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void createLineChart(IncidentsCountBySupport incidentsCountBySupport) {
        List<Entry> valsComp1 = new ArrayList<>();

        ArrayList<IncidentsBySupport> countArray = incidentsCountBySupport.getIncidentsBySupport();
        int i = 0;
        for (IncidentsBySupport currentCount : countArray) {
            valsComp1.add(new Entry(i++, currentCount.getCount(), currentCount.getName()));
        }

//        Entry c1e1 = new Entry(0f, 100000f);
//        valsComp1.add(c1e1);
//        Entry c1e2 = new Entry(1f, 140000f);
//        valsComp1.add(c1e2);

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Incidencias resueltas");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        // dataSets.add(setComp2);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate(); // refresh
    }
}
