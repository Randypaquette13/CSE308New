var _0x5f42=['eachLayer','neighbors','keys','random','abs','latLng','mapid','setView','tileLayer','https://cartodb-basemaps-{s}.global.ssl.fastly.net/dark_nolabels/{z}/{x}/{y}.png','&copy;\x20<a\x20href=\x22http://www.openstreetmap.org/copyright\x22>OpenStreetMap</a>','addTo','#888888','#e6194b','#ffe119','#0082c8','#f58231','#911eb4','#46f0f0','#f032e6','#d2f53c','#008080','#e6beff','#aa6e28','remove','getLayers','resetStyle','onAdd','_div','DomUtil','create','div','info','updatePrecinct','<h5>','toFixed','%</h5>','innerHTML','<h4>Precinct\x20information</h4>','</h5>','<h5>Population:\x20','pop','<h5>District:\x20','<h5>Neighbor\x20count:\x20','length','<hr><h4>District\x20Populations</h4>','Hover\x20over\x20a\x20voting\x20precinct','feature','splice','indexOf','push','target','setStyle','properties','forEach','#d3d3d3','Browser','opera','edge','geoJSON'];(function(_0x1cb816,_0x52196e){var _0x27ba7d=function(_0x54f127){while(--_0x54f127){_0x1cb816['push'](_0x1cb816['shift']());}};_0x27ba7d(++_0x52196e);}(_0x5f42,0x7d));var _0x17d8=function(_0x4e4830,_0x17bee5){_0x4e4830=_0x4e4830-0x0;var _0x22bac9=_0x5f42[_0x4e4830];return _0x22bac9;};bounds=L['latLngBounds'](L[_0x17d8('0x0')](0x4b,-0xc8),L[_0x17d8('0x0')](0xa,-0x32));var myMap=L['map'](_0x17d8('0x1'),{'attributionControl':![],'maxBounds':bounds,'minZoom':0x4})[_0x17d8('0x2')]([39.756214,-100.994031],0x4);L[_0x17d8('0x3')](_0x17d8('0x4'),{'maxZoom':0x12,'attribution':_0x17d8('0x5')})[_0x17d8('0x6')](myMap);colors=[_0x17d8('0x7'),_0x17d8('0x8'),'#3cb44b',_0x17d8('0x9'),_0x17d8('0xa'),_0x17d8('0xb'),_0x17d8('0xc'),_0x17d8('0xd'),_0x17d8('0xe'),_0x17d8('0xf'),'#fabebe',_0x17d8('0x10'),_0x17d8('0x11'),_0x17d8('0x12')];activeLayer=null;function clearActiveLayer(){if(activeLayer!=null){activeLayer[_0x17d8('0x13')]();activeLayer=null;}}function redraw(){var _0x4ac247=geojson[_0x17d8('0x14')]();for(var _0x30a658=0x0;_0x30a658<_0x4ac247['length'];_0x30a658++){geojson[_0x17d8('0x15')](_0x4ac247[_0x30a658]);}}precincts={};neighbors={};districts={};district_precincts=[[],[],[],[],[],[],[],[],[],[],[],[],[],[]];district_populations=[0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0];total_pop=0x0;var info=L['control']();info[_0x17d8('0x16')]=function(_0x60dbfc){this[_0x17d8('0x17')]=L[_0x17d8('0x18')][_0x17d8('0x19')](_0x17d8('0x1a'),_0x17d8('0x1b'));this[_0x17d8('0x1c')]();return this[_0x17d8('0x17')];};function districtStatusString(){var _0x5c2364='';for(var _0x48563c=0x1;_0x48563c<0xe;_0x48563c++){_0x5c2364+=_0x17d8('0x1d')+_0x48563c+':\x20'+(district_populations[_0x48563c]/total_pop*0x64)[_0x17d8('0x1e')](0x2)+_0x17d8('0x1f');}return _0x5c2364;}info[_0x17d8('0x1c')]=function(_0x56d36a){this[_0x17d8('0x17')][_0x17d8('0x20')]=_0x17d8('0x21')+(_0x56d36a?_0x17d8('0x1d')+_0x56d36a['ID']+_0x17d8('0x22')+_0x17d8('0x23')+_0x56d36a[_0x17d8('0x24')]+_0x17d8('0x1d')+_0x17d8('0x25')+districts[_0x56d36a['ID']]+_0x17d8('0x1d')+_0x17d8('0x26')+neighbors[_0x56d36a['ID']][_0x17d8('0x27')]+_0x17d8('0x1d')+_0x17d8('0x28')+districtStatusString():_0x17d8('0x29'));};info[_0x17d8('0x6')](myMap);function move(_0x3aae89,_0xb2be63){var _0x6e9de5=precincts[_0xb2be63][_0x17d8('0x2a')];var _0x4adf88=districts[_0xb2be63];var _0x1de80f=_0x6e9de5['properties'][_0x17d8('0x24')];district_populations[_0x4adf88]-=_0x1de80f;district_populations[_0x3aae89]+=_0x1de80f;districts[_0xb2be63]=_0x3aae89;district_precincts[_0x4adf88][_0x17d8('0x2b')](district_precincts[_0x4adf88][_0x17d8('0x2c')](_0xb2be63),0x1);district_precincts[_0x3aae89][_0x17d8('0x2d')](_0xb2be63);}function highlightPrecinct(_0x2aaf40){var _0x41a115=_0x2aaf40[_0x17d8('0x2e')];_0x41a115[_0x17d8('0x2f')]({'weight':0x5,'color':'#fd7'});getNeighbors(_0x41a115[_0x17d8('0x2a')][_0x17d8('0x30')]['ID'])[_0x17d8('0x31')](function(_0x52ed8b){precincts[_0x52ed8b][_0x17d8('0x2f')]({'weight':0x4,'color':_0x17d8('0x32')});});if(!L['Browser']['ie']&&!L[_0x17d8('0x33')][_0x17d8('0x34')]&&!L['Browser'][_0x17d8('0x35')]){_0x41a115['bringToFront']();}info[_0x17d8('0x1c')](_0x41a115[_0x17d8('0x2a')][_0x17d8('0x30')]);}function resetPrecinctHighlight(_0x4eaa46){geojson[_0x17d8('0x15')](_0x4eaa46['target']);getNeighbors(_0x4eaa46['target'][_0x17d8('0x2a')][_0x17d8('0x30')]['ID'])[_0x17d8('0x31')](function(_0x33c26c){geojson['resetStyle'](precincts[_0x33c26c]);});info[_0x17d8('0x1c')]();}function loadNC(){clearActiveLayer();geojson=L[_0x17d8('0x36')](NCJSON,{'onEachFeature':onEachPrecinct,'style':precinctStyle});geojson[_0x17d8('0x37')](function(_0x26259c){precincts[_0x26259c[_0x17d8('0x2a')][_0x17d8('0x30')]['ID']]=_0x26259c;districts[_0x26259c[_0x17d8('0x2a')]['properties']['ID']]=0x0;neighbors[_0x26259c[_0x17d8('0x2a')][_0x17d8('0x30')]['ID']]=_0x26259c['feature'][_0x17d8('0x30')][_0x17d8('0x38')];total_pop+=_0x26259c[_0x17d8('0x2a')][_0x17d8('0x30')][_0x17d8('0x24')];});redraw();geojson['addTo'](myMap);activeLayer=geojson;myMap['fitBounds'](activeLayer['getBounds']());}function precinctStyle(_0x3e9a0e){return{'fillColor':colors[districts[_0x3e9a0e[_0x17d8('0x30')]['ID']]],'weight':0x1,'opacity':0x1,'color':colors[districts[_0x3e9a0e[_0x17d8('0x30')]['ID']]],'fillOpacity':0.5};}function onEachPrecinct(_0x1620d8,_0x4f0de2){_0x4f0de2['on']({'mouseover':highlightPrecinct,'mouseout':resetPrecinctHighlight});}loadNC();function getRandomUnassignedPrecinct(){var _0x59d059=Object[_0x17d8('0x39')](precincts);tr=_0x59d059[_0x59d059['length']*Math[_0x17d8('0x3a')]()<<0x0];while(districts[tr]!=0x0){tr=_0x59d059[_0x59d059[_0x17d8('0x27')]*Math[_0x17d8('0x3a')]()<<0x0];}return tr;}function getNeighbors(_0x18d250){return neighbors[_0x18d250];}function getDistrict(_0x47cae3){return districts[_0x47cae3];}function getPrecinctPop(_0x160b47){return precincts[_0x160b47][_0x17d8('0x2a')][_0x17d8('0x30')][_0x17d8('0x24')];}function getDistrictPop(_0x3f74ef){return district_populations[_0x3f74ef];}function getStatePop(){return total_pop;}function getDistrictPrecincts(_0x1da7c1){return district_precincts[_0x1da7c1];}function getDistrictQuality(_0x446fbc){var _0x56be5b=getStatePop()/0xd;return 0x1-Math[_0x17d8('0x3b')](getDistrictPop(_0x446fbc)-_0x56be5b)/getStatePop();}function done(){redraw();}