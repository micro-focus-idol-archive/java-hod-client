package com.hp.autonomy.hod.client.api.textindex.status;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.resource.*;
import com.hp.autonomy.hod.client.api.textindex.IndexFlavor;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class TextIndexStatusServiceITCase extends AbstractHodClientIntegrationTest {
    private TextIndexStatusService service;

    public TextIndexStatusServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        service = new TextIndexStatusServiceImpl(getConfig());
    }

    @Test
    public void getIndexStatus() throws HodErrorException {
        final TextIndexStatus status = service.getIndexStatus(getTokenProxy(), getPrivateIndex());
        assertThat(status.getTotalIndexSize(), greaterThan(0L));
        assertThat(status.getTotalDocuments(), greaterThanOrEqualTo(0L));
        assertThat(status.getComponentCount(), greaterThanOrEqualTo(0));
        assertThat(status.getIndexUpdates24hr(), greaterThanOrEqualTo(0));
        assertThat(status.getFlavor(), is(IndexFlavor.EXPLORER));
        assertThat(status.getUserStore(), not(nullValue()));
    }

    @Test
    public void getIndexStatusWithUuid() throws HodErrorException {
        final ResourceName privateIndex = getPrivateIndex();
        final ResourcesService resourcesService = new ResourcesServiceImpl(getConfig());

        final ListResourcesRequestBuilder resourcesRequest = new ListResourcesRequestBuilder()
                .setDomains(Collections.singleton(privateIndex.getDomain()))
                .setTypes(Collections.singleton(ResourceType.TEXT_INDEX));

        final ResourceUuid resourceUuid = resourcesService.list(getTokenProxy(), resourcesRequest).stream()
                .filter(resourceDetails -> privateIndex.equals(resourceDetails.getResource().getResourceName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not determine private index UUID"))
                .getResource()
                .getResourceUuid();

        final TextIndexStatus status = service.getIndexStatus(getTokenProxy(), resourceUuid);
        assertThat(status.getTotalIndexSize(), greaterThan(0L));
        assertThat(status.getTotalDocuments(), greaterThanOrEqualTo(0L));
        assertThat(status.getComponentCount(), greaterThanOrEqualTo(0));
        assertThat(status.getIndexUpdates24hr(), greaterThanOrEqualTo(0));
        assertThat(status.getFlavor(), is(IndexFlavor.EXPLORER));
        assertThat(status.getUserStore(), not(nullValue()));
    }

    @Test
    public void getInvalidIndexStatus() {
        final ResourceIdentifier invalidIndex = new ResourceName(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        try {
            service.getIndexStatus(getTokenProxy(), invalidIndex);
            fail("Expected HodErrorException");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.INSUFFICIENT_PRIVILEGES));
        }
    }
}