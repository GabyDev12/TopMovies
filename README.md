# TopMovies


<div align="center">

  <img width=200px height=200px src="./images/Icon.png">

</div>

***

<p>&nbsp;</p>

## Index

 - [Description](#Description)
 
 - [Version and Database](#Version-and-Database)

 - [GUI and Behavior](#GUI-and-Behavior)
 
 - [Download](#Download) 



<p>&nbsp;</p>

***

<p>&nbsp;</p>



## **Description**


<p>&nbsp;</p>

This Android app allows you to consult the billboard of a cinema, being able to obtain both information about each available film as well as those that will come out soon.

<p>&nbsp;</p>

You can also buy tickets to see the movie you want (you will have to have previously registered, with your own account of the app or with your Google account), being able to choose the day, time and seats you prefer.

Once the purchase has been made, you can download the ticket to see the film or consult the ticket later.

<p>&nbsp;</p>

In addition, you will have the possibility to customize/modify your profile, consult a list of movies you have seen and/or change the language of the application (by default it is in English but it is available in Spanish too).

<p>&nbsp;</p>


***It was developed as a college project in 3-4 months***



<p>&nbsp;</p>

***

<p>&nbsp;</p>



## **Version and Database**


<p>&nbsp;</p>

This app is **available** for devices with **Android 5.1 or after**.

All the data is stored in a **Firebase Database**.

<p>&nbsp;</p>

<div align="center">

  <img width=450px height=200px src="./Images/Firebase-Logo.png">

</div>



<p>&nbsp;</p>

***

<p>&nbsp;</p>



## **GUI and Behavior**

<p>&nbsp;</p>

 - **Loading Screen**:
  
    It is displayed for a short period of time when the application starts and cannot be interacted with.

   <div align="center">

   <img width=200px height=380px src="./Images/Loading-Screen.png">

   </div>

   <p>&nbsp;</p>


 - **Home Screen**:
  
    At the beginning, the films that are currently on the **billboard** are shown, being able to access each one to obtain more information or to buy tickets to see it.

    From the same screen, we can also access a tab that shows the **movies** that will be **released soon**.
    
    <div align="center">

      <img width=200px height=380px src="./Images/Home-Screen-1.png">

      <img width=200px height=380px src="./Images/Home-Screen-2.png">

    </div>

    <p>&nbsp;</p>
    

    In addition, it has a side menu which we can use to navigate between different sections of the app.
    
    Depending on whether you are logged in or not, more options will be available.

    <div align="center">

      <img width=200px height=380px src="./Images/Side-Menu-1.png">

      <img width=200px height=380px src="./Images/Side-Menu-2.png">

    </div>

    <p>&nbsp;</p>
    

    - **Side Menu**:

      - **Home**: allows you to access the billboard and upcoming movies. It is the initial option when loading the application and it was shown in the previous images.
      
      <p>&nbsp;</p>


      - **My Films**: it shows a list of all the movies you have seen, and you can even **see the purchase ticket**, which will show data such as the date and time of the session or the total price paid.

         <div align="center">

         <img width=200px height=380px src="./Images/My-Films.png">

         </div>
         
         <p>&nbsp;</p>

         **By clicking on the eye** that is seen next to each ticket, a new screen will open showing the **ticket in embedded PDF format**.
         
         It will have a unique **QR code**, which must be displayed and scanned at the time of watchinv the movie at the cinema.

         <div align="center">

         <img width=200px height=380px src="./Images/Ticket-Viewer.png">

         </div>

         <p>&nbsp;</p>

         This is a way of being able to **access the purchased ticket** from your profile at any time.

         <p>&nbsp;</p>


      - **Profile**: allows you to check the profile information.
      
         Depending on whether you are **registered Firebase user** or are **using a Google account**, it will be displayed more or less data.

         In addition, we will be able to see the total number of movies watched and we will have the option to log out.

         <div align="center">

         <img width=200px height=380px src="./Images/Profile.png">

         </div>

         <p>&nbsp;</p>


      - **Sign Out**: allows you to log out of the current user account.
      
         <p>&nbsp;</p>


      - **Settings**: if you have not logged in, only the option to **change the language** will be shown, being able to choose between **Spanish** or **English**.
      
         <div align="center">

         <img width=200px height=380px src="./Images/Settings-Not-Logged-In.png">

         </div>

         <p>&nbsp;</p>

         However, if you are **logged in with a Firebase user**, you can **edit the profile** and/or **change the password**.

         <div align="center">

         <img width=200px height=380px src="./Images/Settings-Logged-In-Firebase.png">

         </div>

         <p>&nbsp;</p>

         In the **profile edit**, you can change the first name, last name, login email and you can even set update the profile photo.

         <div align="center">

         <img width=200px height=380px src="./Images/Settings-Logged-In-Firebase-Edit-Profile.png">

         <img width=200px height=380px src="./Images/Settings-Logged-In-Firebase-Edit-Password.png">

         </div>

         <p>&nbsp;</p>

         On the other hand, if you **logged in with a Google account**, when the user tries to **edit the profile**, they will be sent to the **Google website** to make the changes.

         <div align="center">

         <img width=200px height=380px src="./Images/Settings-Logged-In-Google.png">

         <img width=200px height=380px src="./Images/Settings-Logged-In-Google-Edit-Profile.png">

         </div>

         <p>&nbsp;</p>


      - **About**: you will be able to read a **short description of the app** and there will be a link to a **GitHub repository** where you can consult the documentation and the app code.

         <div align="center">

         <img width=200px height=380px src="./Images/About.png">

         </div>

         <p>&nbsp;</p>


 - **Sign Up Screen**:
  
    It shows some fields which are necessary to cover to **create a user account** in the **Firebase database**.
    
    **All fields are required** and the email will be checked if it has a valid format and if the passwords match.
    
    <div align="center">

      <img width=200px height=380px src="./Images/Sign-Up-Screen.png">

    </div>

   <p>&nbsp;</p>


 - **Sign In Screen**:
  
    It shows some fields which are necessary to cover to **log in**.
    
    It also allows you to **log in with a Google account**, being a faster and more secure way to log in.

    If you **log in with a Google account**, there will be **less customization options** in the app, because of it will have to be done by the website.
    
    <div align="center">

      <img width=200px height=380px src="./Images/Sign-In-Screen.png">

    </div>

   <p>&nbsp;</p>


 - **Movie Details Screen**:
  
    It show the **details of the movie selected**, such as the duration, the release date, categories, etc.

    It has a **button** to **watch the trailer** of the film from **inside the app**.
    
    It also has a **button** to **buy tickets to see the movie**, which will work if you have previously logged in and if the selected movie is on the billboard, otherwise a warning will appear.
    
    <div align="center">

      <img width=200px height=380px src="./Images/Movie-Details-Screen.png">

    </div>

   <p>&nbsp;</p>


 - **Booking Screen**:
  
    From this screen you **will select the date**, **time**, and **seats** in which you want to see the movie.
    
    **All fields will be required** to be able to go to the checkout screen.
    
    <div align="center">

      <img width=200px height=380px src="./Images/Booking-Screen-1.png">

    </div>

   <p>&nbsp;</p>

   When **selecting the seats**, the **price per ticket** is indicated, which **will be multiplied** after with the **number of seats selected**, calculating then the **total price** to pay.
    
    <div align="center">

      <img width=200px height=380px src="./Images/Booking-Screen-2.png">

    </div>


 - **Checkout Screen**:
  
    It shows a **summary of the data previously selected**, including the seats that have been selected and the total to pay.
    
    <div align="center">

      <img width=200px height=380px src="./Images/Checkout-Screen.png">

    </div>

   <p>&nbsp;</p>


 - **Tickets Bought Screen**:
  
    The **ticket** of the selected movie **will be saved in the user's profile**, which **will contain the data entered** during the purchase process.
    
    These **data can be accessed** from the ***"My Films"*** option in the **side menu**, as explained above.

    In addition, it offers the **possibility of downloading the ticket of the movie** in **PDF format** and **saving it on your mobile device**, which **cannot be done after exiting this screen** (*it can only be viewed, not downloaded*).
    
    <div align="center">

      <img width=200px height=380px src="./Images/Ticket-Bought-Screen.png">

    </div>



<p>&nbsp;</p>

***

<p>&nbsp;</p>



## **Download**

<p>&nbsp;</p>

You can download the app in the following link → [Download here](https://downgit.github.io/#/home?url=https://github.com/GabyDev12/Pink_Girl_Adventures/tree/master/Builds/Windows)

*Authorize it to be installed. Don't worry, it doesn't have virus -_-*
