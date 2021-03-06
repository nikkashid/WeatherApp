# WeatherApp
Application to search weather details of the entered city.
Already searched results are shown in the list of home screen.
Once searched the data of that city will be present in the database and the new data will only be fetched if the existing data is more than 24 hours old(old data will be deleted first).

Technology used in the app :
1) Kotlin for writing code.
2) Hilt dagger for injecting network, database and viewmodel modules.
3) Room database for storing data.
4) MVVM architecture.
5) DataBinding for fragment as well as recyclerview adapter.
6) Viewbinding on main screen.
7) RXJava/RxAndroid for database as well as network operations.
8) DiffUtil in recyclerView.
9) Navigation component for fragment navigation.
10) Truth lib for testing(in-progress).
11) Monitoring app crashes with Firebase Crashlytics.
