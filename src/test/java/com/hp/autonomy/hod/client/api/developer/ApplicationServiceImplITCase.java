/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.authentication.*;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

// TODO: Enable ApplicationService integration tests when the integration environment supports the application APIs
@Ignore
@RunWith(Parameterized.class)
public class ApplicationServiceImplITCase extends AbstractHodClientIntegrationTest {
    public static final String APPLICATION_DESCRIPTION = "Test application for the Java HOD client";
    private final ApiKey apiKey;

    public ApplicationServiceImplITCase(final Endpoint endpoint) {
        super(endpoint);
        apiKey = endpoint.getApiKey();
    }

    private AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> developerToken;
    private ApplicationService service;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        final AuthenticationService authenticationService = new AuthenticationServiceImpl(getConfig());

        try {
            final UUID tenantUuid = authenticationService.getApplicationTokenInformation(getTokenProxy()).getTenantUuid();
            developerToken = authenticationService.authenticateDeveloper(apiKey, tenantUuid, DEVELOPER_EMAIL);
        } catch (final HodErrorException e) {
            throw new AssertionError(e);
        }

        service = new ApplicationServiceImpl(getConfig());
    }

    @Test
    public void listApplications() throws HodErrorException {
        final List<Application> applications = service.list(developerToken);

        for (final Application application : applications) {
            assertThat(application.getDomain(), not(nullValue()));
            assertThat(application.getName(), not(nullValue()));
            assertThat(application.getDescription(), not(nullValue()));
            assertThat(application.getDomainDescription(), not(nullValue()));

            assertThat(application.getPrivileges(), not(empty()));
        }

        assertThat("Integration test application was not found in list application response", applicationByName(
            applications,
            DOMAIN_NAME,
            APPLICATION_NAME
        ), not(nullValue()));
    }

    @Test
    public void createListAndDeleteApplication() throws HodErrorException {
        final String appName = randomName();
        service.create(developerToken, DOMAIN_NAME, appName, APPLICATION_DESCRIPTION);

        final List<Application> listPostCreate = service.list(developerToken);
        final Application createdApplication = applicationByName(listPostCreate, DOMAIN_NAME, appName);
        assertThat(createdApplication.getDescription(), is(APPLICATION_DESCRIPTION));

        service.delete(developerToken, DOMAIN_NAME, appName);

        final List<Application> listPostDelete = service.list(developerToken);
        assertThat(applicationByName(listPostDelete, DOMAIN_NAME, appName), is(nullValue()));
    }

    @Test
    public void failsToCreateDuplicateApplication() {
        HodErrorException exception = null;

        try {
            service.create(developerToken, DOMAIN_NAME, APPLICATION_NAME, APPLICATION_DESCRIPTION);
        } catch (final HodErrorException e) {
            exception = e;
        }

        assertThat(exception.getErrorCode(), is(HodErrorCode.INVALID_JOB_ACTION_PARAMETER));
    }

    @Test
    public void failsToDeleteNonExistentApplication() {
        HodErrorException exception = null;

        try {
            service.delete(developerToken, DOMAIN_NAME, randomName());
        } catch (final HodErrorException e) {
            exception = e;
        }

        assertThat(exception.getErrorCode(), is(HodErrorCode.INVALID_APPLICATION));
    }

    @Test
    public void listAuthentications() throws HodErrorException {
        final List<Authentication> authentications = service.listAuthentications(developerToken, DOMAIN_NAME, APPLICATION_NAME);

        // The IT suite uses an API key from this application to authenticate, so at least one API key must be assigned
        boolean foundApiKey = false;

        for (final Authentication authentication : authentications) {
            assertThat(authentication.getCreatedAt(), not(nullValue()));
            assertThat(authentication.getMode(), not(nullValue()));
            assertThat(authentication.getUuid(), not(nullValue()));

            if (ApplicationAuthMode.API_KEY.equals(authentication.getMode())) {
                foundApiKey = true;
            }
        }

        assertTrue("The test API key was not returned from list authentications", foundApiKey);
    }

    @Test
    public void createApplicationAndListAuthentications() throws HodErrorException {
        final String name = randomName();
        service.create(developerToken, DOMAIN_NAME, name, APPLICATION_DESCRIPTION);

        final List<Authentication> authentications = service.listAuthentications(developerToken, DOMAIN_NAME, name);
        assertThat(authentications, is(empty()));

        service.delete(developerToken, DOMAIN_NAME, name);
    }

    @Test
    public void addAuthModeAndAuthentication() throws HodErrorException {
        final String name = randomName();
        service.create(developerToken, DOMAIN_NAME, name, APPLICATION_DESCRIPTION);

        service.addAuthenticationMode(developerToken, DOMAIN_NAME, name, ApplicationAuthMode.API_KEY, UserAuthMode.NONE, TokenType.Simple.INSTANCE);

        final ApiKey apiKey = service.addAuthentication(developerToken, DOMAIN_NAME, name);
        assertThat(apiKey, not(nullValue()));

        final List<Authentication> authentications = service.listAuthentications(developerToken, DOMAIN_NAME, name);
        assertThat(authentications, hasSize(1));

        final Authentication newAuthentication = authentications.get(0);
        assertThat(newAuthentication.getCreatedAt(), not(nullValue()));
        assertThat(newAuthentication.getUuid(), not(nullValue()));

        assertThat(newAuthentication.getMode(), is(ApplicationAuthMode.API_KEY));

        service.delete(developerToken, DOMAIN_NAME, name);
    }

    private Application applicationByName(final List<Application> applications, final String domain, final String name) {
        for (final Application application : applications) {
            if (domain.equals(application.getDomain()) && name.equals(application.getName())) {
                return application;
            }
        }

        return null;
    }

    private String randomName() {
        return "java-hod-client-" + Math.round(Math.random() * 1000);
    }
}
