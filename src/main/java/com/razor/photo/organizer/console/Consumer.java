package com.razor.photo.organizer.console;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.MetadataException;


public class Consumer implements Runnable{

    private int consumed = 0;
	private ProductionLine line = null;
	private boolean consumptionComplete = false;
	
	public Consumer(ProductionLine productionLine){
		this.consumptionComplete = false;
		this.line = productionLine;
	}
	
	public void consume() throws Exception{

		while(!consumptionComplete()){

			// sync on the production line
			synchronized(line){
				
				// wait until the line has a product to consume

				while(!line.getProducer().productionStopped() && !consumptionComplete() && !line.hasWorkItems()){
					Log.i("waiting on production line to receive products");
					line.wait();
				}
				
				// remove the product from the line (if one available)
                // if not, drop out of loop

                Product product = line.removeWorkItem();
				if(product==null){
					Log.w("retrieved empty product from the line! Time to stop");
                    return;
				}

                Log.i("removed %s from the line", product.getImageFile().getName());
                Log.i("notify line listener line about to become free with %d items left", line.workItemsOnLine());

                // consume the product

                if ( product.getDateCreated() == null ){
                    Log.w("unable to retrieve date from %s", product.getImageFile().getName());
                    this.copyToUnorderedFolder(product);
                } else {
                    this.copyToOrderedFolder(product);
                }

                this.consumed ++;

				line.notifyAll();
			}
		}
	}
	
	public void startConsuming(){
		Log.i("consumption begins");
		this.consumptionComplete = false;
	}
	
	public void stopConsuming(){
		Log.i("consumption has been completed");
		this.consumptionComplete = true;
		this.line.removeConsumer(this);
	}
	
	public boolean consumptionComplete(){
		return this.consumptionComplete;
	}
	
	private void copyToUnorderedFolder(Product product) {
		if(product!=null && product.getImageFile()!=null && product.getImageFile().getName()!=null){
			try{
		        String newAbsolutePath = String.format("%s%s%s",
                        this.line.getUnsortedDestinationFolder(),
                        File.separator,
                        product.getImageFile().getName());

		        File newFile = new File(newAbsolutePath);
		        if(newFile.canRead()){
		        	Log.w("%s already existed", newFile.getAbsolutePath());
		        	return;
		        }        
		    	FileUtils.copyFile(product.getImageFile(), newFile);
		    	Log.i("copy %s to %s", product.getImageFile().getAbsolutePath(), newFile.getAbsolutePath());
			}catch(Exception ex){
				Log.e("failed to store unreadable image %s in storage folder %s", product.getImageFile().getName(), this.line.getUnsortedDestinationFolder());
			}
		}
	}

    private void copyToOrderedFolder(Product product) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(product.getDateCreated());

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DATE);


        String newDateBasedFolderName = String.format("%s%s%d%s%d%s%02d",
                this.line.getRootDestinationFolder(),
                File.separator,
                year,
                File.separator,
                (month+1),
                File.separator,
                dayOfMonth);

        String newAbsolutePath = String.format("%s%s%s",
                newDateBasedFolderName,
                File.separator,
                product.getImageFile().getName());

        // create the directory if it doesn't already exist

        File newDateBasedFolder = new File(newDateBasedFolderName);
        newDateBasedFolder.mkdir();

        // check if file already exists

        File newFile = new File(newAbsolutePath);
        if(newFile.canRead()){
            Log.w("%s already existed", newFile.getAbsolutePath());
            return;
        }

        // move file

        try {
            FileUtils.copyFile(product.getImageFile(), newFile);
            Log.i("copy %s to %s", product.getImageFile().getAbsolutePath(), newFile.getAbsolutePath());
        } catch (IOException ex) {
            Log.e("unable to copy %s to %s", product.getImageFile().getAbsolutePath(), newFile.getAbsolutePath());
        }

    }

	@Override
	public void run() {
		try{
			this.startConsuming();
			this.consume();
		}catch(Exception ignore){
			Log.e("%s caused thread to stop", ExceptionHelper.getStackTrace(ignore));
		}finally{
			this.stopConsuming();
			Log.a("dropped out of thread after consuming %d products", this.consumed);
		}
	}
	
	
}
