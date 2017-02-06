package com.mgarcia;

import java.io.BufferedReader;
import java.io.FileReader;

import com.mgarcia.hosts.HostsTree;

public class TestMain {

    public static void main(String[] args) throws Exception {

        HostsTree tree = new HostsTree();

        try (BufferedReader br = new BufferedReader(new FileReader("blocklist.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                tree.addUrl(line);
            }
        }

        System.out.println(tree.toString());
    }
}
