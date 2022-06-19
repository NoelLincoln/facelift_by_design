/*
 * The MIT License
 *
 * Copyright 2016 Riverbank Solutions Limited.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.facelift.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * This class transform a JSON map to a JSON tree without the hash key
 *
 * @category    Templates
 * @package     Dev
 * @since       Nov 05, 2018
 * @author      Ignatius
 * @version     1.0.0
 *
 */
public class JsonView extends MappingJackson2JsonView {

    @Override
    public void setObjectMapper(ObjectMapper objectMapper) {
        DefaultSerializerProvider sp = new DefaultSerializerProvider.Impl();

        // Use the custom serializer for null items
        sp.setNullValueSerializer(new NullSerializer());
        objectMapper.setSerializerProvider(sp);

        // To prevent errors on empty beans
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        super.setObjectMapper( objectMapper );
    }
    /**
     *
     * @param model
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Object filterModel(Map<String, Object> model) {
        Object data = super.filterModel(model);

        if( !(data instanceof Map) )
            return data;

        Map map = (Map) data;

        if( map.size() == 1 )
            return map.values().toArray()[0];
        else return map;
    }

}
