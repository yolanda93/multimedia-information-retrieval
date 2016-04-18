# Information Retrieval Multimedia System
   
The goal of this project is to implement a basic CBIR using Lucene 

# Usage 

The project is divided in the following java applications according to the parts in which the assignment is divided (assignment.pdf)

## LuceneApplication
Application to index and search documents in Lucene. It has to modes as it is described below with different arguments.

     - Modes: 1 (Indexer)
                    - dataDir: The directory path with the data to be indexed.
                    - indexDir: The directory path to store the indexed data.
             2 (Searcher)
                    - indexDir: The directory path where is stored the indexed data. 
                    - word: The word to search.
  
## OCRApplication 
Application to obtain text from images using tesseract API. The extraction is optimized using layouts and indexed using LuceneApplication described above.

     - dataDir: The directory path to read images.
     - outDir: The directory path to store the text in json format.

## ImageMetadataExtractor
Application to extract metadata information from png images. The extracted metadata is stored using json format and indexed using LuceneApplication.

     - dataDir: The directory path to read images.
     - outDir: The directory path to store the text in json format.

## ImageFeatureExtractor
    
     - dataDir: The directory path to read images.
     - outDir: The directory path to store the text in json format.

## SOLRApplication 
     - collection: Collection name 
     - dataDir: The directory path with source data.

     Run config.py:
         python config.py dataDir collection

# Project organization 

Folders: 

```
/LuceneApplication. Contains the source code to index text using Lucene 
     
/OCRApplication. Contains the source code to obtain text from images using tesseract

/FLickrImageExtraction. 
    /ImageMetadataExtractor. Contains the source code to extract metadata (using metadata-extractor)
    /ImageFeatureExtractor. Contains the source code to extract basic shape and color features from flickr images.

/DICOMImagesExtraction. 

/SOLRApplication. Contains the source code to integrate indexing of DICOM images and OCR using SOLR

```
