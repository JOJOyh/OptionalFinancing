package com.jojo.finace.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Package Name com.jojo.finace.retrofit
 * Project Name finance
 * Created by JOJO on 18/2/11 14:19
 * Class Name GitHub
 * Remarks:https://api.github.com/repos/JOJOyh/OptionalFinancing/contributors
 */

public interface GitHub {
	@GET("/repos/{owner}/{repo}/contributors")
	Call<List<Contributor>> contributors(
			@Path("owner") String owner,
			@Path("repo") String repo);
}