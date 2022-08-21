/*
 * The MIT License
 *
 * Copyright 2015 Anthony Mwawughanga.
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

import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * This class will be used by the controllers to render a given view using the
 * layout defined.
 *
 * @category    Templates
 * @package     Dev
 * @since       Nov 05, 2018
 * @author      Ignatius
 * @version     1.0.0
 */
public class View {

    /**
     * Properties managed by this class
     */
    private ModelAndView _view;  // The Data to place in the view
    private MessageSource _mS;   // The item used to fetch the language params
    private Locale _locale;      // The current locale used in displaying content

    /**
     * This class constructor initialises the view with the name of the view to
     * use to render the page
     *
     * @param viewName
     */
    public View(String viewName) {
        init(viewName, null);
    }

    /**
     * This class constructor initialises the view with the name of the view to
     * use to render the page and the parameters that should be included in the
     * view to render
     *
     * @param viewName
     * @param map
     */
    public View(String viewName, Map<String, ?> map) {
        init(viewName, map);
    }

    /**
     * Add one attribute at a time to the view
     *
     * @param label
     * @param object
     * @return View
     */
    public View addAttribute(String label, Object object) {
        _view.addObject(label, object);
        return this;
    }

    /**
     * Add/modify attributes used by the view
     *
     * @param map
     * @return View
     */
    public View mergeAttributes(Map<String, ?> map) {
        _view.addAllObjects(map);
        return this;
    }

    /**
     * Use the data managed by this class to get the ModelAndView object that
     * will be used to render a given page
     *
     * @return ModelAndView
     */
    public ModelAndView getView() {
        // Return the view
        return _view;
    }

    /**
     * Call this method to redirect to a given url
     *
     * @param url
     * @return ModelAndView
     */
    public ModelAndView redirect(String url) {
        return new ModelAndView("redirect:/" + url);
    }

    /**
     * This method is used to return a json object from a hashmap
     *
     * @param obj
     * @return ModelAndView
     */
    public ModelAndView sendJSON(Object obj) {
        ModelAndView view = new ModelAndView( new JsonView());
        view.addObject( obj );

        // Get the result
        return view;
    }

    /**
     * Get the message to display using the locale that the user has specified
     *
     * @param message
     * @return string
     */
    public String getMessage(String message) {
        return getMessage(message, null);
    }

    /**
     * Get the message to display using the locale that the user has specified
     *
     * @param message
     * @param params
     * @return string
     */
    public String getMessage(String message, Object[] params) {
        // Check if the message source has been initialised
        if (_mS == null) {
            _mS = new ClassPathXmlApplicationContext("spring-message-source.xml");
        }

        // Return what has been generated
        return _mS.getMessage(message, params, getLocale());
    }

    /**
     * Called to get the current app locale as used to render the content
     *
     * @return Locale
     */
    public Locale getLocale() {
        // If the locale has already been set, return the current locale
        if (_locale instanceof Locale) {
            return _locale;
        }

        // Get the current locale
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);

            if (localeResolver != null) {
                _locale = localeResolver.resolveLocale(request);
            }
        }

        // Set the default locale if the current locale isn't defined
        if (_locale == null) {
            _locale = new Locale("en", "GB");
        }

        // Return the locale
        return _locale;
    }

    /**
     * This private method initialises the class
     *
     * @param viewName
     */
    private void init(String viewName, Map<String, ?> map) {
        _view = new ModelAndView();

        // If the view name is set
        if (viewName != null) {
            // Set the view name
            _view.setViewName( viewName );
        }

        // Set the view parameters
        if (map != null) {
            _view.addAllObjects(map);
        }
    }

}
