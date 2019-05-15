import json 
import geopandas
from collections import defaultdict
from shapely.strtree import STRtree


output = open('output9.json','w')
state = geopandas.read_file('output1.json')

centroids_to_row = {}
for index, row in state.iterrows():
	centroids_to_row[row.geometry.centroid.wkt] = row

neighbors = defaultdict(list)
tree = STRtree(state.geometry)
for index,row in state.iterrows():
	precinct_dp = row['OID_']
	for polygon in tree.query(row.geometry):
		if row.geometry.touches(polygon):
			neighbors_dp = centroids_to_row[polygon.centroid.wkt]['OID_']
			neighbors[precinct_dp].append(neighbors_dp)


with open('output1.json','r+') as f:
	jsonData = json.load(f)
	features = jsonData['features']
	i = 0
	for line in features:
		line['properties']['neighbors'] = neighbors[i]
		i += 1
	json.dump(jsonData,output)

# with open('pls.json','w') as f:
# 	json.dump(neighbors, f, sort_keys=True, indent = 4)