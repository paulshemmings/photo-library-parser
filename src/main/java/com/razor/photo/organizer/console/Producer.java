package com.razor.photo.organizer.console;

import java.io.File;

public class Producer implements Runnable {



	private ProductionLine line = null;
	private boolean productionComplete = false;
    private int produced = 0;
	
	public Producer(ProductionLine line){
		this.line = line;
	}
	
	public void processFile(File imageFile) throws InterruptedException{
		synchronized(line){			
			// while production hasn't completed, wait for there to be no 
			// products on the line. i don't want to overload the line				
			while(!this.productionComplete && line.workItemsOnLine() > line.getMaxLineSize()){
				Log.i("waiting for production line to reduce to manageble size");
				line.wait();
			}
			
			// now there are few products on the line, i'm going to supply it with the next batch of files
			if(!this.productionComplete){					
				Product workItem = new Product(imageFile);
				line.addWorkItem(workItem);
                this.produced ++;
			}
			
			Log.i("notify consumers production line now %d products", line.workItemsOnLine());
			line.notifyAll();
		}		
	}
	
	public void processFolder(File directoryFolder) throws InterruptedException{
		File[] files = directoryFolder.listFiles();				
		for(File file:files){
			if(file.isDirectory()){
				this.processFolder(file);
			}else{
				this.processFile(file);
			}
		}		
		Log.i("production stopped");
	}
	
	public void startProduction(){
		Log.i("Production has been started");
		this.productionComplete = false;
	}	
	
	public void stopProduction(){
		Log.i("Production has been completed");
		this.productionComplete = true;
	}
	
	public boolean productionStopped(){
		return this.productionComplete;
	}

	@Override
	public void run() {
		try{
			this.startProduction();
			this.processFolder(new File(this.line.getPhotoPathFolder()));
		}catch(Exception ex){
			Log.e("production stopped with %s", ExceptionHelper.getStackTrace(ex));
		}finally{
			this.stopProduction();
            Log.a ("Production stopped after producing %d products", this.produced);
		}
	}
}
