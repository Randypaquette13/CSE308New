import json
import random

#Input files: precinct json and demographic data
#Output: out1put.json with demographic data for each precinct.
counter = 1
demographicsCounter = 0
countyDict = {}
repeatCounty = []
census = open('NHprecinctdemographic.csv','r')
output = open('output1.json','w')
next(census)
next(census)
demoData = census.readlines()

with open('New_Hampshire.json','r+') as f:
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
		precinctProperty['Total'] = total
		precinctProperty['Hispanic/Latino'] = totalHispanicLatino
		precinctProperty['White'] = totalWhite
		precinctProperty['Black'] = totalBlack
		precinctProperty['AI/AN'] = totalAIAN
		precinctProperty['Asian'] = totalAsian
		precinctProperty['NH/OPI'] = totalNHOPI
		county = data[3].split()
		countyName = county[0]
		
		# while(features[i][properties]['COUNTY'] != countyName):
		# 	i += 1 
		# 	precinctNumber = features[i]
		repeatData = [total,totalWhite,totalBlack,totalAIAN,totalAsian,totalNHOPI,totalHispanicLatino]

		#if the data is 0, change it to 
		# if(total == 0):
		# 	repeatData

		repeatCounty.append(repeatData)
		demographicsCounter += 1

	# for i in repeatCounty:
	# 	print(i)

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
		precinctProperty['Total'] = l[0]
		precinctProperty['White'] = l[1]
		precinctProperty['Black'] = l[2]
		precinctProperty['AI/AN'] = l[3]
		precinctProperty['Asian'] = l[4]
		precinctProperty['NH/OPI'] = l[5]
		precinctProperty['Hispanic/Latino'] = l[6] 

		demographicsCounter += 1
	json.dump(jsonData, output)

