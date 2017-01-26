package com.youtube.sorcjc.incidencias.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.youtube.sorcjc.incidencias.R;
import com.youtube.sorcjc.incidencias.io.IncidentApiAdapter;
import com.youtube.sorcjc.incidencias.io.response.IncidentsCountByProject;
import com.youtube.sorcjc.incidencias.model.IncidentsByProject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectsChartFragment extends Fragment implements Callback<IncidentsCountByProject> {

    private BarChart barChart;

    public ProjectsChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects_chart, container, false);

        barChart = (BarChart) view.findViewById(R.id.barChart);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Call<IncidentsCountByProject> call = IncidentApiAdapter.getApiService().getIncidentsCountByProject();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<IncidentsCountByProject> call, Response<IncidentsCountByProject> response) {
        if (response.isSuccessful()) {
            IncidentsCountByProject incidentsCountByProject = response.body();
            createBarChart(incidentsCountByProject);
        } else {
            Toast.makeText(getContext(), "Error al obtener la data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<IncidentsCountByProject> call, Throwable t) {
        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void createBarChart(IncidentsCountByProject incidentsCountByProject) {
        List<BarEntry> entries = new ArrayList<>();

        ArrayList<IncidentsByProject> incidentsByProjectArray = incidentsCountByProject.getIncidentsByProject();
        int i = 0;
        String[] labels = new String[incidentsByProjectArray.size()];
        for (IncidentsByProject incidentsByProject: incidentsByProjectArray) {
            entries.add(new BarEntry(i, incidentsByProject.getCount(), incidentsByProject.getName()));
            labels[i] = incidentsByProject.getName();
            ++i;
        }

        BarDataSet set = new BarDataSet(entries, "Proyectos con más incidencias");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width

        set.setColors(new int[] { R.color.colorRed, R.color.colorBlue, R.color.colorGreen }, getContext());

        Description description = new Description();
        description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        description.setText("Cantidad de incidencias");
        barChart.setDescription(description);


        barChart.getXAxis().setValueFormatter(new LabelFormatter(labels));

        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
    }

    public class LabelFormatter implements IAxisValueFormatter {
        private final String[] mLabels;


        LabelFormatter(String[] labels) {
            mLabels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            final int fixedValue = (int) value;

            if (Math.abs(fixedValue-value) <= 0.3) {
                return mLabels[fixedValue];
            }

            return "";
        }
    }

}
