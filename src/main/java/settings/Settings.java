package settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import hosts.HostsTree;

/**
 *
 */
public class Settings {

    @Expose(serialize = false, deserialize = false)
    private static final Logger logger = LoggerFactory.getLogger(Settings.class);

    @SerializedName("log_requests")
    private boolean logRequests;

    private boolean sinkhole;

    @SerializedName("printSize")
    private boolean printSize;

    @SerializedName("output_file")
    private String outputFile;

    @SerializedName("blocklist_files")
    private String[] blocklistFiles;

    @SerializedName("blocklist_urls")
    private String[] blocklistUrls;

    public Settings(boolean logRequests, boolean sinkhole, boolean printSize, String outputFile, String[] blocklistFiles, String[] blocklistUrls) {
        this.logRequests = logRequests;
        this.sinkhole = sinkhole;
        this.printSize = printSize;
        this.outputFile = outputFile;
        this.blocklistFiles = blocklistFiles;
        this.blocklistUrls = blocklistUrls;
    }

    public boolean logRequests() {
        return logRequests;
    }

    public boolean sinkhole() {
        return sinkhole;
    }

    public boolean printSize() {
        return printSize;
    }

    public String outputFile() {
        return outputFile;
    }

    public String[] getBlocklistFiles() {
        return blocklistFiles;
    }

    public String[] getBlocklistUrls() {
        return blocklistUrls;
    }

    /**
     * With the paths of the blocklists, generates the blocklist structure
     * @return the blocklist
     */
    public HostsTree generateBlocklist () {
        HostsTree blocklist = new HostsTree();

        for (String blocklistFile : blocklistFiles) {
            logger.info("Loading file: " + blocklistFile);
            try (BufferedReader br = new BufferedReader(new FileReader(blocklistFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    blocklist.addUrl(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                return null;
            }
        }

        if(printSize)
            logger.info("Loaded blocklist. SIZE: "+ blocklist.size());
        else
            logger.info("Loaded blocklist");

        return blocklist;
    }



    @Override
    public String toString() {
        return "Settings{" +
                "logRequests=" + logRequests +
                ", sinkhole=" + sinkhole +
                ", printSize=" + printSize +
                ", outputFile='" + outputFile + '\'' +
                ", blocklistFiles=" + Arrays.toString(blocklistFiles) +
                ", blocklistUrls=" + Arrays.toString(blocklistUrls) +
                '}';
    }
}
