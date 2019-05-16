import json
from shapely.geometry import Point
from shapely.geometry.polygon import Polygon

#Input files: District file and output1.json
#Output file: out2put.json with demographic data for each precinct AND what district they're in

output = open('output2.json','w')
districtCoordinates = []
with open('NHDistrict.json','r') as f:
	jsonData = json.load(f)
	features = jsonData['features']

	for line in features:
		coordinates = line['geometry']['coordinates']

		if(line['geometry']['type'] == 'MultiPolygon'):
			kap = [tuple(l) for l in coordinates[0][0]]
		else:
			kap = [tuple(l) for l in coordinates[0]]
		districtCoordinates.append(kap)
# print(districtCoordinates[0])
district1 = Polygon(districtCoordinates[0])
district2 = Polygon(districtCoordinates[1])
counter = 0 

district1Counter = 0
district2Counter = 0
with open('output1.json','r') as f:
	jsonData = json.load(f)
	features = jsonData['features']

	for line in features:
		x = line['properties']['INTPTLON_1']
		y = line['properties']['INTPTLAT_1']
		l = tuple([x,y])
		point = Point(l)

		if(line['geometry']['type'] == 'MultiPolygon'):
			
			
			if(point.within(district1)):
				features[counter]['properties']['District'] = 1	
				district1Counter += 1
			elif(point.within(district2)):
				features[counter]['properties']['District'] = 2
				district2Counter += 1
			else:
				# coordinates = line['geometry']['coordinates']
				# for x in coordinates[0][0]:
				# 	tupleCoordinate = tuple(x)
				# 	point = Point(tupleCoordinate)
				# 	if(point.within(district1)):
				# 		features[counter]['properties']['District'] = 1	
				# 		district1Counter += 1
				# 		break
				# 	elif(point.within(district2)):
				# 		features[counter]['properties']['District'] = 2
				# 		district2Counter += 1
				# 		break
				# 	print("Looking")
				features[counter]['properties']['District'] = 1	
				district1Counter += 1

				print(counter)
				print(point)

		else:
			if(point.within(district1)):
				features[counter]['properties']['District'] = 1	
				district1Counter += 1
			elif(point.within(district2)):
				features[counter]['properties']['District'] = 2
				district2Counter += 1
			else:
				# coordinates = line['geometry']['coordinates']
				# for x in coordinates[0]:
				# 	tupleCoordinate = tuple(x)
				# 	point = Point(tupleCoordinate)
				# 	if(point.within(district1)):
				# 		district1Counter += 1
				# 		features[counter]['properties']['District'] = 1	
				# 		break
				# 	elif(point.within(district2)):
				# 		district2Counter += 1
				# 		features[counter]['properties']['District'] = 2
				# 		break
				# 	print("Looking")
				features[counter]['properties']['District'] = 1	
				district1Counter += 1

				print(counter)
				print(point)
		counter += 1
		#print (counter)
	print(district1Counter)
	print(district2Counter)
	json.dump(jsonData, output)




			