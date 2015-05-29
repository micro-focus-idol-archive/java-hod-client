package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.QueryMap;

import java.util.Map;

public interface ResourcesService {

    String URL = "/2/api/sync/resource/v1";

    @GET(URL)
    Resources list(@QueryMap Map<String, Object> parameters);

    @GET(URL)
    Resources list(@Header("token") AuthenticationToken token, @QueryMap Map<String, Object> parameters);

}
