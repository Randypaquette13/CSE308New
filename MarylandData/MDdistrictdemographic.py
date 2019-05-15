import json

#Input files: precinct with everything and District data
#Output file: District data with demographic data

output = open('districtdata.json','w')
d1Total,d1HispanicLatino,d1White,d1Black,d1AIAN,d1Asian,d1NHOPI = 0,0,0,0,0,0,0
d2Total,d2HispanicLatino,d2White,d2Black,d2AIAN,d2Asian,d2NHOPI = 0,0,0,0,0,0,0
d3Total,d3HispanicLatino,d3White,d3Black,d3AIAN,d3Asian,d3NHOPI = 0,0,0,0,0,0,0
d4Total,d4HispanicLatino,d4White,d4Black,d4AIAN,d4Asian,d4NHOPI = 0,0,0,0,0,0,0
d5Total,d5HispanicLatino,d5White,d5Black,d5AIAN,d5Asian,d5NHOPI = 0,0,0,0,0,0,0
d6Total,d6HispanicLatino,d6White,d6Black,d6AIAN,d6Asian,d6NHOPI = 0,0,0,0,0,0,0
d7Total,d7HispanicLatino,d7White,d7Black,d7AIAN,d7Asian,d7NHOPI = 0,0,0,0,0,0,0
d8Total,d8HispanicLatino,d8White,d8Black,d8AIAN,d8Asian,d8NHOPI = 0,0,0,0,0,0,0

counter = 0
with open('output9.json','r') as f:
	jsonData = json.load(f)
	features = jsonData['features']

	for line in features:
		if(line['properties']['District'] == 1):
			print(d1Total)
			d1Total += line['properties']['Total']
			d1HispanicLatino += line['properties']['Hispanic/Latino']
			d1White += line['properties']['White']
			d1Black += line['properties']['Black']
			d1AIAN += line['properties']['AI/AN']
			d1Asian += line['properties']['Asian']
			d1NHOPI += line['properties']['NH/OPI']
		elif(line['properties']['District'] == 2):
			d2Total += line['properties']['Total']
			d2HispanicLatino += line['properties']['Hispanic/Latino']
			d2White += line['properties']['White']
			d2Black += line['properties']['Black']
			d2AIAN += line['properties']['AI/AN']
			d2Asian += line['properties']['Asian']
			d2NHOPI += line['properties']['NH/OPI']
		elif(line['properties']['District'] == 3):
			d3Total += line['properties']['Total']
			d3HispanicLatino += line['properties']['Hispanic/Latino']
			d3White += line['properties']['White']
			d3Black += line['properties']['Black']
			d3AIAN += line['properties']['AI/AN']
			d3Asian += line['properties']['Asian']
			d3NHOPI += line['properties']['NH/OPI']
		elif(line['properties']['District'] == 4):
			d4Total += line['properties']['Total']
			d4HispanicLatino += line['properties']['Hispanic/Latino']
			d4White += line['properties']['White']
			d4Black += line['properties']['Black']
			d4AIAN += line['properties']['AI/AN']
			d4Asian += line['properties']['Asian']
			d4NHOPI += line['properties']['NH/OPI']
		elif(line['properties']['District'] == 5):
			d5Total += line['properties']['Total']
			d5HispanicLatino += line['properties']['Hispanic/Latino']
			d5White += line['properties']['White']
			d5Black += line['properties']['Black']
			d5AIAN += line['properties']['AI/AN']
			d5Asian += line['properties']['Asian']
			d5NHOPI += line['properties']['NH/OPI']
		elif(line['properties']['District'] == 6):
			d6Total += line['properties']['Total']
			d6HispanicLatino += line['properties']['Hispanic/Latino']
			d6White += line['properties']['White']
			d6Black += line['properties']['Black']
			d6AIAN += line['properties']['AI/AN']
			d6Asian += line['properties']['Asian']
			d6NHOPI += line['properties']['NH/OPI']
		elif(line['properties']['District'] == 7):
			d7Total += line['properties']['Total']
			d7HispanicLatino += line['properties']['Hispanic/Latino']
			d7White += line['properties']['White']
			d7Black += line['properties']['Black']
			d7AIAN += line['properties']['AI/AN']
			d7Asian += line['properties']['Asian']
			d7NHOPI += line['properties']['NH/OPI']
		elif(line['properties']['District'] == 8):
			d8Total += line['properties']['Total']
			d8HispanicLatino += line['properties']['Hispanic/Latino']
			d8White += line['properties']['White']
			d8Black += line['properties']['Black']
			d8AIAN += line['properties']['AI/AN']
			d8Asian += line['properties']['Asian']
			d8NHOPI += line['properties']['NH/OPI']

		else:
			print("wtf")

	with open('MD-Full.json','r+') as f2:
		districtData = json.load(f2)
		districtFeature = districtData['features']

		districtFeature[0]['properties']['Total'] = d1Total
		districtFeature[0]['properties']['Hispanic/Latino'] = d1HispanicLatino
		districtFeature[0]['properties']['White'] = d1White
		districtFeature[0]['properties']['Black'] = d1Black
		districtFeature[0]['properties']['AI/AN'] = d1AIAN
		districtFeature[0]['properties']['Asian'] = d1Asian
		districtFeature[0]['properties']['NH/OPI'] = d1NHOPI

		districtFeature[1]['properties']['Total'] = d2Total
		districtFeature[1]['properties']['Hispanic/Latino'] = d2HispanicLatino
		districtFeature[1]['properties']['White'] = d2White
		districtFeature[1]['properties']['Black'] = d2Black
		districtFeature[1]['properties']['AI/AN'] = d2AIAN
		districtFeature[1]['properties']['Asian'] = d2Asian
		districtFeature[1]['properties']['NH/OPI'] = d2NHOPI

		districtFeature[2]['properties']['Total'] = d3Total
		districtFeature[2]['properties']['Hispanic/Latino'] = d3HispanicLatino
		districtFeature[2]['properties']['White'] = d3White
		districtFeature[2]['properties']['Black'] = d3Black
		districtFeature[2]['properties']['AI/AN'] = d3AIAN
		districtFeature[2]['properties']['Asian'] = d3Asian
		districtFeature[2]['properties']['NH/OPI'] = d3NHOPI

		districtFeature[3]['properties']['Total'] = d4Total
		districtFeature[3]['properties']['Hispanic/Latino'] = d4HispanicLatino
		districtFeature[3]['properties']['White'] = d4White
		districtFeature[3]['properties']['Black'] = d4Black
		districtFeature[3]['properties']['AI/AN'] = d4AIAN
		districtFeature[3]['properties']['Asian'] = d4Asian
		districtFeature[3]['properties']['NH/OPI'] = d4NHOPI

		districtFeature[4]['properties']['Total'] = d5Total
		districtFeature[4]['properties']['Hispanic/Latino'] = d5HispanicLatino
		districtFeature[4]['properties']['White'] = d5White
		districtFeature[4]['properties']['Black'] = d5Black
		districtFeature[4]['properties']['AI/AN'] = d5AIAN
		districtFeature[4]['properties']['Asian'] = d5Asian
		districtFeature[4]['properties']['NH/OPI'] = d5NHOPI

		districtFeature[5]['properties']['Total'] = d6Total
		districtFeature[5]['properties']['Hispanic/Latino'] = d6HispanicLatino
		districtFeature[5]['properties']['White'] = d6White
		districtFeature[5]['properties']['Black'] = d6Black
		districtFeature[5]['properties']['AI/AN'] = d6AIAN
		districtFeature[5]['properties']['Asian'] = d6Asian
		districtFeature[5]['properties']['NH/OPI'] = d6NHOPI

		districtFeature[6]['properties']['Total'] = d7Total
		districtFeature[6]['properties']['Hispanic/Latino'] = d7HispanicLatino
		districtFeature[6]['properties']['White'] = d7White
		districtFeature[6]['properties']['Black'] = d7Black
		districtFeature[6]['properties']['AI/AN'] = d7AIAN
		districtFeature[6]['properties']['Asian'] = d7Asian
		districtFeature[6]['properties']['NH/OPI'] = d7NHOPI

		districtFeature[7]['properties']['Total'] = d8Total
		districtFeature[7]['properties']['Hispanic/Latino'] = d8HispanicLatino
		districtFeature[7]['properties']['White'] = d8White
		districtFeature[7]['properties']['Black'] = d8Black
		districtFeature[7]['properties']['AI/AN'] = d8AIAN
		districtFeature[7]['properties']['Asian'] = d8Asian
		districtFeature[7]['properties']['NH/OPI'] = d8NHOPI

	json.dump(districtData, output)

