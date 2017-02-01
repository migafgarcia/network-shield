package settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * Single
 *
 *
 */
public class Settings {

    private final Logger logger = LoggerFactory.getLogger(Settings.class);


    // SETTINGS


    // Whether or not to log queried hosts
    private boolean logRequests = true;

    // Set of paths to blocklist files
    // TODO(migafgarcia): later change this to allow different sources
    private HashSet<String> BLOCKLISTS = new HashSet<>();

    // The size of the final blocklist, -1 if it is not calculated
    private int CALC_BLOCKLIST_SIZE = -1;

    /*
     * Whether to return NAME_ERROR or to redirect blocked queries to a fake http server
     *  to avoid clients re-querying many times
     */
    private boolean RUN_WITH_SINKHOLE = false;

    public static Settings parseSettings() {




        return null;
    }

}
