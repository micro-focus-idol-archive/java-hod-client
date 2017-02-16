package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class GetParametricRangesServiceIT extends AbstractHodClientIntegrationTest {
    private GetParametricRangesService service;

    public GetParametricRangesServiceIT(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        service = new GetParametricRangesServiceImpl(getConfig());
    }

    @Test
    public void getParametricRanges() throws HodErrorException {
        final GetParametricRangesRequestBuilder params = new GetParametricRangesRequestBuilder()
                .setTotalRanges(true)
                .setValueDetails(false)
                .setText("*");

        final List<FieldRanges> output = service.getParametricRanges(
                getTokenProxy(),
                Collections.singletonList("place_population"),
                Collections.singletonList(ResourceName.WIKI_ENG),
                "FIXED{.,1E6,1e7,1E8,.}:place_population",
                params
        );

        assertThat(output, hasSize(1));

        final FieldRanges placePopulation = output.get(0);
        assertThat(placePopulation.getName(), is("place_population"));
        assertThat(placePopulation.getValueDetails(), is(nullValue()));
        assertThat(placePopulation.getTotalRanges(), is(4));

        final List<FieldRanges.ValueRange> valueRanges = placePopulation.getValueRanges();
        assertThat(valueRanges, hasSize(4));

        assertThat(valueRanges.get(0).getLowerBound(), is(nullValue()));
        assertThat(valueRanges.get(0).getUpperBound(), is(1000000D));
        assertThat(valueRanges.get(0).getCount(), is(not(nullValue())));

        assertThat(valueRanges.get(1).getLowerBound(), is(1000000D));
        assertThat(valueRanges.get(1).getUpperBound(), is(10000000D));
        assertThat(valueRanges.get(1).getCount(), is(not(nullValue())));

        assertThat(valueRanges.get(2).getLowerBound(), is(10000000D));
        assertThat(valueRanges.get(2).getUpperBound(), is(100000000D));
        assertThat(valueRanges.get(2).getCount(), is(not(nullValue())));

        assertThat(valueRanges.get(3).getLowerBound(), is(100000000D));
        assertThat(valueRanges.get(3).getUpperBound(), is(nullValue()));
        assertThat(valueRanges.get(3).getCount(), is(not(nullValue())));
    }

    @Test
    public void getValueDetails() throws HodErrorException {
        final GetParametricRangesRequestBuilder params = new GetParametricRangesRequestBuilder()
                .setValueDetails(true)
                .setMaxRanges(1)
                .setText("*");

        final List<FieldRanges> output = service.getParametricRanges(
                getTokenProxy(),
                Collections.singletonList("place_population"),
                Collections.singletonList(ResourceName.WIKI_ENG),
                null,
                params
        );

        assertThat(output, hasSize(1));

        final FieldRanges placePopulation = output.get(0);
        assertThat(placePopulation.getName(), is("place_population"));
        assertThat(placePopulation.getTotalRanges(), is(nullValue()));
        assertThat(placePopulation.getValueRanges(), hasSize(1));

        final FieldRanges.ValueDetails valueDetails = placePopulation.getValueDetails();
        assertThat(valueDetails.getCount(), is(notNullValue()));
        assertThat(valueDetails.getMean(), is(notNullValue()));
        assertThat(valueDetails.getSum(), is(notNullValue()));
        assertThat(valueDetails.getMaximum(), is(notNullValue()));
        assertThat(valueDetails.getMinimum(), is(notNullValue()));
    }
}