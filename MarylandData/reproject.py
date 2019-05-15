import geopandas as gpd
import glob


file_name = "Precinct2010_83NadMeters.shp"
data = gpd.read_file(file_name, encoding="utf-8")
data = data.to_crs({'init': 'epsg:4326'})
data.to_file("resultx.shp", encoding="utf-8")

