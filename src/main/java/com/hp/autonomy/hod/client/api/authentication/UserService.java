package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Service for getting user details from HP Haven OnDemand
 */
public interface UserService {

    /**
     * Get details for the user represented by the given user unbound token
     * @param userUnboundToken The user unbound token
     * @return A response containing the user details
     * @throws HodErrorException
     */
    @GET("/user")
    GetUserResponse getUser(@Header("user_token") AuthenticationToken userUnboundToken) throws HodErrorException;

}
