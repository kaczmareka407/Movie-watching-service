
package org.bp.movie;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.3.2
 * 2023-01-19T22:16:40.446+01:00
 * Generated source version: 3.3.2
 */

@WebFault(name = "movieFault", targetNamespace = "http://www.bp.org")
public class MovieFaultMsg extends Exception {

    private org.bp.types.Fault movieFault;

    public MovieFaultMsg() {
        super();
    }

    public MovieFaultMsg(String message) {
        super(message);
    }

    public MovieFaultMsg(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public MovieFaultMsg(String message, org.bp.types.Fault movieFault) {
        super(message);
        this.movieFault = movieFault;
    }

    public MovieFaultMsg(String message, org.bp.types.Fault movieFault, java.lang.Throwable cause) {
        super(message, cause);
        this.movieFault = movieFault;
    }

    public org.bp.types.Fault getFaultInfo() {
        return this.movieFault;
    }
}
