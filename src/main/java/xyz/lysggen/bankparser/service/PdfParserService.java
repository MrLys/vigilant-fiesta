package xyz.lysggen.bankparser.service;

import org.ghost4j.Ghostscript;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PdfParserService {
    public void executeCommands(File file) throws IOException {

        try {
            File directory = new File(".");
            Process p = Runtime.getRuntime().exec(new String[] {"sh", "-c", "/home/thomas/projects/vigilant-fiesta/topdf.sh"});
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
