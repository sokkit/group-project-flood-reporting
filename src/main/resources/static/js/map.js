// Create map and set the view with Lat and Long of Wales, 7 is zoom of map and user cannot zoom further than that
var map = L.map('map', {
    minZoom: 8
});

// Add the tile/style of the map from mapbox.com
L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    maxZoom: 18,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoiYWJkdWxtaWFoIiwiYSI6ImNrbXdpN2hwZDBmM3cydXJubXM2eHoyaGQifQ.RI7Qv5cRdy1h-BRgK1NKpA'
}).addTo(map);

// Set view map view to center of Wales
map.setView([52.4307, -3.7837], 7);

// Creating a function that will request data from API and store as JSON
// Adapted From https://www.geeksforgeeks.org/how-to-use-the-javascript-fetch-api-to-get-data/
function requestFromAPI(url){
    return new Promise(async (resolve, reject) => {
        try{
            const result = await fetch(url)
            resolve(result.json())
        }catch (e){
            reject(e)
        }
    })
}

////// Function will get lat long from postcode API when user fills in postcode field on report form //////
function getLatLongFromPostcode() {
    // Get the postcode from field
    const postcodeField = document.getElementById("postcodeField").value;
    const localAuthField = document.getElementById("localAuthField");
    const postcodeAPI = "https://api.postcodes.io/postcodes/"+postcodeField;        // Add postcode to api
    console.log(postcodeAPI);

    // Get results from API
    requestFromAPI(postcodeAPI).then((apiData) => {
        console.log(apiData);
        var results = apiData["result"];
        const lat = results["latitude"];            // Storing Latitude from results
        const long = results["longitude"];          // Storing Longitude from results
        const localAuthority = results["admin_district"];
        localAuthField.setAttribute("value", localAuthority);
        // Add animation to map, so that it zooms to location at duration of 1.5
        // Adapted from https://gis.stackexchange.com/questions/228273/how-to-slow-the-zoom-transition-speed-in-leaflet
        map.flyTo([lat, long], 16,{
            animate: true,
            duration: 1.5
        });
        var popup = L.popup()
            .setLatLng([lat, long])
            .setContent("Click on map to specify your report location")
            .openOn(map);
    });
}

////// Add markers to the map from report form //////
// Requesting data for reports and adding markers to map
const reportAPI = "https://localhost:8081/api/reports";
requestFromAPI(reportAPI).then((result) => {
    console.log(result)

    result.forEach((report) => {
        // Add marker for each report
        var marker = L.marker(report["latLong"].split(", ")).addTo(map);
        marker.bindPopup("Description: "+report["description"]+"<br>Date and time reported: "+report["datetime"]+"<br>Depth of Flood (meters): "+report["depthMeters"]+"<hr><center><a class='btn btn-secondary' role='button' style='color: white' href='/detailed-report/"+report["reportPath"]+"'>View More Details</a></center>");
    })
});

////// Get lat long from when user clicks on map //////
function setLatLongInField(latLong) {
    var field = document.getElementById("latLongField");
    var postcodeField = document.getElementById("postcodeField");
    field.setAttribute("value", latLong);
    postcodeField.setAttribute("placeholder", "Co-ords from map: "+latLong);
}

var popup = L.popup();

// Create function that will get latlong from when user clicks on map
// This function does various functionality using the latlong
function onMapClick(e) {
    // Remove unnecessary values from .toString
    var latLongTemp = e.latlng.toString().replace(/^\D+/g, '');         // Only get digits
    var latLong = latLongTemp.replace(/([()])/g, '');           // Remove parentheses
    console.log(latLong)
    var latLongSplit = latLong.split(", ");

    const opencageAPI = "https://api.opencagedata.com/geocode/v1/json?key=e0e009282aec4b119ca5cd7d25c7c20d&q="+latLongSplit[0]+"%2C+"+latLongSplit[1]+"&pretty=1&no_annotations=1";
    requestFromAPI(opencageAPI).then((apiData) => {
        console.log(apiData);
        var results = apiData["results"];
        var firstResult = results[0];
        var components = firstResult["components"];
        const country = components["state"];
        if (country!="Wales") {
            popup
                .setLatLng(e.latlng)
                .setContent("Cannot set location outside of Wales")           // Let user know location is being set here
                .openOn(map);
        } else {
            popup
                .setLatLng(e.latlng)
                .setContent("Setting location of your report here")           // Let user know location is being set here
                .openOn(map);

            // Adding flyTo animation when user clicks on map to help pin-point a more specific location
            map.flyTo(e.latlng, 16, {
                animate: true,
                duration: 1.5
            });

            // Set lat long in latLong field
            setLatLongInField(latLong);
            console.log(latLong)        // Console log the latLong (testing)

            // Get local authority and postcode from API and set those values in the report form
            const localAuthField = document.getElementById("localAuthField");
            const localAuthority = components["county"];
            const postcodeOpencage = components["postcode"];
            localAuthField.value = localAuthority;          // Set value of field to local authority from API

            const postcodeField = document.getElementById("postcodeField");
            const postcodeAPI = "https://api.postcodes.io/postcodes?lon=" + latLongSplit[1] + "&lat=" + latLongSplit[0];        // Add postcode to api
            requestFromAPI(postcodeAPI).then((apiData) => {
                console.log(apiData);
                try {
                    var results = apiData["result"];
                    var firstResult = results[0];
                    var postcodeIO = firstResult["postcode"];
                } catch(err) {
                    console.log("There is an error trying to get postcode: "+err)
                }

                // If postcode exists in postcode.io API, then set value of field to postcode from API
                if (postcodeIO) {
                    postcodeField.value = postcodeIO;
                } else if (postcodeOpencage) {
                    postcodeField.value = postcodeOpencage;           // If postcode exists in api.opencagedata API, then set value of field to postcode from API
                } else {
                    postcodeField.value = "";           // Otherwise, keep field empty
                }
            });
        }
    });
}

map.on('click', onMapClick);