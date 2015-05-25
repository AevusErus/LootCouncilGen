# Loot Council Generation Script

### Purpose
A java program that automatically generates a Loot Council to make sure the participation is as fair an unbiased as possible.

### How It Works
The program expects there to be a raidList.txt containing the following information: the available list of raiders and the number of times they've been on the council.

From this list the script calculates the names for the council. It will always prioritize raiders with a lower participation count than others in order to try and keep participation in the council as even as possible. The script picks out all potential "candidates" for a council; these are the raiders with the lowest participation count or the second to lowest if there aren't enough lowest count raiders to fill the council (i.e. 5 raiders). From this Candidate list the script will choose 5 randomly to be on the council, but will prioritize those with the LOWEST participation count. For example, if 3 people have the lowest participation count out of everyone else on the candidate list, they will be selected guaranteed and the other 2 positions will be filled randomly from the remaining candidates.

After the council list is generated, the council list and the candidate list are printed to the councilList.txt file, along with being printed to the console. To figure out who is on the council for a given week, simply scroll down the councilList.txt file until you find the corresponding raid week printed out at the top of every list. If you're looking for the most current council, look at the currentCouncil.txt file. In addition, the participation count of the selected council members is updated in the raidList.txt file and increased by one.

In the case a council member needs to be replaced then the next person on the candidate list (reading left to right) is chosen. When the candidate list is printed, it is already put in a random order so that the selection is still maintaining randomness. The selected candidate member(s) then have their participation count in raidList.txt increased (currently this has to be done manually). The replaced player keeps their participation count even if they didn't get to participate this week...in essence they forfeit their right to be on the council by not showing up. This is done in order to maintain the balance of the participation scores as it was revealed to cause major problems of repeating members on the council ahead of others, which is what we're trying to avoid.

And that's how this script works! There are still bugs and things being worked out so please be patient with us. If you notice any issues, feel free to create a github issue here so that it can be addressed ASAP! They all get reported to our guild's Slack and Lifefire will see it almost immediately.

### Known Bugs
1. Not easy for everyone to run
	* Right now the list is very much programmer friendly, but not friendly to a wider range of our raiding team. Thus, it is a goal to eventually have this more accessible for people to run on their local machines.

### How To Install
Currently it's best if only one person actually pushes to master (since it contains the council participation list). However, if you would like to pull this project and play around with the code on your own, please feel free. In addition, even if you have no intention of working on the code, please feel free to submit issues or bugs if you notice them so that they can be corrected ASAP.

Further instructions on how to pull and run this script are coming soon.
