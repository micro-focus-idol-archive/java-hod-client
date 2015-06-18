/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.viewdocument;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.io.File;
import java.io.InputStream;

public interface ViewDocumentService {

    /**
     * Convert a file to HTML and retrieve the result as a stream containing the HTML using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param file The file to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewFile(
        File file,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as a stream containing the HTML using the given token
     * @param tokenProxy The token proxy to use
     * @param file The file to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewFile(
        TokenProxy tokenProxy,
        File file,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as a stream containing the HTML using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param bytes The bytes of the file to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewFile(
        byte[] bytes,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as a stream containing the HTML using the given token
     * @param tokenProxy The token proxy to use
     * @param bytes The bytes of the file to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewFile(
        TokenProxy tokenProxy,
        byte[] bytes,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as a stream containing the HTML using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param inputStream An InputStream representing the file to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewFile(
        InputStream inputStream,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as a stream containing the HTML using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param inputStream An InputStream representing the file to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewFile(
        TokenProxy tokenProxy,
        InputStream inputStream,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert an object store object to HTML and retrieve the result as a stream containing the HTML using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param reference The object store reference to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewReference(
        String reference,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert an object store object to HTML and retrieve the result as a stream containing the HTML using the given
     * token proxy
     * @param tokenProxy The token proxy to use
     * @param reference The object store reference to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewReference(
        TokenProxy tokenProxy,
        String reference,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert publicly accessible url to HTML and retrieve the result as a stream containing the HTML using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * API key
     * @param url The url to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewUrl(
        String url,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert publicly accessible url to HTML and retrieve the result as a stream containing the HTML using the given
     * token proxy
     * @param tokenProxy The token proxy to use
     * @param url The url to view
     * @param params Additional parameters to use for the request
     * @return An InputStream which contains the HTML of the document. This stream must be closed after use.
     * @throws HodErrorException
     */
    InputStream viewUrl(
        TokenProxy tokenProxy,
        String url,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param file The file to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewFileAsHtmlString(
        File file,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param file The file to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewFileAsHtmlString(
        TokenProxy tokenProxy,
        File file,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param bytes The bytes of the file to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewFileAsHtmlString(
        byte[] bytes,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param bytes The bytes of the file to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewFileAsHtmlString(
        TokenProxy tokenProxy,
        byte[] bytes,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param inputStream An InputStream representing the file to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewFileAsHtmlString(
        InputStream inputStream,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param inputStream An InputStream representing the file to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewFileAsHtmlString(
        TokenProxy tokenProxy,
        InputStream inputStream,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a reference to HTML and retrieve the result as an HTML String using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param reference The reference to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewReferenceAsHtmlString(
        String reference,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a reference to HTML and retrieve the result as an HTML String using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param reference The reference to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewReferenceAsHtmlString(
        TokenProxy tokenProxy,
        String reference,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param url The url to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewUrlAsHtmlString(
        String url,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String using the given token proxy
     * @param tokenProxy The token proxy to use to use
     * @param url The url to view
     * @param params Additional parameters to use for the request
     * @return A String containing the HTML
     * @throws HodErrorException
     */
    String viewUrlAsHtmlString(
        TokenProxy tokenProxy,
        String url,
        ViewDocumentRequestBuilder params
    ) throws HodErrorException;

}
