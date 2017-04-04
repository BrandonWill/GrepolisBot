package Grepolis.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author Brandon
 * Created by Brandon on 10/31/2015.
 * Time: 2:15 PM
 */
public class BrowserExtension {
    /* These are NOT developed by me and may contain anti-bot measures.
     * They're downloaded directly from the official site. I can't legally host them online.
     * USE AT YOUR OWN RISK!
     *
     * Only tools written purely in JS can be added. Dio Tools uses GM API and can't be added.
     */

    public static String loadGRC() {
        StringBuilder sb = new StringBuilder();
        try {
            URL GCR = new URL("https://www.grcrt.net/scripts/GrepolisReportConverterV2.user.js");
            URLConnection connection = GCR.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
                sb.append("\n");
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
