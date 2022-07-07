ABSTRACT:
Research has shown that living longer is directly proportional to living a healthy lifestyle. Our mobile application promotes healthy living among our users. Living a healthy lifestyle can help prevent chronic diseases and long-term illnesses. Feeling good about yourself and taking care of your health is important for your self-esteem and self-image. Maintain a healthy lifestyle by doing what is right for your body. We programmed our application for mobile users because we believe having a health tracker with the users will always encourage them to track their health and live healthier lives. Our application is featured with Diet Monitoring, Diet Suggestion, Steep counting, and Sleep suggestion dashboards. The user is required to input his Name, Age, Height, and Weight. Our integrated model calculates the user's Body Mass Index and suggests a suitable diet and sleep. Our mobile application contains a navigation bar that allows users to navigate through different dashboards. Our diet monitoring feature contains a date selection button that allows users to select the date and input the food consumed in a day. We used cloud firebase and NutritionX API technologies to build our Mobile Application.

Motivation:
With the growing overweight and obese population around the world, it is essential that we monitor our living habits and avoid activities that might cause harm. Research has stated that people with a body mass index higher than 25 are likely to live a shorter life compared to those whose BMI is less than 25. Many successful people show that living a healthy life will make them more productive and concentrate more on the things they like. Research has shown that sufficient sleep recharges a person's mind and body and helps them feel refreshed and alert throughout the day. Our motto is to promote healthier living among our users. We believe our users will benefit from using our application, transform their lives, and start living healthier life.







DESIGN:
The application implements the user registration, sign-in, and password reset features using the Google firebase authentication, which provides methods to create and manage users who use their email addresses and passwords to sign in. The user's data is stored in the firebase realtime database. At the time of registration, the user needs to enter the user information like name, email address, height, weight, age, etc., in the application. The information entered will be stored in the database and can be accessed only by that user.  The application uses the information entered, and the smart AI creates suggestions for the user like sleep schedule, diet suggestions, and the number of steps required to be taken during a day. The user can navigate to all the features using the navigation bar. The features provided by the application are Foot Step Monitoring, Sleep Suggestion, Diet Monitoring, and Diet Suggestion. The home page contains information about the total calories consumed and the calories to be consumed for the day. The Foot Step Monitoring page displays the information about the footsteps taken by the user and the suggested number of steps to take. Sleep suggests the minimum number of hours a user needs to sleep to stay healthy.  The Diet monitoring feature enables the user to monitor their diet and check if their dietary intake reaches the suggested calories. The user just needs to enter the name of the food, and the application retrieves the calorie information of the food from the database and the API. The user can also check his diet information for all days and compare the diet consumed. The diet suggestion feature calculates the user's BMI and uses the user's information to suggest a specific diet for the user. 
TECHNOLOGIES USED:
ANDROID STUDIO: Android studio is a desktop IDE. It is used for developing Android applications. Our mobile application is developed in Android Studio. Android studio is part of the Android open source project. Google primarily develops it, and it is built on Jetbrains’ Intellij IDEA software and designed specifically for Android development. Android Studio supports different plugins and libraries, which helps the developer make more complicated apps. The gradle-based app building and Android-specific refactoring provide a very conducive and user-friendly platform.

FIREBASE:  Firebase is used to create a backend for different mobile and desktop applications. It is primarily developed by Google. Firebase is usually integrated with Android Studio projects. Firebase provides many analytical features, authentication, Hosting, Cloud Messaging, Cloud storage, and many more. In this project, the cloud database and authentication features are used.  Firebase is popular among android developers due to its ease in integrating in a project and maintaining it. We used firebase to authenticate our users using their authentication feature and we used their many backend features to make our app more useful.

NutritionX API: NutritionX API is developed by Nutritionix company. This API has a lot of interesting features, such as Voice-Text input translator, Autocomplete searches, Branded food descriptions, Restaurant geolocator, and many more. We used NutrionX API to retrieve information about the food items requested by our users. This API has the largest nutrition database. Integrating this into our Mobile Application would help the user get his search about the food consumed or going to consume faster.
IMPLEMENTATION:


Flow Chart
Initially, when the application is launched, the application starts the Main Activity. In the main Activity, the user sign-in action is implemented. OnclickListener is attached to the forgot password TextView and registration button, which will launch the respective Forgot password and Registration activity when pressed. The user authentication is implemented using the Google firebase authentication and database. Figure 1, 2, and 3 shows the layout for the login, registration, and password reset pages. 
                  
                      Fig. 1 Login                Fig. 2 Reset Password         Fig. 3 Registration
The application launches the Profile activity when the user login is successful. The application remains in this activity and automatically launches this activity if the user is already logged in. The Profile activity implements a navigation bar and on clicking on the items navigates to all the features. Figure 4 shows the layout of the navigation bar.

Fig. 4 Navigation bar

On clicking the home item in the navigation bar, it launches the home fragment in the profile activity.  The home fragment uses the progress bar which is used to display the calorie information. The Home fragment calculates the BMI and uses the user's information from the firebase database to suggest the recommended calorie intake. The Fig 5. shows the home fragment.
                        
Fig. 5 Home                       Fig. 6 Sleep Suggestion        Fig 7. Diet Monitoring
 The  Steps Monitoring fragment displays the information about the users steps and suggested number of steps. The number of steps information is given by the Step Counter sensor. The data is then stored in the real time firebase database which can be accessed in real time by the application and displayed by the Step Monitoring feature. Figure 6. shows the step monitoring layout.  The Diet monitoring uses Recycler view to show the diets from the database. The users can add all the diets and check the diets based on the date. The diet suggestion fragment gives the diets based on the users information.The fig 7. Shows the diet monitoring.

TECHNICAL CHALLENGES:
We faced a lot of issues when trying to store the data in the firebase database and retrieve the information. 
We had trouble making the step counter sensor work since we were trying to move from activity to activity.
The firebase recyclerview adapter was not working properly in the beginning but after some debugging we were able to retrieve the information from the database and display it.
FUTURE WORK:
NutritionX API provides a lot of features that will improve our user experience. Integrating their Voice-Text feature will make inputting the data easier for the users. Their Restaurant's Geolocator will help suggest the ideal food for the user.
We can integrate meal prep API such BigOven to suggest healthier recipes to our users.
We are integrating a  planning section in. This will help the user to plan the next day's meals and calculate the amount of nutrients and calories for the next day.
We can integrate a smartwatch connection, this will help the user share their tracked data from a smartwatch onto a Mobile phone.
References:

Add Firebase to your Android project  |  Firebase Documentation (google.com)
Nutrition API by Nutritionix
1,000,000+ Recipe and Grocery List API | BigOven API
Get Started with Firebase Authentication on Android  |  Firebase Documentation (google.com)





