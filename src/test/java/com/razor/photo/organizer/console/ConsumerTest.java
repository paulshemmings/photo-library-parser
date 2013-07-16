package com.razor.photo.organizer.console;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: phemmings
 * Date: 7/15/13
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConsumerTest {

    private ProductionLine productionLine = new ProductionLine();

    @Test
    public void setConsumerFlagTrueSuccessfully() {
        Consumer c = new Consumer(this.productionLine);
        c.startConsuming();
        assertFalse(c.consumptionComplete());
    }

    @Test
    public void setConsumerFlagFalseSuccessfully() {
        Consumer consumer = new Consumer(this.productionLine);
        consumer.stopConsuming();
        assertTrue(consumer.consumptionComplete());
    }
}
