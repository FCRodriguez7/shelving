package edu.yale.sml.logic;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.yale.sml.model.OrbisRecord;

/**
 * Helper logic static class
 */
@ManagedBean
@RequestScoped
public class Rules {

   private static final Logger logger = LoggerFactory.getLogger(Rules.class);

    public static final String NOT_CHARGED_STRING = "Not Charged";

    public static final String NULL_BARCODE_STRING = "00000000";

    /**
     * @param s item status
     * @return whether status is valid or invalid (missing, lost, charged, or not charged)
     */
    public static boolean isValidItemStatus(final String s) {
        if (s.equals("Missing") || s.equals("Lost") || s.equals("Charged") || s.startsWith("Renewed")
                || s.startsWith("In Transit") || s.startsWith("Missing") || s.startsWith("On Hold")
                || s.startsWith("Withdrawn") || s.startsWith("Claims Returned") || s.startsWith("Lost")
                || s.startsWith("Recall Request") || s.startsWith("Overdue") || s.startsWith("Withdrawn")
                || s.startsWith("Overdue")) {
            return false;
        } else if (s.equals("Not Charged") || s.equals("Discharged") || s.startsWith("Not Charged")
                || s.startsWith("Discharged") || s.startsWith("Circulation Review")
                || s.startsWith("Cataloging Review") || s.startsWith("Damaged")) {
            return true;
        }
        return false;
    }

    public static String getLastValidDisplayCallNum(final List<OrbisRecord> list) {
        int lastValid = 0;

        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getITEM_BARCODE().contains(NULL_BARCODE_STRING)) {
                lastValid = i;
            }
        }

        if (list.get(list.size() - 1).getITEM_BARCODE().contains(NULL_BARCODE_STRING)) {
            return list.get(lastValid).getDISPLAY_CALL_NO();
        } else {
            return list.get(list.size() - 1).getDISPLAY_CALL_NO();
        }
    }

    public static String getFirstValidDisplayCallNum(final List<OrbisRecord> list) {
        int firstValid = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getITEM_BARCODE().contains(NULL_BARCODE_STRING)) {
                //ignore
            } else {
                firstValid = i;
                break;
            }
        }
        return list.get(firstValid).getDISPLAY_CALL_NO();
    }

    /**
     * Determines location error based on whether the UI input and the actual location mismatch.
     * Note: Currently locations are hardcoded.
     * @param location
     * @param finalLocationName
     * @return
     */
    public static boolean isLocationError(final String location, final String finalLocationName) {
        if (finalLocationName.equalsIgnoreCase("art") && (location.equalsIgnoreCase("artref")
                || location.equalsIgnoreCase("artrefl") || location.equalsIgnoreCase("dra")
                || location.equalsIgnoreCase("drarefl"))) {
            return false;
        }

        if (finalLocationName.equalsIgnoreCase("med") && (location.equalsIgnoreCase("medwk1"))) {
            return false;
        }

        if (finalLocationName.equalsIgnoreCase("medref") && (location.equalsIgnoreCase("medwk1"))) {
            return false;
        }

        if (finalLocationName.equalsIgnoreCase("medrefeol") && (location.equalsIgnoreCase("medwk1"))) {
            return false;
        }

        if (!location.equalsIgnoreCase(finalLocationName)) {
            return true;
        }

        return false;
    }

}