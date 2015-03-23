# Loot Council Generation Script

## Purpose
A java program that automatically generates a Loot Council to make sure the participation is as fair an unbiased as possible.

## How It Works
The program expects there to be a raidList.txt file with the list of raiders to select from and the count of their participation in previous councils. From this list the program figures out a list of participants with the lowest count, if that list is less than 5 (the required number for a complete council) then it finds raiders with the second lowest count and adds them to the candidate list. From this candidate list, participants are chosen at random. Priority is given to those with the lowest participation count to try and keep it as even as possible as time progresses. This selection function determines the council list, which is then put into the councilList.txt file.

## How To Install
Currently it's best if only one person actually pushes to master (since it contains the council participation list). However, if you would like to pull this project and play around with the code on your own, please feel free. In addition, even if you have no intention of working on the code, please feel free to submit issues or bugs if you notice them so that they can be corrected ASAP.

Further instructions on how to pull and run this script are coming soon.
