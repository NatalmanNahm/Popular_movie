# Popular_movie Overview
Create an app that fetch movie API data from a movie database as JSON and displays them in a gridView when an item is clicked, we will be directed to the detail activity to see more info about a movie such as the release date, the overview of the movie, reviews and trailers. When you click on the trailers, it will take you to the youtube app to play that trailer clicking on the "Add as favorite" button will add it the movie to the database and will be access later. when you change your setting to favorite movie.you are able to take them out of you favorite movie database just by clicking on the button again. You will be able to change your setting from Popupar movies to Most rated Movies and Your favorite Movies.

# Installation
 Use an API Key if you want to run this app, You will need to create an account at https://www.themoviedb.org/account/signup and request an API Key. Once you get the key, go to your gradle.proprieties file and add your api Key like so `myMovieAPIKEY="4c847680f6bc9cd56eff4d157bedc568"`. Go to your App Gradle file and add this
 
 ```
 buildTypes.each{
        it.buildConfigField 'String', 'myMovieAPIKEY', myMovieAPIKEY
    }
```
Then head to the utilities folder and NetworkUtils then change this `private static String KEY = BuildConfig.myMovieDbApiKey;` to `private static String KEY = BuildConfig.myMovieAPIKEY;`.

# Why this app
Things I learn building this app:
- Build a UI layout for multiple Activities.
- Launch these Activities via Intent.
- Fetch data from themovieDB API
- You’ll allow users to view and play trailers (either in the youtube app or a web browser).
- You’ll allow users to read reviews of a selected movie.
- You’ll also allow users to mark a movie as a favorite in the details view by tapping a button (star).
- You'll make use of Android Architecture Components (Room, LiveData, ViewModel and Lifecycle) to create a robust an efficient application.
- You'll create a database using Room to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
- You’ll modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.


    
