import json
import random

#Input files: precinct json and demographic data
#Output: out1put.json with demographic data for each precinct.
counter = 1
demographicsCounter = 0
countyDict = {}
repeatCounty = []
census = open('AZprecinctdemographic.csv','r')
output = open('output1.json','w')
next(census)
next(census)
demoData = census.readlines()

with open('Arizona.json','r+') as f:
	jsonData = json.load(f)
	for line in demoData:
		data = line.split(',')

		total = int(data[5])
		totalHispanicLatino = int(data[16])
		totalWhite = int(data[7]) + int(data[17])
		totalBlack = int(data[8]) + int(data[18])
		totalAIAN = int(data[9]) + int(data[19])
		totalAsian = int(data[10]) + int(data[20])
		totalNHOPI = int(data[11]) +  int(data[21])

		features = jsonData['features']
		precinctProperty = features[demographicsCounter]['properties']
		#make into function later
		district = precinctProperty['COUNTY']
	

		if(district == 1):
			precinctProperty['COUNTY'] = 'Apache'
		elif(district == 3):
			precinctProperty['COUNTY'] = 'Cochise'
		elif(district == 5):
			precinctProperty['COUNTY'] = 'Coconino'
		elif(district == 7):
			precinctProperty['COUNTY'] = 'Gila'
		elif(district == 9):
			precinctProperty['COUNTY'] = 'Graham'
		elif(district == 11):
			precinctProperty['COUNTY'] = 'Greenlee'
		elif(district == 12):
			precinctProperty['COUNTY'] = 'La Paz'
		elif(district == 13):
			precinctProperty['COUNTY'] = 'Maricopa'
		elif(district == 15):
			precinctProperty['COUNTY'] = 'Mohave'
		elif(district == 17):
			precinctProperty['COUNTY'] = 'Navajo'
		elif(district == 19):
			precinctProperty['COUNTY'] = 'Pima'
		elif(district == 21):
			precinctProperty['COUNTY'] = 'Pinal'
		elif(district == 23):
			precinctProperty['COUNTY'] = 'Santa Cruz'
		elif(district == 25):
			precinctProperty['COUNTY'] = 'Yavapai'
		elif(district == 27):
			precinctProperty['COUNTY'] = 'Yuma'
		

		
		precinctProperty['Total'] = total
		precinctProperty['Hispanic/Latino'] = totalHispanicLatino
		precinctProperty['White'] = totalWhite
		precinctProperty['Black'] = totalBlack
		precinctProperty['AI/AN'] = totalAIAN
		precinctProperty['Asian'] = totalAsian
		precinctProperty['NH/OPI'] = totalNHOPI
		county = data[3].split()
		countyName = county[0]
	
		repeatData = [total,totalWhite,totalBlack,totalAIAN,totalAsian,totalNHOPI,totalHispanicLatino]
		repeatCounty.append(repeatData)
		demographicsCounter += 1

	# for i in sorted(map(lambda x: x['properties']['COUNTY'], features)): 
	# 	if(i not in countyDict):
	# 		counter = 1
	# 	countyDict[i] = counter
	# 	counter += 1
	# total = 0
	# for i in countyDict:
	# 	total += countyDict[i]
	# 	print(i, countyDict[i])
	# print('The total number of precincts is: ',total)

	total = 2224

	while(demographicsCounter != total):
		features = jsonData['features']
		precinctProperty = features[demographicsCounter]['properties']
		l = random.choice(repeatCounty)

		district = precinctProperty['COUNTY']

		if(district == 1):
			precinctProperty['COUNTY'] = 'Apache'
		elif(district == 3):
			precinctProperty['COUNTY'] = 'Cochise'
		elif(district == 5):
			precinctProperty['COUNTY'] = 'Coconino'
		elif(district == 7):
			precinctProperty['COUNTY'] = 'Gila'
		elif(district == 9):
			precinctProperty['COUNTY'] = 'Graham'
		elif(district == 11):
			precinctProperty['COUNTY'] = 'Greenlee'
		elif(district == 12):
			precinctProperty['COUNTY'] = 'La Paz'
		elif(district == 13):
			precinctProperty['COUNTY'] = 'Maricopa'
		elif(district == 15):
			precinctProperty['COUNTY'] = 'Mohave'
		elif(district == 17):
			precinctProperty['COUNTY'] = 'Navajo'
		elif(district == 19):
			precinctProperty['COUNTY'] = 'Pima'
		elif(district == 21):
			precinctProperty['COUNTY'] = 'Pinal'
		elif(district == 23):
			precinctProperty['COUNTY'] = 'Santa Cruz'
		elif(district == 25):
			precinctProperty['COUNTY'] = 'Yavapai'
		elif(district == 27):
			precinctProperty['COUNTY'] = 'Yuma'

		precinctProperty['Total'] = l[0]
		precinctProperty['White'] = l[1]
		precinctProperty['Black'] = l[2]
		precinctProperty['AI/AN'] = l[3]
		precinctProperty['Asian'] = l[4]
		precinctProperty['NH/OPI'] = l[5]
		precinctProperty['Hispanic/Latino'] = l[6] 

		demographicsCounter += 1

	json.dump(jsonData, output)

