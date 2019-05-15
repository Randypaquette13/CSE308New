import json

output = open('districtdata.json','w')

d1Total,d1HispanicLatino,d1White,d1Black,d1AIAN,d1Asian,d1NHOPI = 0,0,0,0,0,0,0
d2Total,d2HispanicLatino,d2White,d2Black,d2AIAN,d2Asian,d2NHOPI = 0,0,0,0,0,0,0
counter = 0
with open('output2.json','r') as f:
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

		else:
			print("wtf")

	with open('NH-Full.json','r+') as f2:
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
	json.dump(districtData, output)

