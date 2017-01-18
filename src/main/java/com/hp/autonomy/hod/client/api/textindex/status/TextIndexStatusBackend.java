package com.hp.autonomy.hod.client.api.textindex.status;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

interface TextIndexStatusBackend {
    String URL = "/2/api/sync/textindex/{index_identifier}/status/v2";

    /**
     * Get index status information for the given text index.
     * @param token Token to use for authentication
     * @param indexIdentifier The name and domain for the text index
     * @return Index status information for the text index
     * @throws HodErrorException If HOD returns a non-200 response
     */
    @GET(URL)
    Response getIndexStatus(
            @Header("token") AuthenticationToken<?, ?> token,
            @Path("index_identifier") ResourceName indexIdentifier
    ) throws HodErrorException;
}
