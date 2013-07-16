package com.razor.photo.organizer.console;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;

import java.io.File;
import java.util.Date;

public class Product{

	private File imageFile;
    private Metadata metadata = null;
    private Date createDate = null;

	public Product(){		
	}

	public Product(File file){
		this.imageFile = file;
	}

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}
	public File getImageFile() {
		return imageFile;
	}



    public Metadata getMetadata() {

        if (this.metadata != null) {
            return this.metadata;
        }

        if (this.getImageFile() == null) {
            return null;
        }

        try{
            this.setMetadata(ImageMetadataReader.readMetadata(this.getImageFile()));
        }catch(ImageProcessingException ipex){
            Log.e("metadata failed to read %s with %s", this.getImageFile().getName(), ExceptionHelper.getStackTrace(ipex));
        }catch(Exception ex){
            Log.e("metadata failed to read %s with %s", this.getImageFile().getName(), ExceptionHelper.getStackTrace(ex));
        }

        return this.metadata;
    }

    public Date getDateCreated() {

        if (this.createDate != null) {
            return this.createDate;
        }

        if (this.getMetadata() == null) {
            return null;
        }

        Directory directory = this.getMetadata().getDirectory(ExifDirectory.class);
        if (directory == null) {
            Log.e("unable to retrieve directory from %s", this.getImageFile().getName());
            return null;
        }

        try {
            if (directory.containsTag(ExifDirectory.TAG_DATETIME_ORIGINAL)) {
                this.setCreateDate(directory.getDate(ExifDirectory.TAG_DATETIME_ORIGINAL));
            } else if (directory.containsTag(ExifDirectory.TAG_DATETIME)) {
                this.setCreateDate(directory.getDate(ExifDirectory.TAG_DATETIME));
            } else if (directory.containsTag(ExifDirectory.TAG_DATETIME_DIGITIZED)) {
                this.setCreateDate(directory.getDate(ExifDirectory.TAG_DATETIME_DIGITIZED));
            }
        } catch (MetadataException mdx) {
            Log.e ("failed to load meta data on %s because %s", this.getImageFile().getName(), mdx.getMessage());
        }

        return this.createDate;
    }
}