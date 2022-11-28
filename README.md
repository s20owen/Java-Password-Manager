# Java-Password-Manager

## Developed with JavaFX and Scenebuilder.
Note (This password manage does not use encryption for username or password fields. it is stored as plain text in JSON file.)
### Features
- Textfield complete (username/password)
- Create/Delete operations
- Saves to Local JSON file
- Detects if JSON file exists else creates empty one
- Uses clearbitlogo https://clearbit.com/logo API to load website icons ex: https://logo.clearbit.com/apple.com

### Example Clearbitlogo use
```
  private Image image;
  private static String imgurl = "https://logo.clearbit.com/";
  
  image = new Image(imgurl + lbltitle.getText() + ".com"); 
		  		  
	if(image.isError()) 
  	{
	image = new Image(imgdefault);
	iconimage.setImage(image);
	}
  	else{
	iconimage.setImage(image);
	//System.out.println("image successfully loaded from " + image.getUrl());
  	}

```
## Main screen
<img src="/Assets/JPWM1.png" width="500" height="400">

## Logo imported

<img src="/Assets/JPWM2.png" width="500" height="400">

## About/Version

<img src="/Assets/JPWM%20about.png" width="500" height="400">

## Password Generator

<img src="/Assets/JPWM%20passgen1.png" width="500" height="400">

# Video Demo



https://user-images.githubusercontent.com/71678206/204403121-c158f675-ffa3-4acb-afda-35f40b56ca97.mp4


### TODO's
- Enable user to select JSON file from file system
- Drag and drop file loading
- Link/create random password
- Encryption Salt/Hash
