package com.youtube.sorcjc.incidencias.io;

import com.youtube.sorcjc.incidencias.io.response.CategoriesResponse;
import com.youtube.sorcjc.incidencias.io.response.IncidentsCountByProject;
import com.youtube.sorcjc.incidencias.io.response.IncidentsCountBySupport;
import com.youtube.sorcjc.incidencias.io.response.IncidentsStateResponse;
import com.youtube.sorcjc.incidencias.io.response.LoginResponse;
import com.youtube.sorcjc.incidencias.io.response.ProjectsResponse;
import com.youtube.sorcjc.incidencias.io.response.SimpleResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IncidentApiService {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> postLogin(@Field("email") String email, @Field("password") String password);

    // Get data for spinners

    @GET("projects")
    Call<ProjectsResponse> getProjects();

    @GET("project/categories")
    Call<CategoriesResponse> getCategories(@Query("project_id") int projectId);

    // New incident
    @POST("incidents")
    Call<SimpleResponse> postNewIncident(@Query("title") String title,
                                         @Query("description") String description,
                                         @Query("severity") String severity,
                                         @Query("category_id") int category_id,
                                         @Query("project_id") int project_id,
                                         @Query("client_id") int client_id);

    // Reports

    @GET("incidents/state")
    Call<IncidentsStateResponse> getIncidentsState();

    @GET("projects/incident")
    Call<IncidentsCountByProject> getIncidentsCountByProject();

    @GET("supports/incident")
    Call<IncidentsCountBySupport> getIncidentsCountBySupport();
}