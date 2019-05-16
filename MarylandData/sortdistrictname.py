import json
import operator 

output = open('Maryland1sorted.json','w')

with open('Maryland1.json','r') as f:
	jsonData = json.load(f)
	jsonData.sort(key=operator.itemgetter('NAME'))
	json.dump(jsonData)