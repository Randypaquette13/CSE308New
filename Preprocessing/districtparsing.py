f=open("AZdistrictdemographic.csv", "r")
file = f.readlines()

districtlist = []
for line in file:
	if '5001500' in line:
		districtInfo = line.rstrip('\n').split(',')
		#important indices are 0,4,6,8,10,12,14,16,18
		#id, total, white, black, american indian, asian, native Hawaiian, other, 2 or more races
		infolist = []
		geoID = districtInfo[0]
		total = float(districtInfo[4])
		infolist.append(geoID[-1])
		infolist.append(total)
		infolist.append(float(districtInfo[6]) / total * 100)
		infolist.append(float(districtInfo[8]) / total * 100)
		infolist.append(float(districtInfo[10]) / total * 100)
		infolist.append(float(districtInfo[12]) / total * 100)
		infolist.append(float(districtInfo[14]) / total * 100)
		infolist.append(float(districtInfo[16]) / total * 100)
		infolist.append(float(districtInfo[18]) / total * 100)

		districtlist.append(infolist)

		
f.close()
print (districtlist)




