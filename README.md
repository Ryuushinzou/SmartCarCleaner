![Build Spring app with Maven](https://github.com/Ryuushinzou/SmartCarCleaner/workflows/Build%20Spring%20app%20with%20Maven/badge.svg)
![Build Android project](https://github.com/Ryuushinzou/SmartCarCleaner/workflows/Build%20Android%20project/badge.svg)

# Smart Car Cleaner
A smart self-service washing station software ecosystem aimed to mobile users built using Java, Kotlin and ReactJS.

## Table of Contents
* [General info](#general-info) - short description of the main supported features
* [Technologies](#technologies) - list of technologies used and their purposes
* [Setup](#setup) - *How to* guides for installing, building and running the apps in this project

## General info
The Smart Car Cleaner (SCC) is a project that handles the management of a self-service washing station for vehicles. Using the SCC environment, an admin can manage the locations, resource levels and user appointments in order to provide quality services to the station's clients. While admins are able to manage the generated data, users in the role of analyst can review all the data using reports generated by the system to improve the services and/or provide solutions to problems in a proactive approach.

## Technologies
This project is developed using:
* **Java 8** - for the Spring web services
* **Kotlin** - for the Android client app
* **ReactJS** - for the web apps (admin & data alalysis)

## Setup
At the moment, in order to setup the sub-projects (web, mobile, etc.) the steps provided in the SETUP.md files related to the project as follows:
* For the Spring project: ![Spring Project SETUP.md](./backend/SETUP.md)
* For the Android project: ![Android Project SETUP.md](./android/SETUP.md)
* For the Web project: ![Web Project SETUP.md](./web/SETUP.md)
