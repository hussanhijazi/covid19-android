# Clean Architecture
App to show covid-19 data from Brazil and Brazilian cities.

## Create google_maps_api.xml file in app/src/debug/res/values/ with your Google Maps Key
    <resources>
        <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR_KEY_HERE</string>
    </resources>


## Code
Using "Clean Architecture" based on Antonio Leivas and BufferApp implementations.

## Covid-19 Apis references
* https://brasil.io/api/dataset/covid19/
* https://coronavirus-tracker-api.herokuapp.com/
* https://corona.lmao.ninja/v2/historical/brazil
* https://coronavirus-19-api.herokuapp.com/

### Modules
#### domain
In this module, you will find all models (entities)

#### app
In this module, you will find all Android classes and UI.

#### data
In this module you will find all the interfaces/datasources, apis and repos.

#### usecases
In this module you will find all *usecases* / *iteractors*.

## Some observations

### Koin
Used for dependency injection (easy to use and fulfills all requirements here).

### Continuous Integration 
Using Bitrise.io for this purpose.

## References
* Post: https://antonioleiva.com/clean-architecture-android/
* BufferApp: https://github.com/bufferapp/clean-architecture-components-boilerplate
