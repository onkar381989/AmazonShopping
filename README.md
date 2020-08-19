# amazonshopping
Android Set Up in windows and mac machines
1. Download androd sdk tools, platform-tools, platforms
2. For Windows : Set path of all above downloaded tools in environment variable using %ANDROID_HOME%\tools\bin %ANDROID_HOME%\platform-tools
3. For Mac : Set path of all above downloaded tools in bash_profile using 
			 export ANDROID_HOME=/Users/onkar.r/Library/Android/sdk
			 export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
4. Install appium on machine using npm command: npm install -g appium
5. Start UI AutomatorViewer for xpath and ids for an app, just open command prompt and type uiautomatorviewer and hit enter