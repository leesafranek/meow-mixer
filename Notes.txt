Machine learning sandbox in python

Setup
	Installation of tensorflow (cpu version, not gpu)
		Need to install 64 bit version of  3.5/6.x (mandatory)
		install tensorflow via pip, e.g. >> pip3 install tensorflow

	Might want a python package called pydub.
		Used for converting audio file types (*.wav files are ideal). Wraps libav
		
	Audacity is a relatively light-weight program for editing audio files
	
Main breakdown of project
	Goal: Replace vocals of songs with cat sounds in the same pitch
	
	This can be broken down into a few chunks
	1. Detection of vocals
		This is mostly machine learning/signal processing.
	2. Changing pitch of cat sound to match 
		all signal processing (easy)
		Need audio files to play around with ( check licenses )
	3. Gathering of audio files
		a. collection of some cat meows
			http://www.orangefreesounds.com/cat-meowing-sounds/
		b. audio files containing vocals
			ideally some of these files will only be vocals (might want some without vocals as well)
			here's a very promising dataset:
	4. Implementation
			https://sites.google.com/site/unvoicedsoundseparation/mir-1k
		What language/tools will be used to automate this process.
		Currently using python 
		
Notes:
	Need signal processing tools in python for dealing with audio files and finding tones.
	Python ships with the standard library wave
	this stackedoverflow page looks promising https://stackoverflow.com/questions/43963982/python-change-pitch-of-wav-file
	
	General audio tools will probably be helpful.
		Found a way to convert audio file types pretty easily within python (once you download the libav stuff)
		https://stackoverflow.com/questions/3049572/how-to-convert-mp3-to-wav-in-python
		
	This project looks very close to what we want to be doing:
	https://github.com/andabi/music-source-separation/blob/master/README.md