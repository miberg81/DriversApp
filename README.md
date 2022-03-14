# DriversApp

Hi, German!
And welcome to the DriversApp
This is a backend microservice to manage drivers on the road in an elegant way
This app provides both CRUD Apis and query apis to manage drivers as per the task spec.

The app runs by running DriversServiceApplication 
The server listens on port 8005.

Attached here is the list of Get apis to run in postman:

@Get
getAllDrivers()
http://localhost:8005/drivers

@Get
getDriversWithinMapBounds()
http://localhost:8005/drivers/lat/30/40/long/30/40

@Get
getDriversWorkingBetween()
http://localhost:8005/drivers/start/12/0/finish/22/0

@Get
getActiveDrivers()
http://localhost:8005/drivers

Please see a link to ALL postman apis collection
https://www.getpostman.com/collections/56f721a80e1839033807
