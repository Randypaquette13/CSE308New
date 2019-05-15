import json
from shapely.geometry import Point
from shapely.geometry.polygon import Polygon

#Input files: District file and output1.json
#Output file: out2put.json with demographic data for each precinct AND what district they're in

output = open('output2.json','w')
districtCoordinates = []
with open('AZ-FULL.json','r') as f:
	jsonData = json.load(f)
	features = jsonData['features']

	for line in features:
		coordinates = line['geometry']['coordinates']

		if(line['geometry']['type'] == 'MultiPolygon'):
			kap = [tuple(l) for l in coordinates[0][0]]
		else:
			kap = [tuple(l) for l in coordinates[0]]
		districtCoordinates.append(kap)
district1 = Polygon(districtCoordinates[0])
district2 = Polygon(districtCoordinates[1])
district3 = Polygon(districtCoordinates[2])
district4 = Polygon(districtCoordinates[3])
district5 = Polygon(districtCoordinates[4])
district6 = Polygon(districtCoordinates[5])
district7 = Polygon(districtCoordinates[6])
district8 = Polygon(districtCoordinates[7])
district9 = Polygon(districtCoordinates[8])
counter = 0 

district1Counter,district2Counter,district3Counter,district4Counter,district5Counter,district6Counter,district7Counter,district8Counter,district9Counter = 0,0,0,0,0,0,0,0,0

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
			elif(point.within(district3)):
				features[counter]['properties']['District'] = 3
				district3Counter += 1
			elif(point.within(district4)):
				features[counter]['properties']['District'] = 4
				district4Counter += 1
			elif(point.within(district5)):
				features[counter]['properties']['District'] = 5
				district5Counter += 1
			elif(point.within(district6)):
				features[counter]['properties']['District'] = 6
				district6Counter += 1
			elif(point.within(district7)):
				features[counter]['properties']['District'] = 7
				district7Counter += 1
			elif(point.within(district8)):
				features[counter]['properties']['District'] = 8
				district8Counter += 1
			elif(point.within(district9)):
				features[counter]['properties']['District'] = 9
				district9Counter += 1

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
			elif(point.within(district3)):
				features[counter]['properties']['District'] = 3
				district3Counter += 1
			elif(point.within(district4)):
				features[counter]['properties']['District'] = 4
				district4Counter += 1
			elif(point.within(district5)):
				features[counter]['properties']['District'] = 5
				district5Counter += 1
			elif(point.within(district6)):
				features[counter]['properties']['District'] = 6
				district6Counter += 1
			elif(point.within(district7)):
				features[counter]['properties']['District'] = 7
				district7Counter += 1
			elif(point.within(district8)):
				features[counter]['properties']['District'] = 8
				district8Counter += 1
			elif(point.within(district9)):
				features[counter]['properties']['District'] = 9
				district9Counter += 1

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
	print(district3Counter)
	print(district4Counter)
	print(district5Counter)
	print(district6Counter)
	print(district7Counter)
	print(district8Counter)
	print(district9Counter)

	json.dump(jsonData, output)




			