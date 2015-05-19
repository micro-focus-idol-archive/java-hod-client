# java-hod-client

Java Client for communicating with [HP Haven OnDemand](http://www.idolondemand.com)

## About
java-hod-client provides a Java Interface to the HP Haven OnDemand APIs to simplify calling the HP Haven OnDemand APIs
from Java, allowing consumers of java-hod-client to access HP Haven OnDemand in a typesafe way.

## Goals
Currently only a small subset of HP Haven OnDemand APIs are supported. The eventual aim to support all the HP Haven OnDemand
APIs.

If an API you need is not supported, submit a pull request!

## Usage

java-hod-client is available from the central Maven repository.

    <dependency>
        <groupId>com.hp.autonomy.hod</groupId>
        <artifactId>java-hod-client</artifactId>
        <version>0.5.0</version>
    </dependency>

java-hod-client uses [Retrofit](http://square.github.io/retrofit/) as the basis of its HTTP implementation. This
requires the use of a RestAdapter to use the services. We have used [Jackson](https://github.com/FasterXML/jackson) for
JSON transformation, so you will need to use the Jackson Converter. To send multipart requests to HP Haven OnDemand
correctly, you will need to wrap this in an HodConverter. An error handler is supplied for parsing error responses from
HP Haven OnDemand.

    final RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint("https://api.idolondemand.com/")
        .setConverter(new HodConverter(new JacksonConverter()))
        .setErrorHandler(new HodErrorHandler())
        .build();
        
    final AuthenticationService authenticationService = restAdapter.create(AuthenticationService.class);

    final QueryTextIndexService queryTextIndexService = restAdapter.create(QueryTextIndexService.class);
    
HP Haven OnDemand requires the use of a token to make API requests. These can be obtained with the AuthenticationService.
For more information, consult the HP Haven OnDemand documentation.

    final AuthenticationToken token = authenticationService.authenticateApplication(
        myApiKey,
        myApplication,
        myDomain,
        TokenType.simple
    ).getToken();

You can then call the methods on queryTextIndexService with the token to communicate with HP Haven OnDemand.

    final Map<String, Object> params = new QueryTextIndexRequestBuilder()
        .setAbsoluteMaxResults(10)
        .setTotalResults(true)
        .setIndexes("wiki_eng")
        .build();

    final Documents documents = queryTextIndexService.queryTextIndexWithText(
        token,
        "cats",
        params);

## Library structure
APIs can be found in the com.hp.autonomy.hod.client.api package. APIs are packaged more or less according to their urls.

## Asynchronous requests
For asynchronous actions the Retrofit service returns a JobId. We also provide a service which will track the status of
the job IDs.

    final AddToTextIndexJobService addToTextIndexService = new AddToTextIndexJobService(restAdapter.create(AddToTextIndexJobService.class));

The methods on this service take a callback which will be called when the job is completed

    addToTextIndexService.addFileToTextIndex(getToken(), file, getIndex(), params, new HodJobCallback<AddToTextIndexResponse>() {
        @Override
        public void success(final AddToTextIndexResponse result) {
            // called if the job succeeds
        }

        @Override
        public void error(final HodErrorCode error) {
            // called if the job fails
        }

        @Override
        public void handleException(final RuntimeException exception) {
            // called if a RuntimeException is thrown during the process
        }
    });


The APIs which are currently asynchronous are

* AddToTextIndex
* DeleteFromTextIndex
* CreateTextIndex
* DeleteTextIndex

## Request Interceptors
For cases where you want to send the same token with every request, we provide RequestInterceptors. These are
configured when the RestAdapter is created. Most API methods have a version which does not take a token, which can be used
in conjunction with a request interceptor.

    final AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService() {
        @Override
        public AuthenticationToken getToken() {
            return myToken;
        }
    }

    // set up a RestAdapter using a request interceptor
    final RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint("https://api.idolondemand.com/")
        .setConverter(new HodConverter(new JacksonConverter()))
        .setErrorHandler(new HodErrorHandler())
        .setRequestInterceptor(new AuthenticationTokenServiceRequestInterceptor(authenticationTokenService))
        .build();

    // query for documents using the token provided by the service
    final Documents documents = queryTextIndexService.queryTextIndexWithText(
        "cats",
        params);

When using the RequestInterceptor, be sure not to call any method which takes an AuthenticationToken. This is because 
the token will be set twice, which makes HP Haven OnDemand sad.

    // set up a RestAdapter using a request interceptor
    final RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint("https://api.idolondemand.com/")
        .setConverter(new HodConverter(new JacksonConverter()))
        .setErrorHandler(new HodErrorHandler())
        .setRequestInterceptor(new AuthenticationTokenServiceRequestInterceptor(authenticationTokenService))
        .build();

    // BROKEN - this will generate an HodErrorException as the token will be set twice
    final Documents documents = queryTextIndexService.queryTextIndexWithText(
        someOtherToken
        "cats",
        params);

## HavenOnDemandService
For those times where the API you need to use is not currently supported, there is the HavenOnDemandService. This can
query any API.

    final HavenOnDemandService havenOnDemandService = restAdapter.create(HavenOnDemandService.class)
    
    final Map<String, Object> params = new HashMap<>();
    params.put("token", token);
    params.put("text", "*");
    
    final Map<String, Object> result = havenOnDemandService.get("textindex", "query", "search", 1, params);
    
This approach requires a greater familiarity with the HP Haven OnDemand documentation. It also removes the type safety of
the dedicated services, making the response useful only for automated transformation into JSON.

If you find yourself looking at the HavenOnDemandService, we encourage you to write a service for the API and submit a 
pull request.

## Contributing
We welcome pull requests. These must be licensed under the MIT license. Please submit pull requests to the develop
branch - the master branch is for stable code only.

## Is it any good?
Yes.

## License
Copyright 2015 Hewlett-Packard Development Company, L.P.

Licensed under the MIT License (the "License"); you may not use this project except in compliance with the License.