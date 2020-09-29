# WatermarkRemover
Original Author: Matthew Crabtree  
A program to bulk remove watermarks from various image sharing platforms.  
  
Running the program will search for folders called \Uncropped and \Cropped within the working directory.  
If those folders do not exist, it will create them.  
Anything in Uncropped that is a supported filed type: {".jpg", ".gif", ".png", ".bmp", ".webmp", ".jpeg"} will be cropped  
and placed into the Cropped folder.  
  
NOTE: None of the original files within either folder will be affected.
