/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.textindexing.AddToTextIndexRequestBuilder;
import com.hp.autonomy.hod.client.api.textindexing.Document;
import com.hp.autonomy.hod.client.api.textindexing.Documents;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.HavenOnDemandJobStatusRunnable;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.util.TestCallback;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import retrofit.mime.TypedFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)
public class HavenOnDemandServiceITCase extends AbstractHodClientIntegrationTest {

    private HavenOnDemandService havenOnDemandService;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        havenOnDemandService = getRestAdapter().create(HavenOnDemandService.class);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @After
    public void tearDown() throws InterruptedException {
        scheduledExecutorService.shutdown();
        scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS);
    }

    public HavenOnDemandServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testGet() throws HodErrorException {
        final Map<String, Object> params = new HashMap<>();
        params.put("apiKey", endpoint.getApiKey());
        params.put("text", "*");
        params.put("total_results", true);

        final Map<String, Object> result = havenOnDemandService.get("querytextindex", params);

        assertThat(result.get("totalhits"), is(instanceOf(Integer.class)));
        assertThat((Integer) result.get("totalhits"), is(greaterThan(0)));
    }

    @Test
    public void testAsyncGet() throws HodErrorException {
        final Map<String, Object> params = new HashMap<>();
        params.put("apiKey", endpoint.getApiKey());
        params.put("text", "*");
        params.put("total_results", true);

        final JobId jobId = havenOnDemandService.getAsync("querytextindex", params);

        final JobStatus<Map<String, Object>> jobResult = havenOnDemandService.getJobResult(endpoint.getApiKey(), jobId);

        final Map<String, Object> result = jobResult.getActions().get(0).getResult();

        assertThat(result.get("totalhits"), is(instanceOf(Integer.class)));
        assertThat((Integer) result.get("totalhits"), is(greaterThan(0)));
    }

    @Test
    public void testPost() throws HodErrorException {
        final Map<String, Object> params = new HashMap<>();
        params.put("apiKey", endpoint.getApiKey());
        params.put("file", new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/hod/client/api/search/queryText.txt")));
        params.put("total_results", true);

        final Map<String, Object> result = havenOnDemandService.post("querytextindex", params);

        assertThat(result.get("totalhits"), is(instanceOf(Integer.class)));
        assertThat((Integer) result.get("totalhits"), is(greaterThan(0)));
    }

    @Test
    public void testAsyncPost() throws HodErrorException {
        final Document document = new Document.Builder()
                .setReference("0e5ed498-7251-4acf-8658-34ed874ca352")
                .setTitle("Some stuff")
                .setContent("")
                .build();

        final Documents<Document> documents = new Documents<>(document);

        final Map<String, Object> params = new HashMap<>();
        params.put("apiKey", endpoint.getApiKey());
        params.put("json", documents);
        params.put("index", getIndex());
        params.put("duplicate_mode", AddToTextIndexRequestBuilder.DuplicateMode.replace);

        final JobId jobId = havenOnDemandService.postAsync("addtotextindex", params);

        final JobStatus<Map<String, Object>> jobResult = havenOnDemandService.getJobResult(endpoint.getApiKey(), jobId);

        final Map<String, Object> result = jobResult.getActions().get(0).getResult();

        assertThat(result.get("references"), is(notNullValue()));
        assertThat(result.get("references"), is(instanceOf(List.class)));

        final List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get("references");

        assertThat(resultList, hasSize(1));
    }

    @Test
    public void testAsyncPostWithJobStatus() throws HodErrorException, InterruptedException {
        final Document document = new Document.Builder()
                .setReference("0e5ed498-7251-4acf-8658-34ed874ca352")
                .setTitle("Some stuff")
                .setContent("")
                .build();

        final Documents<Document> documents = new Documents<>(document);

        final Map<String, Object> params = new HashMap<>();
        params.put("apiKey", endpoint.getApiKey());
        params.put("json", documents);
        params.put("index", getIndex());
        params.put("duplicate_mode", AddToTextIndexRequestBuilder.DuplicateMode.replace);

        final JobId jobId = havenOnDemandService.postAsync("addtotextindex", params);

        final CountDownLatch latch = new CountDownLatch(1);
        final TestCallback<Map<String, Object>> testCallback = new TestCallback<>(latch);
        final Runnable runnable = new HavenOnDemandJobStatusRunnable(havenOnDemandService, endpoint.getApiKey(), jobId, testCallback, scheduledExecutorService);

        scheduledExecutorService.submit(runnable);

        latch.await();

        final Map<String, Object> result = testCallback.getResult();

        assertThat(result.get("references"), is(notNullValue()));
        assertThat(result.get("references"), is(instanceOf(List.class)));

        final List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get("references");

        assertThat(resultList, hasSize(1));
    }

}