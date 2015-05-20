package edu.yale.sml.logic;


import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LogicHelper {

    private static final Logger logger = LoggerFactory.getLogger(LogicHelper.class);

    private static final int MIN_BARCODE_LEN = 1;    //should be 14, but not needed right now

    public static final int MAX_QUERY_COUNT = 1500;

    /**
     * @param fileUploadController PrimeFaces component
     * @return Contents of barcode file as List<String>
     * @throws IOException
     */
    public static String readFileAsString(final UploadedFile fileUploadController)
            throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        List<String> toFind = new ArrayList<String>();
        int count = 0;
        try {
            is = fileUploadController.getInputstream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            List<String> added = new ArrayList<String>();

            while ((line = br.readLine()) != null) {
                if (added.contains(line)) // 39002091235557 -- replace w/ regex
                {
                }
                sb.append(line + "\n");
                added.add(line);
                count++;
            }

            br.close();
            Scanner s = new Scanner(sb.toString()).useDelimiter("\n");
            sb = new StringBuilder();
            while (s.hasNext()) {
                String a = s.next().trim(); // always trim a string
                if (a.length() < MIN_BARCODE_LEN) {
                    logger.debug("Skipping string:" + a);
                    continue;
                }
                sb.append(a + "\n");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    logger.debug("Error closing File stream.");
                }
            }

            if (count > MAX_QUERY_COUNT) {
                return null; // throw exception
            }
        }
        return sb.toString();
    }

    /**
     * Get CAS user from url
     *
     * @param
     * @param contents
     * @return
     * @throws IOException
     */
    public static List<String> getCASUser(final String cas_server_url, final StringBuffer contents)
            throws IOException {
        OutputStreamWriter writer = null;
        BufferedReader in = null;
        List<String> response = new ArrayList<String>();
        StringBuffer response_body = new StringBuffer();
        try {
            URL url = new URL(cas_server_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(contents.toString());
            writer.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response.add(new String(Integer.toString(conn.getResponseCode())));
            String decodedString = "";
            while ((decodedString = in.readLine()) != null) {
                if (decodedString.length() > 0) {
                    response_body.append(decodedString + "\n");
                    response.add(decodedString);
                }
            }
        } catch (java.net.UnknownHostException e) {
            throw new java.net.UnknownHostException();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
        return response;
    }
}
