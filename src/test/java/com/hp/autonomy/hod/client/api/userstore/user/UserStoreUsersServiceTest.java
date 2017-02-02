package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit.RestAdapter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserStoreUsersServiceTest {
    @Mock
    private HodServiceConfig<EntityType, TokenType.Simple> hodServiceConfig;

    @Mock
    private ResourceName resourceName;

    @Mock
    private Requester<EntityType, TokenType.Simple> requester;

    @Mock
    private RestAdapter restAdapter;

    private ObjectMapper objectMapper;
    private UserStoreUsersService userStoreUsersService;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        when(hodServiceConfig.getObjectMapper()).thenReturn(objectMapper);
        when(hodServiceConfig.getRequester()).thenReturn(requester);
        when(hodServiceConfig.getRestAdapter()).thenReturn(restAdapter);

        userStoreUsersService = new UserStoreUsersServiceImpl(hodServiceConfig);
    }

    @Test
    public void getMetadata() throws HodErrorException, IOException {
        final GetMetadataResponse sampleGetMetadataResponse = objectMapper.readValue(getClass()
                .getResourceAsStream("/com/hp/autonomy/hod/client/api/userstore/user/metadata.json"), GetMetadataResponse.class);

        when(requester.makeRequest(any(Class.class), Mockito.<Requester.BackendCaller<EntityType, TokenType.Simple>>any())).thenReturn(sampleGetMetadataResponse);

        final Map<String, JsonNode> metadataMap = userStoreUsersService.getUserMetadata(resourceName, UUID.randomUUID());
        assertNotNull(metadataMap);
        assertThat(metadataMap, hasKey("DisplayName"));
    }
}
