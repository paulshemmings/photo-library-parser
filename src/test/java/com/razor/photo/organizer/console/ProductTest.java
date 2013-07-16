package com.razor.photo.organizer.console;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: phemmings
 * Date: 7/16/13
 * Time: 9:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProductTest {


    @Test
    public void storesImageFileSuccessfuly() throws ImageProcessingException, MetadataException {
        File imageFile = mock(File.class);
        Product product = new Product(imageFile);
        assertTrue(product.getImageFile() != null);
    }

    @Test
    public void canReadNonDateInformationSuccessfully() throws ImageProcessingException {
        /*
        File imageFile = mock(File.class);
        Product product = new Product(imageFile);

        Iterator directoryIterator =  product.getMetadata().getDirectoryIterator();
        while (directoryIterator.hasNext()) {
            Directory directory = (Directory) directoryIterator.next();

            Iterator tagIterator = directory.getTagIterator();
            while (tagIterator.hasNext()) {
                Tag tag = (Tag) tagIterator.next();
                System.out.println(tag);
            }
        }
        */
    }
}
