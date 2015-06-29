# java-hod-client

Java Client for communicating with [HP Haven OnDemand](http://www.idolondemand.com)

## About
java-hod-client provides a Java Interface to the HP Haven OnDemand APIs to simplify calling the HP Haven OnDemand APIs
from Java, allowing consumers of java-hod-client to access HP Haven OnDemand in a typesafe way. It also automatically
handles refreshed tokens in the headers of HP Haven OnDemand responses.

## Goals
Currently only a small subset of HP Haven OnDemand APIs are supported. The eventual aim to support all the HP Haven
OnDemand APIs.

If an API you need is not supported, submit a pull request!

## Usage

java-hod-client is available from the central Maven repository.

    <dependency>
        <groupId>com.hp.autonomy.hod</groupId>
        <artifactId>java-hod-client</artifactId>
        <version>0.9.0</version>
    </dependency>

java-hod-client services are configured using instances of HodServiceConfig. The easiest way to do this is to use the
default options:

    final HodServiceConfig config = new HodServiceConfig.Builder("https://api.idolondemand.com").build();

This will create a configuration object with an InMemoryTokenRepository for storing tokens, a default Jackson
ObjectMapper, a default HodErrorHandler, and a default HTTP client. 

It is also possible to override the default configuration:

    final HodServiceConfig config = new HodServiceConfig.Builder("https://api.idolondemand.com")
        .setTokenRepository(tokenRepository) // configure an alternative TokenRepository
        .setHttpClient(httpClient) // use a custom Apache HttpClient - useful if you're behind a proxy
        .setObjectMapper(objectMapper) // use a custom Jackson ObjectMapper if you're using custom types
        .setErrorHandler(errorHandler) // use a custom error handler
        .build():

Once a config object has been created, it can be used to construct services. To ensure the TokenRepository works
correctly, the same configuration object should be used for all services.

    final AuthenticationService authenticationService = new AuthenticationServiceImp(config);

    final QueryTextIndexService<Documents> queryTextIndexService = QueryTextIndexService.documentsService(config);
    
HP Haven OnDemand requires the use of a token to make API requests. These can be obtained with the 
AuthenticationService. The AuthenticationServiceImpl will store the token in the configured TokenRepository and return a
TokenProxy which can be used to retrieve the token. This allows the token to be updated when the refresh_token header is
sent by HP Haven OnDemand.
For more information, consult the HP Haven OnDemand documentation.

    final TokenProxy tokenProxy = authenticationService.authenticateApplication(
        myApiKey,
        myApplication,
        myDomain,
        TokenType.simple
    );

You can then call the methods on queryTextIndexService with the token proxy to communicate with HP Haven OnDemand.

    final QueryRequestBuilder params = new QueryRequestBuilder()
        .setAbsoluteMaxResults(10)
        .setTotalResults(true)
        .addIndexes(index);

    final Documents documents = queryTextIndexService.queryTextIndexWithText(
        tokenProxy,
        "cats",
        params);
        
If there is no token associated with the TokenProxy, or the token has expired according to its expiry field, a
HodAuthenticationFailedException will be thrown.

## Library structure
APIs can be found in the com.hp.autonomy.hod.client.api package. APIs are packaged more or less according to their urls.

## Asynchronous requests
Some APIs are provided as a PollingService. These services will obtain a JobId from HP Haven OnDemand, and poll the job
status API until completion.

    final AddToTextIndexService addToTextIndexService = new AddToTextIndexPollingService(config);

The methods on this service take a callback which will be called when the job is completed

    addToTextIndexService.addFileToTextIndex(
        tokenProxy, 
        file, 
        index, 
        params, 
        new HodJobCallback<AddToTextIndexResponse>() {
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

## TokenProxyService
For cases where you want to send the same token with every request, we provide TokenProxyService. When using a
TokenProxyService, use the methods which do not take a TokenProxy as the first argument.

    // create a TokenProxyService
    final TokenProxyService tokenProxyService = new TokenProxyService() {
        @Override
        public TokenProxy getTokenProxy() {
            return myTokenProxy;
        }
    }

    // create a configuration object
    final HodServiceConfig config = new HodServiceConfig.Builder("https://api.idolondemand.com")
        .setTokenProxyService(tokenProxyService) // configure the TokenProxyService
        .build():

    // query for documents using the token proxy provided by the service
    final Documents documents = queryTextIndexService.queryTextIndexWithText(
        "cats",
        params);

If a service method is called with a TokenProxy this will take priority over the TokenProxyService.

## HavenOnDemandService
For those times where the API you need to use is not currently supported, there is the HavenOnDemandService. This can
query any API.

    final HavenOnDemandService havenOnDemandService = new HavenOnDemandServiceImpl(config);
    
    final Map<String, Object> params = new HashMap<>();
    params.put("text", "*");
    
    final Map<String, Object> result = havenOnDemandService.get(
        tokenProxy, 
        "textindex", 
        "query", 
        "search", 
        1, 
        params, 
        Map.class
    );
    
This approach requires a greater familiarity with the HP Haven OnDemand documentation. If Map is used as the return 
type, it also removes the type safety and gives an object which is only useful for things like automated transformation 
into JSON.

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