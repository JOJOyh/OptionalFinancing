package com.jojo.finace.retrofit2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Package Name com.jojo.finace.retrofit2
 * Project Name finance
 * Created by JOJO on 18/2/11 16:30
 * Class Name SinaFund
 * Remarks:
 */

public interface SinaFund {
	@GET("/list={fund}")
	Call<String> funds(
			@Path("fund") String fund);
}
