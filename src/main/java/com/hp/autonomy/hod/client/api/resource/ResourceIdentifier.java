package com.hp.autonomy.hod.client.api.resource;

import java.io.Serializable;

/**
 * Type that can be used as the identifier for a resource when making a request to HOD.
 */
public interface ResourceIdentifier extends Serializable {

    String SEPARATOR = ":";

    /**
     * @return A string representation of this resource suitable for sending to HOD.
     */
    @Override
    String toString();

}
