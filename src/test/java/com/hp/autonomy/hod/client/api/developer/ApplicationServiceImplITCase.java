/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.hp.autonomy.hod.client.AbstractDeveloperHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCode;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ApplicationServiceImplITCase extends AbstractDeveloperHodClientIntegrationTest {
    public static final String APPLICATION_DESCRIPTION = "Test application for the Java HOD client";

    public ApplicationServiceImplITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    private ApplicationService service;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        service = new ApplicationServiceImpl(getConfig());
    }

    @Test
    public void listApplications() throws HodErrorException {
        final List<Application> applications = service.list(getDeveloperToken(), getEndpoint().getDomainName());

        for (final Application application : applications) {
            assertThat(application.getDomain(), is(getEndpoint().getDomainName()));
            assertThat(application.getName(), not(nullValue()));
            assertThat(application.getPrivileges(), not(empty()));
        }

        assertThat("Integration test application was not found in list application response", applicationByName(
                applications,
                getEndpoint().getDomainName(),
                getEndpoint().getApplicationName()
        ), isPresent());
    }

    @Test
    public void createListAndDeleteApplication() throws HodErrorException {
        final String appName = randomName();
        service.create(getDeveloperToken(), getEndpoint().getDomainName(), appName, APPLICATION_DESCRIPTION);

        final List<Application> listPostCreate = service.list(getDeveloperToken(), getEndpoint().getDomainName());
        final Optional<Application> optionalApplication = applicationByName(listPostCreate, getEndpoint().getDomainName(), appName);
        assertThat(optionalApplication, isPresent());
        assertThat(optionalApplication.get().getDescription(), is(APPLICATION_DESCRIPTION));

        service.delete(getDeveloperToken(), getEndpoint().getDomainName(), appName);

        final List<Application> listPostDelete = service.list(getDeveloperToken(), getEndpoint().getDomainName());
        assertThat(applicationByName(listPostDelete, getEndpoint().getDomainName(), appName), isEmpty());
    }

    @Test
    public void failsToCreateDuplicateApplication() {
        testErrorCode(HodErrorCode.INVALID_JOB_ACTION_PARAMETER, () -> service.create(getDeveloperToken(), getEndpoint().getDomainName(), getEndpoint().getApplicationName(), APPLICATION_DESCRIPTION));
    }

    @Test
    public void failsToDeleteNonExistentApplication() {
        testErrorCode(HodErrorCode.INVALID_APPLICATION, () -> service.delete(getDeveloperToken(), getEndpoint().getDomainName(), randomName()));
    }

    @Test
    public void listAuthentications() throws HodErrorException {
        final List<AuthenticationDetails> authentications = service.listAuthentications(getDeveloperToken(), getEndpoint().getDomainName(), getEndpoint().getApplicationName());

        // The IT suite uses an API key from this application to authenticate, so at least one API key must be assigned
        final Optional<AuthenticationDetails> maybeApiKeyAuth = authentications.stream()
                .filter(authentication -> ApplicationAuthMode.API_KEY.equals(authentication.getMode()))
                .findFirst();

        assertThat(maybeApiKeyAuth, isPresent());

        authentications.forEach(authentication -> {
            assertThat(authentication.getCreatedAt(), not(nullValue()));
            assertThat(authentication.getMode(), not(nullValue()));
            assertThat(authentication.getUuid(), not(nullValue()));
        });
    }

    @Test
    public void createApplicationAndListAuthentications() throws HodErrorException {
        final String name = randomName();
        service.create(getDeveloperToken(), getEndpoint().getDomainName(), name, APPLICATION_DESCRIPTION);

        final List<AuthenticationDetails> authentications = service.listAuthentications(getDeveloperToken(), getEndpoint().getDomainName(), name);
        assertThat(authentications, is(empty()));

        service.delete(getDeveloperToken(), getEndpoint().getDomainName(), name);
    }

    @Test
    public void createAuthentication() throws HodErrorException {
        final List<AuthenticationDetails> initialAuthentications = service.listAuthentications(getDeveloperToken(), getEndpoint().getDomainName(), getEndpoint().getApplicationName());

        final ApiKey apiKey = service.addAuthentication(getDeveloperToken(), getEndpoint().getDomainName(), getEndpoint().getApplicationName());
        assertThat(apiKey, not(nullValue()));

        final List<AuthenticationDetails> finalAuthentications = service.listAuthentications(getDeveloperToken(), getEndpoint().getDomainName(), getEndpoint().getApplicationName());
        assertThat(finalAuthentications, hasSize(initialAuthentications.size() + 1));
    }

    @Test
    public void createUpdateDescriptionDelete() throws HodErrorException {
        final String name = UUID.randomUUID().toString();
        service.create(getDeveloperToken(), getEndpoint().getDomainName(), name, APPLICATION_DESCRIPTION);

        final String newDescription = "My new application description";
        final ApplicationUpdateRequest updateRequest = ApplicationUpdateRequest.builder().description(newDescription).build();
        service.update(getDeveloperToken(), getEndpoint().getDomainName(), name, updateRequest);

        final List<Application> applications = service.list(getDeveloperToken(), getEndpoint().getDomainName());
        final Optional<Application> application = applicationByName(applications, getEndpoint().getDomainName(), name);
        assertThat(application, isPresent());
        assertThat(application.get().getDescription(), is(newDescription));

        final List<AuthenticationDetails> authentications = service.listAuthentications(getDeveloperToken(), getEndpoint().getDomainName(), name);
        assertThat(authentications, is(empty()));

        service.delete(getDeveloperToken(), getEndpoint().getDomainName(), name);
    }

    @Test
    public void createUpdateAuthenticationsDelete() throws HodErrorException {
        final String name = UUID.randomUUID().toString();
        service.create(getDeveloperToken(), getEndpoint().getDomainName(), name, APPLICATION_DESCRIPTION);

        final ApplicationUpdateRequest updateRequest = ApplicationUpdateRequest.builder()
                .authentications(Collections.singletonList(new Authentication(ApplicationAuthMode.API_KEY, UserAuthMode.NONE, TokenType.Simple.INSTANCE)))
                .build();

        service.update(getDeveloperToken(), getEndpoint().getDomainName(), name, updateRequest);

        // Check description has not changed
        final List<Application> applications = service.list(getDeveloperToken(), getEndpoint().getDomainName());
        final Optional<Application> application = applicationByName(applications, getEndpoint().getDomainName(), name);
        assertThat(application, isPresent());
        assertThat(application.get().getDescription(), is(APPLICATION_DESCRIPTION));

        // Check we can add an authentication
        final ApiKey apiKey = service.addAuthentication(getDeveloperToken(), getEndpoint().getDomainName(), name);
        assertThat(apiKey, not(nullValue()));

        final List<AuthenticationDetails> authentications = service.listAuthentications(getDeveloperToken(), getEndpoint().getDomainName(), name);
        assertThat(authentications, hasSize(1));

        service.delete(getDeveloperToken(), getEndpoint().getDomainName(), name);
    }

    @Test
    public void createUpdateAuthenticationsAndDescriptionDelete() throws HodErrorException {
        final String name = UUID.randomUUID().toString();
        service.create(getDeveloperToken(), getEndpoint().getDomainName(), name, APPLICATION_DESCRIPTION);

        final String newDescription = "Super awesome application!";

        final ApplicationUpdateRequest updateRequest = ApplicationUpdateRequest.builder()
                .description(newDescription)
                .authentications(Arrays.asList(
                        new Authentication(ApplicationAuthMode.API_KEY, UserAuthMode.FACEBOOK, TokenType.Simple.INSTANCE),
                        new Authentication(ApplicationAuthMode.API_KEY, UserAuthMode.TWITTER, TokenType.HmacSha1.INSTANCE)
                ))
                .build();

        service.update(getDeveloperToken(), getEndpoint().getDomainName(), name, updateRequest);

        // Check description has changed
        final List<Application> applications = service.list(getDeveloperToken(), getEndpoint().getDomainName());
        final Optional<Application> application = applicationByName(applications, getEndpoint().getDomainName(), name);
        assertThat(application, isPresent());
        assertThat(application.get().getDescription(), is(newDescription));

        // Check we can add an authentication
        final ApiKey apiKey = service.addAuthentication(getDeveloperToken(), getEndpoint().getDomainName(), name);
        assertThat(apiKey, not(nullValue()));

        final List<AuthenticationDetails> authentications = service.listAuthentications(getDeveloperToken(), getEndpoint().getDomainName(), name);
        assertThat(authentications, hasSize(1));

        service.delete(getDeveloperToken(), getEndpoint().getDomainName(), name);
    }

    private Optional<Application> applicationByName(final Collection<Application> applications, final String domain, final String name) {
        return applications.stream()
                .filter(application -> domain.equals(application.getDomain()) && name.equals(application.getName()))
                .findFirst();
    }

    private String randomName() {
        // Can't use UUID here because UUIDs are too long
        return "java-hod-client-" + new Random().nextInt(1000);
    }
}
