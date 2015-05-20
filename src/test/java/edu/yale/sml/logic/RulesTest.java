package edu.yale.sml.logic;

import edu.yale.sml.model.OrbisRecord;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RulesTest {

    @Test
    public void shouldDetermineValidItemStatus() {
        assertFalse(Rules.isValidItemStatus("Lost"));
        assert (Rules.isValidItemStatus("Not Charged"));
    }

    @Test
    public void shouldGetLastValidDisplayCallNum() {
        List<OrbisRecord> orbisRecords = new ArrayList<OrbisRecord>();
        OrbisRecord orbisRecord = new OrbisRecord();
        orbisRecord.setITEM_BARCODE("333222202");
        orbisRecord.setDISPLAY_CALL_NO("PT 6CN");
        orbisRecords.add(orbisRecord);
        String s = Rules.getLastValidDisplayCallNum(orbisRecords);

        assertEquals(s, "PT 6CN");
    }

    @Test
    public void shouldGetFirstValidDisplayCallNum() {
        List<OrbisRecord> orbisRecords = new ArrayList<OrbisRecord>();
        OrbisRecord orbisRecord = new OrbisRecord();
        orbisRecord.setITEM_BARCODE("333222202");
        orbisRecord.setDISPLAY_CALL_NO("PT 6CN");

        orbisRecords.add(orbisRecord);
        String s = Rules.getFirstValidDisplayCallNum(orbisRecords);

        assertEquals(s, "PT 6CN");
    }

}
