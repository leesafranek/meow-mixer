MeowMixer

Goal:
Record audio on android device, replace vocals with cat sounds matching tone/syllables/inflection etc.

/Android/ directory contains project files (Android Studio) for a simple android application that can record and playback audio.

Need:
1. Some way to do signal analysis on android. C++ is supported so this means libav is an option (probably overkill). A java option would be significantly less painful.
	a. Syllable detection
	b. tone matching

2. Polished meow audio files (with free to use licences) placed in the ./audio/ directory. Will need to check supported audio encoding/playback for MediaRecorder/MediaPlayer android api.

3. UI That doesn't look like trash
