import sys
import os

# SOLR

folder= sys.argv[1]
collection= sys.argv[2]

print folder
print collection

#Start solr
os.system("./bin/solr start")
print "Solar is running ... "
	
#Create collection of documents
os.system("./bin/solr create -c "+ collection)

#Index Documents
os.system("./bin/post -c "+  collection+ " "+ path)

#Close solr
os.system("./bin/solr stop -all")
		

