# Information Retrieval Multimedia System
   
The goal of this project is to implement of a basic CBIR using Lucene 

# Usage 

The project is divided in the following scripts according to the parts in which the assignment is divided (assignment.pdf)

## LuceneApplication

     - Modes: 1 (Indexer)
                    - dataDir: The directory path with the data to be indexed.
                    - indexDir: The directory path to store the indexed data.
             2 (Searcher)
                    - word: The word to search.
  
## OCRApplication 
       
     - dataDir: The directory path to read images.
     - outDir: The directory path to store the text in json format.

## ImageMetadataExtractor
    
     - dataDir: The directory path to read images.
     - outDir: The directory path to store the text in json format.

## ImageFeatureExtractor
    
     - dataDir: The directory path to read images.
     - outDir: The directory path to store the text in json format.

## SOLRApplication 
       
     - Modes: 1 (Indexing DICOM images)
              2 (Indexing OCR)

     - dataDir: The directory path with source data.
     - outDir: The directory path to store data.


# Project organization 

Folders: 

## LuceneApplication. Contains the source code to index text using Lucene 
     
## OCRApplication. Contains the source code to obtain text from images using tesseract

## FLickrImageExtraction. 
    / ImageMetadataExtractor. Contains the source code to extract metadata (using metadata-extractor)
    / ImageFeatureExtractor. Contains the source code to extract basic shape and color features from flickr images.

## DICOMImagesExtraction. 

## SOLRApplication. Contains the source code to integrate indexing of DICOM images and OCR using SOLR


