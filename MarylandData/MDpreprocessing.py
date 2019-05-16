import json
import random

#Input files: precinct json and demographic data
#Output: out1put.json with demographic data for each precinct.
counter = 1
demographicsCounter = 0
countyDict = {}
repeatCounty = []
census = open('MDprecinctdemographic.csv','r')
output = open('output1.json','w')
next(census)
next(census)
demoData = census.readlines()

with open('Maryland.json','r+') as f:
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
		geoid = precinctProperty['COUNTY']
		district = geoid[2:5]

		if(district == '001'):
			precinctProperty['COUNTY'] = 'Allegany'
		elif(district == '003'):
			precinctProperty['COUNTY'] = 'Anne Arundel'
		elif(district == '005'):
			precinctProperty['COUNTY'] = 'Baltimore'
		elif(district == '009'):
			precinctProperty['COUNTY'] = 'Calvert'
		elif(district == '011'):
			precinctProperty['COUNTY'] = 'Caroline'
		elif(district == '013'):
			precinctProperty['COUNTY'] = 'Carroll'
		elif(district == '015'):
			precinctProperty['COUNTY'] = 'Cecil'
		elif(district == '017'):
			precinctProperty['COUNTY'] = 'Charles'
		elif(district == '019'):
			precinctProperty['COUNTY'] = 'Dorchester'
		elif(district == '021'):
			precinctProperty['COUNTY'] = 'Frederick'
		elif(district == '023'):
			precinctProperty['COUNTY'] = 'Garrett'
		elif(district == '025'):
			precinctProperty['COUNTY'] = 'Harford'
		elif(district == '027'):
			precinctProperty['COUNTY'] = 'Howard'
		elif(district == '029'):
			precinctProperty['COUNTY'] = 'Kent'
		elif(district == '031'):
			precinctProperty['COUNTY'] = 'Montgomery'
		elif(district == '033'):
			precinctProperty['COUNTY'] = 'Prince George\'s'
		elif(district == '035'):
			precinctProperty['COUNTY'] = 'Queen Anne\'s'
		elif(district == '037'):
			precinctProperty['COUNTY'] = 'St. Mary\'s'
		elif(district == '039'):
			precinctProperty['COUNTY'] = 'Somerset'
		elif(district == '041'):
			precinctProperty['COUNTY'] = 'Talbot'
		elif(district == '043'):
			precinctProperty['COUNTY'] = 'Washington'
		elif(district == '045'):
			precinctProperty['COUNTY'] = 'Wilcomico'
		elif(district == '047'):
			precinctProperty['COUNTY'] = 'Worcester'
		elif(district == '045'):
			precinctProperty['COUNTY'] = 'Wilcomico'
		elif(district == '045'):
			precinctProperty['COUNTY'] = 'Wilcomico'
		elif(district == '510'):
			precinctProperty['COUNTY'] = 'Baltimore'

		
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

	for i in sorted(map(lambda x: x['properties']['COUNTY'], features)): 
		if(i not in countyDict):
			counter = 1
		countyDict[i] = counter
		counter += 1
	total = 0
	for i in countyDict:
		total += countyDict[i]
		print(i, countyDict[i])
	print('The total number of precincts is: ',total)


	while(demographicsCounter != total):
		features = jsonData['features']
		precinctProperty = features[demographicsCounter]['properties']
		l = random.choice(repeatCounty)

		geoid = precinctProperty['COUNTY']
		district = geoid[2:5]
		if(district == '001'):
			precinctProperty['COUNTY'] = 'Allegany'
		elif(district == '003'):
			precinctProperty['COUNTY'] = 'Anne Arundel'
		elif(district == '005'):
			precinctProperty['COUNTY'] = 'Baltimore'
		elif(district == '009'):
			precinctProperty['COUNTY'] = 'Calvert'
		elif(district == '011'):
			precinctProperty['COUNTY'] = 'Caroline'
		elif(district == '013'):
			precinctProperty['COUNTY'] = 'Carroll'
		elif(district == '015'):
			precinctProperty['COUNTY'] = 'Cecil'
		elif(district == '017'):
			precinctProperty['COUNTY'] = 'Charles'
		elif(district == '019'):
			precinctProperty['COUNTY'] = 'Dorchester'
		elif(district == '021'):
			precinctProperty['COUNTY'] = 'Frederick'
		elif(district == '023'):
			precinctProperty['COUNTY'] = 'Garrett'
		elif(district == '025'):
			precinctProperty['COUNTY'] = 'Harford'
		elif(district == '027'):
			precinctProperty['COUNTY'] = 'Howard'
		elif(district == '029'):
			precinctProperty['COUNTY'] = 'Kent'
		elif(district == '031'):
			precinctProperty['COUNTY'] = 'Montgomery'
		elif(district == '033'):
			precinctProperty['COUNTY'] = 'Prince George\'s'
		elif(district == '035'):
			precinctProperty['COUNTY'] = 'Queen Anne\'s'
		elif(district == '037'):
			precinctProperty['COUNTY'] = 'St. Mary\'s'
		elif(district == '039'):
			precinctProperty['COUNTY'] = 'Somerset'
		elif(district == '041'):
			precinctProperty['COUNTY'] = 'Talbot'
		elif(district == '043'):
			precinctProperty['COUNTY'] = 'Washington'
		elif(district == '045'):
			precinctProperty['COUNTY'] = 'Wilcomico'
		elif(district == '047'):
			precinctProperty['COUNTY'] = 'Worcester'
		elif(district == '045'):
			precinctProperty['COUNTY'] = 'Wilcomico'
		elif(district == '045'):
			precinctProperty['COUNTY'] = 'Wilcomico'
		elif(district == '510'):
			precinctProperty['COUNTY'] = 'Baltimore'

		precinctProperty['Total'] = l[0]
		precinctProperty['White'] = l[1]
		precinctProperty['Black'] = l[2]
		precinctProperty['AI/AN'] = l[3]
		precinctProperty['Asian'] = l[4]
		precinctProperty['NH/OPI'] = l[5]
		precinctProperty['Hispanic/Latino'] = l[6] 

		demographicsCounter += 1

	json.dump(jsonData, output)

