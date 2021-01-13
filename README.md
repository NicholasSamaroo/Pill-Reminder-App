# Pill-Reminder-App

Utilized Androids **Notification framework** and **AlarmManger** to create an Android mobile application allowing users to pick from various alarm types 
designed to remind them to take their medication.

**APP DESCRIPTION**
-----------------
Users click the **extended floating action button** in the main activity to bring them to the form activity. This is where they fill out all necessary information needed to create 
an alarm for the medication of their choosing. Pertinent information from the form activity is then represented back in the main activity in a **card based layout** which 
utilizes a **recycler view**. From there, if the card is swiped left or right an alert dialog appears asking them if they want to cancel the alarm.

All card information is stored using the **Room persistence library**. A running integer is used to create unique pending intents for alarm creation. The integer is saved to **shared preferences** and restored on creation of the form activity.

**FUTURE INTEGRATIONS**
---------------------
Features to be integrated in the future include: a duration option which allows the user to choose for how long they want to be notified for, another alarm type labeled 'Daily every X hours" that alerts the user in chunks of X beginning from the first intake time to the last intake time, *and* an inventory tracker which would track how many pills the user has left and notify them when they are running low. As seen in the demo, there is a button labeled "Dose" to the far right of the "Choose a time" button; this is where the user would input the dosage for their medication.

**DEMO**
------
![Demo](https://github.com/NicholasSamaroo/Pill-Reminder-App/blob/master/demo/demo.gif)
Demo

**EXAMPLE NOTIFICATION**
----------------------
![Example Notification](https://github.com/NicholasSamaroo/Pill-Reminder-App/blob/master/Notification%20Example/Notification.gif)
Example of Notification being fired

**FEATURES TO BE ADDED IN FUTURE**
--------------------------------
![Future integrations](https://github.com/NicholasSamaroo/Pill-Reminder-App/blob/master/Future%20integrations/needToIntegrate.gif)
Features to be integrated in the future
