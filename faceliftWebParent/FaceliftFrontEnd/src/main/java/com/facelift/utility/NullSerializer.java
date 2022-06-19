package com.facelift.utility;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 *
 * This class overwrites Jackson JSON NullSerializer to output an empty String
 * for Bean fields with null values
 *
 * @category    templates
 * @package     Dev
 * @since       Nov 05, 2018
 * @author      Ignatius
 * @version     1.0.0
 */
public class NullSerializer extends JsonSerializer<Object> {

    // Generate the preferred JSON value
    @Override
    public void serialize(Object t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeString("");
    }
}
