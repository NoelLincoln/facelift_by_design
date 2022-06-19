package com.facelift.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * This class wraps all custom Spring EL functions(compatible with THYMELEAF)
 * utilized by the application view templates
 *
 * @category    utility
 * @package     Dev
 * @since       Nov 05, 2021
 * @author      Noel
 * @version     1.0.0
 */
@Component
public class ViewTags {

    /**
     * Highlights the current menu item on the sidebar view
     *
     * @param uris
     * @return String CSS value: 
     * active open >> for links with sub menu items
     * active >> for a standalone item or a node in a sub-menu
     */
    public static String isActiveMenu(String... uris) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String css = "";
        String currentUri = request.getRequestURI();

        if (uris.length < 2) {
            if (uris[0].equals(currentUri)) {
                css = "active";
            }
        } else {
            for (String uri : uris) {
                if (uri.equals(currentUri)) {
                    css = "active";
                    break;
                }
            }
        }
        return css;
    }
}
