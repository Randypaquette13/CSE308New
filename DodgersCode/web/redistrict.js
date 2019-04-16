// This is a simple algorithm tester.
// The default algorithm uses a simple graph partitioning
// technique to get a basic redistricting plan, then uses a
// simulated annealing algorithm to improve the result.

// The only variable here is population. Contiguity and compactness
// are not accounted for, for the sake of brevity.

// Feel free to experiment with the JS code below. It's 
// intentionally very simplified, and that means there are
// many improvements that you can make.

// To start, try implementing a simple contiguity check. 
// One very intuitive algorithm for this is as follows:
//	- Build a list of each neighbor N of P:
//  - Filter it so that each N must be in the same district as P (pre-move)
//	- Pick one N and carry out a breadth first search for all the others
//	- Return true immediately upon collecting every other N
//	- If this search fails, return false.

// Retrns true if a move leaves a precinct contiguous
function contiguity_check(from, precinct) {
	return true;
}

console.log("STARTING ALGORITHM");

// First - use a simple, fast algorithm to get a basic redistricting plan
precinct_neighbors = [[]];

// Start with 13 random precincts
for (var i = 1; i < 14; i++) {
	// Get a random precinct, add it to our district to start it off.
	var p = getRandomUnassignedPrecinct();
	move(i, p);
	precinct_neighbors.push([]);
	precinct_neighbors[i] = getNeighbors(p);
}

// Repeatedly go through each district and add all adjacent precincts.
var count = 0
var finished = false;
while (!finished) {
	finished = true;
	// Go through each precinct, and add all its neighbors
	for (var i = 1; i < 14; i++) {
		// Get a random precinct, add it to our district to start it off.
		var temp = [];
		precinct_neighbors[i].forEach(function(n){
			// Only consume unassigned neighbors
			if (getDistrict(n) == 0) {
				move(i, n);
				temp = temp.concat(getNeighbors(n));
				finished = false;
			}
		});
		precinct_neighbors[i] = temp;
	}
}

/// - Next: Run a very basic simulated annealing algorithm

//* You can delete the first '/' of this line to see what
// the result looks like without the simulated annealing 
// component.

// A function we'll use further down. Tests the usefulness of a move.
// As we're using simple measures here, it's easier to rate quality
// on a per - district basis and sum the two, given that no move
// will affect any uninvolved districts.
function testMove(to, from, precinct) {
	// Check for contiguity - you can implement this if you're feeling
	// ambitious. See my algorithm and function declaration above ^^^.
	if (!contiguity_check(from, precinct)) {
		return false;
	}
	// Get quality before the move
	var to_quality = getDistrictQuality(to);
	var from_quality = getDistrictQuality(from);
	// Make the move
	move(to, precinct);
	// Get quality after the move
	var new_to_quality = getDistrictQuality(to);
	var new_from_quality = getDistrictQuality(from);
	// If we accepted the move, return true.
	if (new_from_quality + new_to_quality > to_quality + from_quality) {
		return true
	}
	// If the move wasn't useful, undo it and return false.
	move(from, precinct);
	return false;
}


var foundMove = true;
var move_count = 0; // Lets you more easily debug infinite loops.
while (foundMove && move_count < 10000) {	
	// We exit if we can no longer make a move
	foundMove = false;
	// Get a starting district
	var start_district = -1;
	var worst_quality = 1.0;
	for (var i = 1; i < 14; i++) {
		var quality = getDistrictQuality(i);
		if (quality < worst_quality) {
			start_district = i;
			worst_quality = quality;
		}
	}
	// Sometimes we stop very early on, with suboptimal results. 
	// Why is that?
	// Check the console, look at the map. Notice anything?
	// How could we alleviate this problem?
	//	- A more exhaustive termination condition - check every district?
	// 	- Multiple runs - take the best result
	//	- A better initial generation algorithm - quality in, quality out
	console.log("Worst district: " + start_district);
	// Get each of its precincts 
	// (can optimize this by maintaining a list of border precincts and using that)
	var sd_precincts = getDistrictPrecincts(start_district).slice();
	for (var i = sd_precincts.length - 1; i >= 0; i--) {
		// Get each precinct's other-district neighbors
		var p1 = sd_precincts[i];
		var p1_neighbors = getNeighbors(p1);
		for (var j = p1_neighbors.length - 1; j >= 0; j--) {
			var p2 = p1_neighbors[j];
			var d1 = start_district;
			var d2 = getDistrict(p2);
			if (d1 != d2) {
				// Check both possible moves for each pair
				// To check a move, we do the following:
				// 1. Check whether it represents an 'improvement' in the state of affairs
				//	- In your projects, this will mean that the move increases the objective function.
				if (testMove(d2, d1, p1)) {
					// 2. If so, take the move and restart the loop.
					console.log("Made a move: " + p1 + " from " + d1 + " to " + d2);
					foundMove = true;
					move_count += 1;
					break;
				} else {
					// 3. If not, test the other move we can make with this part of the border. 
					if (testMove(d1, d2, p2)) {
						console.log("Made a move: " + p2 + " from " + d2 + " to " + d1);
						foundMove = true;
						move_count += 1;
						break;
					}
					//	- Keep going if neither works out.
				}
			}
		}
		if (foundMove == true) {
			break;
		}
	}

}
console.log(move_count + " moves made.");
//*/

done(); // Redraw the map to show our new redistricting plan

// Documentation:
// function move(d, p) - Set p's district to d
// function getRandomUnassignedPrecinct() - Returns a random precinct with district=0
// function getNeighbors(p) - Takes a precinct, returns an array of neighbor precincts
// function getDistrict(p) - Takes a precinct, returns its assigned district
// function getPrecinctPop(p) - Takes a precinct, returns its population
// function getDistrictPop(d) - Takes a district, returns its population
// function getStatePop() - Returns the total population of North Carolina
// function getDistrictPrecincts(d) - Returns the list for precincts assigned to district d
// function getDistrictQuality(d) - Returns the objective function score for district d
// function done() - Finishes the process, rendering the map your algorithm generated