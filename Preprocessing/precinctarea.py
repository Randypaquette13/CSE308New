from shapely.geometry import MultiPolygon
from shapely.geometry import shape
import fiona

f = open("output.txt",'w')
Multi = MultiPolygon([shape(pol['geometry']) for pol in fiona.open('az_final.shp')]) 
Polygons = list(Multi)

counter = 0
for i in Polygons:
	counter += 1
	f.write("%d %s \n" % (counter, str(i.area)) )
