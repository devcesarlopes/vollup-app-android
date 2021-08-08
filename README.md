# vollup-app-android

This app is a native **Android** App made as a job for further avaliation from [Vollup](https://www.vollup.com/).
It was developed using [Android Studio](https://developer.android.com/studio?hl=pt&gclid=CjwKCAjw3riIBhAwEiwAzD3TiWGMLwJfHW4meMvGt4mY-y4UFzd1YzDHvJ6nReMEw04JFntq2vSJ0BoCyfgQAvD_BwE&gclsrc=aw.ds) with ```Java```.

The objective is:
1. Create in ```Firebase``` a database with the following fields:

- id.
- user.
- name.
- password.
- balance.

2. Create a ```login``` screen (front and back) connecting with Firebase.

3. Create the front and back from the indicated ```page``` with the following functionalities:

- Show the account balance.
- Show the name from the logged user.

PSD with ```page```: https://we.tl/t-MBVLkPPgsB
Also in 'brydge_tela.psd' in files above.

made by:
Julio Cesar Lopes Santos

## First Steps
1. First download [Android Studio](https://developer.android.com/studio?hl=pt&gclid=CjwKCAjw3riIBhAwEiwAzD3TiWGMLwJfHW4meMvGt4mY-y4UFzd1YzDHvJ6nReMEw04JFntq2vSJ0BoCyfgQAvD_BwE&gclsrc=aw.ds), and all of it's contents.
2. After Android Studio download and installation, create a project, choosing the **API version**, ```Java``` or ```Kotlin``` for language and name.
- Note: Never forget to create the project in **Github repository** so it's easier to commit changes and keep tracking versions.
3. In project, check if all ***SDK files*** are updated.

## Developing Workflow
Some users start the App development from the back-end, others from front-end.
I personally prefer always start from front-end. From my experience during my work as App Dev at UFBA, I think that it's easier to start from front-end because: 
- It's easier to view all the app's functionalities and promote changes.
- It's easier to reuse variables and structure the back-end with proper efficency.

In this project we already have the app design that contains 3 screens, ```login```, ```register account``` and ```page```.

With that in mind, the work-flow is going to be:

1. front-end (login, register and page)
2. Install Firebase and it's features to the project.
3. back-end
4. Testing

## Front End.
- During front creation, it's important to keep in mind that there are many different phones, with different sizes, so it's really important to make a responsible layout. Nothing worse than floating elements to spoil the user experience.
- Make sure that everything the design has requested is in the layout.
- Make sure everything is in place.
- Avoid using absolute meajures, it's much more cleaner to use Relative Layouts.

### Login
This is the resulted layout:
