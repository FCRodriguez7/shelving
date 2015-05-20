package edu.yale.sml.view;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "error")
@javax.faces.bean.ViewScoped
public class ErrorBean {

    private final static Logger logger = LoggerFactory.getLogger("edu.yale.sml.view.ErrorBean");

    public ErrorBean() {
        super();
    }

    @PostConstruct
    public void initialize() {
    }

    private void fillStackTrace(final Throwable t, final PrintWriter w) {
        if (t == null) {
            return;
        }

        if (t instanceof ServletException) {
            Throwable cause = ((ServletException) t).getRootCause();
            if (cause != null) {
                w.println("Root cause:");
                fillStackTrace(cause, w);
            }
        } else if (t instanceof SQLException) {
            Throwable cause = ((SQLException) t).getNextException();
            if (cause != null) {
                w.println("Next exception:");
                fillStackTrace(cause, w);
            }
        } else if (t instanceof NullPointerException) {
            //
        } else if (t instanceof org.hibernate.JDBCException) {
            w.append(t.getMessage());
            return;
        } else if (t instanceof org.hibernate.HibernateException) {
            if (t.getMessage().contains("org.hibernate.exception.JDBCConnectionException")) {
                w.append("Error reaching Database Server.");
                w.append(t.getMessage());
                return;
            }
        } else if (t instanceof edu.yale.sml.view.NullFileException) {
            w.append("error");
            return;
        }

        else if (t instanceof java.net.UnknownHostException) {
            w.append(t.getMessage());
            return;
        } else {
            Throwable cause = t.getCause();
            if (cause != null) {
                w.println("Cause of Exception:");
                fillStackTrace(cause, w); // recursive
            }
        }
        w.println("\nStackTrace\n \n:");
        t.printStackTrace(w);
    }

    /*
     * For #{error.message} Depends on messages
     */
    public String getMessage() {
        // JSF puts this info in the request map
        final Map<String, Object> request =FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        final Throwable ex = (Throwable) request.get("javax.servlet.error.exception");
        final StringBuffer sb = new StringBuffer();

        if (ex.getMessage().contains("Form Validation Error")) {
            sb.append("ShelfScan-E01 You did not specify a file.\n");
        } else if (ex.getMessage().contains("NullFileException")) {
            sb.append("ShelfScan-E01 You did not specify a file.\n");
        } else if (ex.getMessage().contains("Hibernate Serialization")) {
            sb.append("ShelfScan-E02 Error saving or serializing values to database.\n");
        } else if (ex.getMessage().contains("CAS")) {
            sb.append("ShelfScan-E03 Error contacting CAS server.\n");
        } else if (ex.getMessage().contains("org.hibernate.exception.JDBCConnectionException")) {
            sb.append("ShelfScan-E05 Error connecting with Database.\n");
        } else if (ex.getMessage().contains("org.hibernate.exception.GenericJDBCException")) {
            sb.append("ShelfScan-E04 Error initiating connection with one or more databases.\n");
        } else {
            sb.append("An exception occured while processing the request. Click on link to notify admin. . .");
        }
        return sb.toString();
    }

    /*
     * For #{error.stackTrace}
     */
    public String getStackTrace() {
        final Map<String, Object> request = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        final Throwable ex = (Throwable) request.get("javax.servlet.error.exception"); // !!
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        fillStackTrace(ex, pw); // TODO note static
        return sw.toString();
    }
}
