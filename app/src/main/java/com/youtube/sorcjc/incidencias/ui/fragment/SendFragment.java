package com.youtube.sorcjc.incidencias.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.youtube.sorcjc.incidencias.Global;
import com.youtube.sorcjc.incidencias.R;
import com.youtube.sorcjc.incidencias.io.IncidentApiAdapter;
import com.youtube.sorcjc.incidencias.io.response.CategoriesResponse;
import com.youtube.sorcjc.incidencias.io.response.ProjectsResponse;
import com.youtube.sorcjc.incidencias.io.response.SimpleResponse;
import com.youtube.sorcjc.incidencias.model.Category;
import com.youtube.sorcjc.incidencias.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendFragment extends Fragment implements Callback<ProjectsResponse>, View.OnClickListener {

    private EditText etTitle, etDescription;
    private Spinner spinnerProject, spinnerCategory, spinnerSeverity;
    private Button btnSend;

    // Fetched data
    private ArrayList<Project> projects;
    private ArrayList<Category> categories;

    public SendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);

        spinnerProject = (Spinner) view.findViewById(R.id.spinnerProject);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        spinnerSeverity = (Spinner) view.findViewById(R.id.spinnerSeverity);

        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etDescription = (EditText) view.findViewById(R.id.etDescription);

        btnSend = (Button) view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int projectId = getProjectIdByPosition(position);

                Call<CategoriesResponse> call = IncidentApiAdapter.getApiService().getCategories(projectId);
                call.enqueue(new Callback<CategoriesResponse>() {
                    @Override
                    public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                        if (response.isSuccessful()) {
                            CategoriesResponse categoriesResponse = response.body();
                            categories = categoriesResponse.getCategories();
                            loadSpinnerCategory();
                        } else {
                            Toast.makeText(getActivity(), "Error al cargar categorías", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchProjectsFromServer();
//        ArrayList<Project> projects = new ArrayList<>();
//        projects.add(new Project(7, "Proyecto 1"));
//        projects.add(new Project(17, "Proyecto 2"));
//        loadSpinnerProject(projects);
    }

    private void fetchProjectsFromServer() {
        Call<ProjectsResponse> call = IncidentApiAdapter.getApiService().getProjects();
        call.enqueue(this);
    }

    private void loadSpinnerProject() {
        List<String> list = new ArrayList<>();
        for (Project project : projects) {
            list.add(project.getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        // spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProject.setAdapter(spinnerArrayAdapter);
    }
    private void loadSpinnerCategory() {
        List<String> list = new ArrayList<>();
        for (Category category : categories) {
            list.add(category.getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        spinnerCategory.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onResponse(Call<ProjectsResponse> call, Response<ProjectsResponse> response) {
        if (response.isSuccessful()) {
            ProjectsResponse projectsResponse = response.body();
            projects = projectsResponse.getProjects();
            loadSpinnerProject();
        } else {
            Toast.makeText(getActivity(), "Error al cargar proyectos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ProjectsResponse> call, Throwable t) {
        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                postNewIncident();
                break;
        }
    }

    private final char[] severity_codes_array = new char[] {'M', 'N', 'A'};

    private void postNewIncident() {
        int iProject = Global.getSpinnerSelectedIndex(spinnerProject);
        int iCategory = Global.getSpinnerSelectedIndex(spinnerCategory);
        int iSeverity = Global.getSpinnerSelectedIndex(spinnerSeverity);

        final int category_id = getCategoryIdByPosition(iCategory);
        final int project_id = getProjectIdByPosition(iProject);

        final String title = etTitle.getText().toString();
        final String description = etDescription.getText().toString();
        final String severity = String.valueOf(severity_codes_array[iSeverity]);

        final int client_id = Global.getIntFromSharedPreferences(getActivity(), "id");

        Call<SimpleResponse> call = IncidentApiAdapter.getApiService().postNewIncident(title,
                description, severity, category_id, project_id, client_id);
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isError()) {
                        Toast.makeText(getActivity(), "Error inesperado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Incidencia registrada correctamente", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        etTitle.setText("");
        etDescription.setText("");
    }

    private int getProjectIdByPosition(int position) {
        return projects.get(position).getId();
    }
    private int getCategoryIdByPosition(int position) {
        return categories.get(position).getId();
    }
}
