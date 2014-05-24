/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class ZipExtractor {

    /**
     * Extract ZIP archive
     *
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public static void extract(String zipFile, String outputFolder, boolean makeDir) throws IOException {

        byte[] buffer = new byte[1024];

        //create output directory is not exists
        File folder = new File(outputFolder);
        
        if (!folder.exists()) {
            if (makeDir) {
                folder.mkdir();
            } else {
                throw new FileNotFoundException("Output folder \"" + outputFolder +"\" doesn't exist");
            }
        }

        //get the zip file content
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        
        //get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();

        while (ze != null) {
            String fileName = ze.getName();
            File newFile = new File(outputFolder + File.separator + fileName);

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }
}
