f=open("AZdistrictdemographic.csv", "r")
file = f.readlines()

infolist = []
for line in file:
	if '5001500' in line:
		districtInfo = line.rstrip('\n').split(',')
		#important indices are 0,4,6,8,10,12,14,16,18
		#id, white, black, american indian, asian, native Hawaiian, other, 2 or more races
		d = []
		geoID = districtInfo[0]
		d.append(geoID[-1])
		d.append(districtInfo[4])
		d.append(districtInfo[6])
		d.append(districtInfo[8])
		d.append(districtInfo[10])
		d.append(districtInfo[12])
		d.append(districtInfo[14])
		d.append(districtInfo[16])
		d.append(districtInfo[18])

		infolist.append(d)

		
f.close()
print(infolist)





